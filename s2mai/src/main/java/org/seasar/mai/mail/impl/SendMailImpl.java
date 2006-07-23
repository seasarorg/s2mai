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
package org.seasar.mai.mail.impl;

import org.seasar.mai.mail.SendMail;

/**
 * @author Satsohi Kimura
 */
public class SendMailImpl extends com.ozacc.mail.impl.SendMailImpl implements SendMail, Cloneable {
    private int connectionTimeout = 5000;

    private String messageId;

    private int readTimeout = 5000;

    public SendMailImpl() {
        super();
    }

    public SendMailImpl(String host) {
        super(host);
    }

    public SendMailImpl(SendMailImpl sendMail) {
        setHost(sendMail.getHost());
        setPort(sendMail.getPort());
        setProtocol(sendMail.getProtocol());
        setUsername(sendMail.getUsername());
        setPassword(sendMail.getPassword());
        setReturnPath(sendMail.getReturnPath());
        setCharset(sendMail.getCharset());
        setConnectionTimeout(sendMail.getConnectionTimeout());
        setMessageId(sendMail.getMessageId());
        setReadTimeout(sendMail.getReadTimeout());
    }

    public Object clone() {
        return new SendMailImpl(this);
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        super.setConnectionTimeout(connectionTimeout);
        this.connectionTimeout = connectionTimeout;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        super.setMessageId(messageId);
        this.messageId = messageId;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        super.setReadTimeout(readTimeout);
        this.readTimeout = readTimeout;
    }

}
