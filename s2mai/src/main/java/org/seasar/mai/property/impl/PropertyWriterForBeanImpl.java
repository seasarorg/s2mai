/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.mai.property.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.property.PropertyWriterForBean;
import org.seasar.mai.property.mail.MailPropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;
import org.seasar.mai.property.server.ServerPropertyWriter;
import org.seasar.mai.property.server.ServerPropertyWriterFactory;

/**
 * @author rokugen
 */
public class PropertyWriterForBeanImpl implements PropertyWriterForBean, S2MaiConstants {
    private MailPropertyWriterFactory mailPropertyWriterFactory;

    private ServerPropertyWriterFactory serverPropertyWriterFactory;

    public void setMailProperty(Mail mail, Object bean) {
        if (bean == null) {
            return;
        }

        BeanDesc desc = BeanDescFactory.getBeanDesc(bean.getClass());

        for (int i = 0; i < desc.getPropertyDescSize(); i++) {
            PropertyDesc pd = desc.getPropertyDesc(i);
            Object value = pd.getValue(bean);
            if (value == null) {
                continue;
            }
            setMailProperty(mail, pd, value);
        }

    }

    private void setMailProperty(Mail mail, PropertyDesc pd, Object value) {

        boolean isAttachedFile = isAttachedFileProperty(pd);
        String propertyName = isAttachedFile ? ATTACHED_FILE : pd.getPropertyName();
        MailPropertyWriter propWriter = mailPropertyWriterFactory.getMailPropertyWriter(propertyName);

        if (propWriter == null) {
            return;
        }

        if (!isAttachedFile) {
            propWriter.init(mail);
        }
        propWriter.setProperty(mail, value);
    }

    private boolean isAttachedFileProperty(PropertyDesc propertyDesc) {
        Class clazz = propertyDesc.getPropertyType();

        if (clazz == List.class) {
            Method method = propertyDesc.getReadMethod();
            Class elementType = MethodUtil.getElementTypeOfListFromReturnType(method);
            if (elementType != null) {
                clazz = elementType;
            }
        }

        return (clazz == AttachedFile.class || clazz == AttachedFile[].class || ATTACHED_FILE.equals(propertyDesc
                .getPropertyName()));
    }

    public void setServerProperty(SendMail sendMail, Object bean) {
        if (bean == null) {
            return;
        }

        BeanDesc desc = BeanDescFactory.getBeanDesc(bean.getClass());
        ServerPropertyWriter propWriter = null;
        for (int i = 0; i < SERVER_PROPERTIES.length; i++) {
            String propertyName = SERVER_PROPERTIES[i];
            if (desc.hasPropertyDesc(propertyName)) {
                PropertyDesc pd = desc.getPropertyDesc(propertyName);
                Object value = pd.getValue(bean);
                if (value != null) {
                    propWriter = serverPropertyWriterFactory.getServerPropertyWriter(propertyName);
                    propWriter.setProperty(sendMail, value);
                }
            }
        }
    }

    /**
     * @param mailPropertyWriterFactory
     *            The mailPropertyWriterFactory to set.
     */
    public void setMailPropertyWriterFactory(MailPropertyWriterFactory mailPropertyWriterFactory) {
        this.mailPropertyWriterFactory = mailPropertyWriterFactory;
    }

    /**
     * @param serverPropertyWriterFactory
     *            The serverPropertyWriterFactory to set.
     */
    public final void setServerPropertyWriterFactory(ServerPropertyWriterFactory serverPropertyWriterFactory) {
        this.serverPropertyWriterFactory = serverPropertyWriterFactory;
    }
}
