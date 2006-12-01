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
package org.seasar.mai.property.mail.impl;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.MailAddress;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public abstract class AbstractMailPropertyWriter implements MailPropertyWriter {
    private S2Container container;

    public void setProperty(Mail mail, Object value) {
        if (value instanceof String) {
            setString(mail, value);
        } else if (value instanceof InternetAddress) {
            setInternetAddress(mail, value);
        } else if (value instanceof MailAddress) {
            setMailAddress(mail, value);
        } else if (value instanceof List) {
            setList(mail, value);
        } else if (value instanceof Object[]) {
            setList(mail, Arrays.asList((Object[]) value));
        }
    }

    private void setList(Mail mail, Object value) {
        List listValue = (List) value;
        Object addrValue = null;
        for (Iterator itr = listValue.iterator(); itr.hasNext();) {
            addrValue = itr.next();
            this.setProperty(mail, addrValue);
        }
    }

    private void setMailAddress(Mail mail, Object value) {
        MailAddress maValue = (MailAddress) value;
        if (StringUtil.isEmpty(maValue.getPersonal())) {
            this.setPropertyToMail(mail, maValue.getAddress());
        } else {
            this.setPropertyToMail(mail, maValue);
        }
    }

    private void setInternetAddress(Mail mail, Object value) {
        InternetAddress iaValue = (InternetAddress) value;
        try {
            // TODO もうっちょっとマシなやり方できないかなあと
            iaValue.setPersonal(this.toSafetyText(iaValue.getPersonal()));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        this.setPropertyToMail(mail, iaValue);
    }

    private void setString(Mail mail, Object value) {
        this.setPropertyToMail(mail, (String) value);
    }

    private String toSafetyText(String text) {
        if (text == null) {
            text = "";
        }
        try {
            String charset = (String) container.getComponent(S2MaiConstants.MAIL_CHARSET);
            MimeUtility.encodeText(text, charset, "B");
            return text;
        } catch (BufferOverflowException e) {
            return toSafetyText(text + " ");
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    protected abstract void setPropertyToMail(Mail mail, String value);

    protected abstract void setPropertyToMail(Mail mail, MailAddress mailAddress);

    protected abstract void setPropertyToMail(Mail mail, InternetAddress value);

    /**
     * @param container
     *            The container to set.
     */
    public final void setContainer(S2Container container) {
        this.container = container;
    }

}
