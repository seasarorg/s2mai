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
package org.seasar.mai.meta.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.MultiPartEmail;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.property.PropertyWriterForAnnotation;

/**
 * @author Satsohi Kimura
 * @author rokugen
 */
public class MaiMetaDataImpl implements MaiMetaData {
    private Map mails = new HashMap();

    private Map templatePaths = new HashMap();

    private String ext;


    public MaiMetaDataImpl(Class maiClass, PropertyWriterForAnnotation propertyWriterForAnnotation, String ext) {
        this.ext = ext;
        Mail classMail = null;
        try {
            String path = maiClass.getName().replaceAll("\\.", "/") + ".dicon";
            S2Container container = S2ContainerFactory.create(path);
            classMail = (Mail) container.getComponent(Mail.class);
        } catch (ResourceNotFoundRuntimeException e) {
            classMail = new Mail();
        }
        Method[] methods = maiClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Mail mail = getMail(maiClass, classMail, method);

            mails.put(method.getName(), mail);
            this.setPropertiesFromMailPropertiesDicon(mail);
            propertyWriterForAnnotation.setMailProperty(mail, method);

        }
    }

    private Mail getMail(Class maiClass, Mail classMail, Method method) {
        Mail mail = null;
        try {
            String path = maiClass.getName().replaceAll("\\.", "/") + "_" + method.getName() + ".dicon";
            S2Container container = S2ContainerFactory.create(path);
            mail = (Mail) container.getComponent(Mail.class);
        } catch (ResourceNotFoundRuntimeException e) {
            mail = new Mail(classMail);
        }
        if (mail == null) {
            mail = new Mail();
        }
        return mail;
    }

    private void setPropertiesFromMailPropertiesDicon(Mail mail) {
        S2Container container = S2ContainerFactory.create(S2MaiConstants.MAIL_PROPERTIES_DICON);
        String from = (String) container.getComponent("from");
        if (from != null && mail.getFrom() == null) {
            mail.setFrom(from);
        }
        String replyTo = (String) container.getComponent("replyTo");
        if (replyTo != null && mail.getReplyTo() == null) {
            mail.setReplyTo(replyTo);
        }

    }

    public Mail getMail(Method method) {
        Mail origin = (Mail) mails.get(method.getName());
        return new Mail(origin);
    }

    public synchronized String getTemplatePath(Method method) {
        String path = (String) templatePaths.get(method.getName());
        if (path != null) {
            return path;
        }
        Class maiClass = method.getDeclaringClass();

        path = maiClass.getName().replaceAll("\\.", "/") + "_" + method.getName() + "." + ext;

        templatePaths.put(method.getName(), path);

        return path;
    }

}
