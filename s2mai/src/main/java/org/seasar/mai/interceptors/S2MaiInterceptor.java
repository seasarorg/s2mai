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
package org.seasar.mai.interceptors;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.MailExceptionHandler;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.mail.impl.MailExceptionHandlerImpl;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.meta.MaiMetaDataFactory;
import org.seasar.mai.property.PropertyWriterForBean;
import org.seasar.mai.util.FreeMarkerUtil;

import com.ozacc.mail.Mail;
import com.ozacc.mail.MailException;

/**
 * @author Satoshi Kimura
 */
public class S2MaiInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -3003054499497347849L;

    private Logger logger = Logger.getLogger(S2MaiInterceptor.class);

    private MaiMetaDataFactory maiMetaDataFactory;

    private S2Container container;

    private SendMail sendMail;

    private MailExceptionHandler mailExceptionHandler = new MailExceptionHandlerImpl();
    
    private PropertyWriterForBean propertyWriter;

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
        Object bean = getBean(invocation);
        Object data = getData(bean);
        Mail mail = createMail(method, data, metaData);
        propertyWriter.setMailProperty(mail, bean);        
        SendMail sendMail = (SendMail) this.sendMail.clone();
        propertyWriter.setServerProperty(sendMail, bean);
        send(mail, sendMail);
        return null;
    }

    private boolean isGetSendMail(Method method) {
        if ("getSendMail".equals(method.getName()) && SendMail.class.equals(method.getReturnType())) {
            return true;
        } else {
            return false;
        }
    }
    
    private Object getBean(MethodInvocation invocation){
        Object[] arguments = invocation.getArguments();
        if (arguments == null || arguments.length == 0) {
            return null;
        }
        return arguments[0];
    }

    private Object getData(Object arg) {
        if(arg == null){
            return null;
        }
        
        if (arg instanceof Map) {
            return arg;
        }
        Map map = new HashMap();
        map.put(S2MaiConstants.DATA_NAME, arg);
        return map;
    }

    private void send(Mail mail, SendMail sendMail) {
        logger.debug("send mail...");
        logger.debug(mail);
        try {
            sendMail.send(mail);
        } catch (MailException e) {
            mailExceptionHandler.handle(e);
        }
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
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return text;
        }
        return text.substring(text.indexOf("\n") + "\r\n".length() + 1, text.length());
    }

    private String getSubject(String text) {
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return null;
        }
        return text.substring(S2MaiConstants.TEMPLATE_SUBJECT.length(), text.indexOf("\r"));
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

    public void setMailExceptionHandler(MailExceptionHandler mailExceptionHandler) {
        this.mailExceptionHandler = mailExceptionHandler;
    }

    /**
     * @param propertyWriter The propertyWriter to set.
     */
    public void setPropertyWriter(PropertyWriterForBean propertyWriter) {
        this.propertyWriter = propertyWriter;
    }

}
