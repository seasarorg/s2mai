/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.mai.intereceptors;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.meta.MaiMetaDataFactory;
import org.seasar.mai.util.FreeMarkerUtil;

import com.ozacc.mail.Mail;

/**
 * @author Satoshi Kimura
 */
public class S2MaiInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -3003054499497347849L;

    private Logger logger = Logger.getLogger(S2MaiInterceptor.class);

    private MaiMetaDataFactory maiMetaDataFactory;

    private S2Container container;

    private SendMail sendMail;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (isGetSendMail(method)) {
            return sendMail.clone();
        }
        if (!MethodUtil.isAbstract(method)) {
            return invocation.proceed();
        }
        init();
        MaiMetaData metaData = maiMetaDataFactory.getMaiMetaData(getTargetClass(invocation));
        Object data = getData(invocation);
        Mail mail = createMail(method, data, metaData);
        setMailProperty(mail, data);
        SendMail sendMail = (SendMail) this.sendMail.clone();
        setServerProperty(sendMail, data);
        send(mail, sendMail);
        return null;
    }

    private void setServerProperty(SendMail sendMail, Object data) {
        if (data == null) {
            return;
        }
        BeanDesc desc = BeanDescFactory.getBeanDesc(data.getClass());
        if (desc.hasPropertyDesc("host")) {
            PropertyDesc host = desc.getPropertyDesc("host");
            sendMail.setHost((String) host.getValue(data));
        }
        // TODO 他のプロパティーも
    }

    private void setMailProperty(Mail mail, Object data) {
        if (data == null) {
            return;
        }
        BeanDesc desc = BeanDescFactory.getBeanDesc(data.getClass());
        if (desc.hasPropertyDesc("from")) {
            PropertyDesc from = desc.getPropertyDesc("from");
            mail.setFrom((String) from.getValue(data));
        }
        // TODO 他のプロパティーも
    }

    private boolean isGetSendMail(Method method) {
        if ("getSendMail".equals(method.getName()) && SendMail.class.equals(method.getReturnType())) {
            return true;
        } else {
            return false;
        }
    }

    private Object getData(MethodInvocation invocation) {
        Object[] arguments = invocation.getArguments();
        if (arguments == null || arguments.length == 0) {
            return null;
        }
        Object data = arguments[0];
        if (data instanceof Map) {
            return data;
        }
        Map map = new HashMap();
        map.put(S2MaiConstants.DATA_NAME, data);
        return map;
    }

    private void send(Mail mail, SendMail sendMail) {
        logger.debug("send mail...");
        logger.debug(mail);
        sendMail.send(mail);
        logger.debug("success send mail.");
    }

    private Mail createMail(Method method, Object data, MaiMetaData metaData) {
        Mail mail = metaData.getMail(method);
        String path = metaData.getTemplatePath(method);
        String text = FreeMarkerUtil.processResource(path, data);
        String subject = getSubject(text);
        text = getText(text);
        mail.setSubject(subject);
        mail.setText(text);
        return mail;
    }

    private void init() {
        String enc = (String) container.getComponent(S2MaiConstants.TEMPLATE_ENCODING);
        FreeMarkerUtil.setDefaultEncoding(enc);
    }

    private String getText(String text) {
        if (text.startsWith(S2MaiConstants.SUBJECT) == false) {
            return text;
        }
        return text.substring(text.indexOf("\n") + "\r\n".length() + 1, text.length());
    }

    private String getSubject(String text) {
        if (text.startsWith(S2MaiConstants.SUBJECT) == false) {
            return null;
        }
        return text.substring(S2MaiConstants.SUBJECT.length(), text.indexOf("\r"));
    }

    public void setMaiMetaDataFactory(MaiMetaDataFactory maiMetaDataFactory) {
        this.maiMetaDataFactory = maiMetaDataFactory;
    }

    public void setSendMail(SendMail sendMail) {
        this.sendMail = sendMail;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

}
