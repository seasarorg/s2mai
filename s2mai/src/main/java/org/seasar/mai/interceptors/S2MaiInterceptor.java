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
package org.seasar.mai.interceptors;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.mail.Transport;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.meta.MaiMetaDataFactory;
import org.seasar.mai.property.PropertyWriterForBean;
import org.seasar.mai.template.ContextHelper;
import org.seasar.mai.template.TemplateProcessor;

import com.ozacc.mail.Mail;

/**
 * @author Satoshi Kimura
 */
public class S2MaiInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -3003054499497347849L;

    // private Logger logger = Logger.getLogger(S2MaiInterceptor.class);

    private MaiMetaDataFactory maiMetaDataFactory;

    private SendMail sendMail;

    private PropertyWriterForBean propertyWriter;

    private ContextHelper contextHelper;

    private TemplateProcessor templateProcessor;

    private Transport transport;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (isGetSendMail(method)) {
            return sendMail.clone();
        }
        if (!MethodUtil.isAbstract(method)) {
            return invocation.proceed();
        }
        sendMail(invocation);
        return null;
    }

    private void sendMail(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        init();
        MaiMetaData metaData = maiMetaDataFactory.getMaiMetaData(getTargetClass(invocation));
        Object bean = getBean(invocation);
        Object context = contextHelper.createContext(bean);
        Mail mail = createMail(method, context, metaData);
        propertyWriter.setMailProperty(mail, bean);
        SendMail mailSender = (SendMail) sendMail.clone();
        propertyWriter.setServerProperty(mailSender, bean);
        send(mail, mailSender);
    }

    private boolean isGetSendMail(Method method) {
        if ("getSendMail".equals(method.getName()) && SendMail.class.equals(method.getReturnType())) {
            return true;
        } else {
            return false;
        }
    }

    private Object getBean(MethodInvocation invocation) {
        Object[] arguments = invocation.getArguments();
        if (arguments == null || arguments.length == 0) {
            return null;
        }
        return arguments[0];
    }

    private void send(Mail mail, SendMail sendMail) {
        transport.send(mail, sendMail);
    }

    private Mail createMail(Method method, Object context, MaiMetaData metaData) {
        Mail mail = metaData.getMail(method);
        String path = metaData.getTemplatePath(method);
        String text = templateProcessor.processResource(path, context);
        String subject = getSubject(text);
        text = getText(text);
        if (subject != null) {
            mail.setSubject(subject);
        }
        mail.setText(text);
        return mail;
    }

    private void init() {
        templateProcessor.init();
    }

    private String getText(String text) {
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return text;
        }

        return text.replaceFirst(".*((\r\n\r\n)|(\n\n)|(\r\r))", "");
    }

    private String getSubject(String text) {
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return null;
        }
        LineNumberReader reader = new LineNumberReader(new StringReader(text));
        reader.setLineNumber(0);
        String subjectLine = null;
        try {
            subjectLine = reader.readLine();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return subjectLine.substring(S2MaiConstants.TEMPLATE_SUBJECT.length(), subjectLine.length());
    }

    public void setMaiMetaDataFactory(MaiMetaDataFactory maiMetaDataFactory) {
        this.maiMetaDataFactory = maiMetaDataFactory;
    }

    public void setSendMail(SendMail sendMail) {
        this.sendMail = sendMail;
    }

    /**
     * @param propertyWriter
     *            The propertyWriter to set.
     */
    public void setPropertyWriter(PropertyWriterForBean propertyWriter) {
        this.propertyWriter = propertyWriter;
    }

    public void setContextHelper(ContextHelper contextHelper) {
        this.contextHelper = contextHelper;
    }

    public void setTemplateProcessor(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

}
