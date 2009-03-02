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

import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.AnnotationReaderFactory;
import org.seasar.mai.property.PropertyWriterForAnnotation;
import org.seasar.mai.property.mail.MailPropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class PropertyWriterForAnnotationImpl implements PropertyWriterForAnnotation {
    private MailPropertyWriterFactory mailPropertyWriterFactory;

    private AnnotationReaderFactory annotationReaderFactory;

    public void setMailProperty(Mail mail, Method method) {
        AnnotationReader annotationReader = annotationReaderFactory.createMaiAnnotationReader();
        Object value = annotationReader.getTo(method);
        setValueToMail(S2MaiConstants.TO, value, mail);
        value = annotationReader.getCc(method);
        setValueToMail(S2MaiConstants.CC, value, mail);
        value = annotationReader.getBcc(method);
        setValueToMail(S2MaiConstants.BCC, value, mail);
        value = annotationReader.getSubject(method);
        setValueToMail(S2MaiConstants.SUBJECT, value, mail);
        value = annotationReader.getFrom(method);
        setValueToMail(S2MaiConstants.FROM, value, mail);
        value = annotationReader.getReplyTo(method);
        setValueToMail(S2MaiConstants.REPLY_TO, value, mail);
        value = annotationReader.getReturnPath(method);
        setValueToMail(S2MaiConstants.RETURN_PATH, value, mail);
    }

    private void setValueToMail(String propertyName, Object value, Mail mail) {
        MailPropertyWriter writer = mailPropertyWriterFactory.getMailPropertyWriter(propertyName);

        if (value == null) {
            return;
        }
        writer.init(mail);
        writer.setProperty(mail, value);
    }

    /**
     * @param mailPropertyWriterFactory
     *            The mailPropertyWriterFactory to set.
     */
    public final void setMailPropertyWriterFactory(MailPropertyWriterFactory mailPropertyWriterFactory) {
        this.mailPropertyWriterFactory = mailPropertyWriterFactory;
    }

    /**
     * @param annotationReaderFactory
     *            The annotationReaderFactory to set.
     */
    public final void setAnnotationReaderFactory(AnnotationReaderFactory annotationReaderFactory) {
        this.annotationReaderFactory = annotationReaderFactory;
    }

}
