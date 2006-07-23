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
package org.seasar.mai.meta.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceNotFoundRuntimeException;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.meta.MaiMetaData;

import com.ozacc.mail.Mail;

/**
 * @author Satsohi Kimura
 */
public class MaiMetaDataImpl implements MaiMetaData {
    private Map mails = new HashMap();

    private Map templatePaths = new HashMap();

    public MaiMetaDataImpl(Class maiClass, AnnotationReader annotationReader) {
        Mail classMail = null;
        try {
            String path = maiClass.getName().replaceAll("\\.", "/") + ".dicon";
            S2Container container = S2ContainerFactory.create(path);
            classMail = (Mail) container.getComponent(Mail.class);
        } catch (ResourceNotFoundRuntimeException e) {
            classMail = null;
        }
        Method[] methods = maiClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Mail mail = getMail(maiClass, classMail, method);

            mails.put(method, mail);

            setAnnotationValues(annotationReader, method, mail);
        }
    }

    private void setAnnotationValues(AnnotationReader annotationReader, Method method, Mail mail) {
        String[] to = annotationReader.getTo(method);
        setTo(to, mail);
        String[] cc = annotationReader.getCc(method);
        setCc(cc, mail);
        String[] bcc = annotationReader.getBcc(method);
        setBcc(bcc, mail);
        String subject = annotationReader.getSubject(method);
        setSubject(subject, mail);
        String from = annotationReader.getFrom(method);
        setFrom(from, mail);
        String replyTo = annotationReader.getReplyTo(method);
        setReplyTo(replyTo, mail);
        String returnPath = annotationReader.getReturnPath(method);
        setReturnPath(returnPath, mail);
    }

    private Mail getMail(Class maiClass, Mail classMail, Method method) {
        Mail mail = null;
        try {
            String path = maiClass.getName().replaceAll("\\.", "/") + "_" + method.getName() + ".dicon";
            S2Container container = S2ContainerFactory.create(path);
            mail = (Mail) container.getComponent(Mail.class);
        } catch (ResourceNotFoundRuntimeException e) {
            mail = classMail;
        }
        if (mail == null) {
            mail = new Mail();
        }
        return mail;
    }

    private void setBcc(String[] bcc, Mail mail) {
        if (bcc == null || bcc.length == 0) {
            return;
        }
        mail.clearBcc();
        for (int i = 0; i < bcc.length; i++) {
            String val = bcc[i].trim();
            mail.addBcc(toSafetyText(val));
        }
    }

    private void setCc(String[] cc, Mail mail) {
        if (cc == null || cc.length == 0) {
            return;
        }
        mail.clearCc();
        for (int i = 0; i < cc.length; i++) {
            String val = cc[i].trim();
            mail.addCc(toSafetyText(val));
        }
    }

    private void setTo(String[] to, Mail mail) {
        if (to == null || to.length == 0) {
            return;
        }
        mail.clearTo();
        for (int i = 0; i < to.length; i++) {
            String val = to[i].trim();
            mail.addTo(val, toSafetyText(val));
        }
    }

    private void setReplyTo(String replyTo, Mail mail) {
        if (replyTo == null) {
            return;
        }
        mail.setReplyTo(toSafetyText(replyTo));
    }

    private void setReturnPath(String returnPath, Mail mail) {
        if (returnPath == null) {
            return;
        }
        mail.setReturnPath(toSafetyText(returnPath));
    }

    private void setSubject(String subject, Mail mail) {
        if (subject == null) {
            return;
        }
        mail.setSubject(toSafetyText(subject));
    }

    private void setFrom(String from, Mail mail) {
        if (from == null) {
            return;
        }
        mail.setFrom(from);
    }

    public Mail getMail(Method method) {
        return (Mail) mails.get(method);
    }

    private String toSafetyText(String text) {
        if (text == null) {
            text = "";
        }
        try {
            MimeUtility.encodeText(text, "ISO-2022-JP", "B"); // TODO ISO-2022-JPを外出し
            return text;
        } catch (BufferOverflowException e) {
            return toSafetyText(text + " ");
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public String getTemplatePath(Method method) {
        String path = (String) templatePaths.get(method);
        if (path != null) {
            return path;
        }
        Class maiClass = method.getDeclaringClass();
        path = maiClass.getName().replaceAll("\\.", "/") + "_" + method.getName() + "." + S2MaiConstants.FREEMARKER_EXT;

        templatePaths.put(method, path);

        return path;
    }

}
