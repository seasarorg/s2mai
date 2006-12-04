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

import org.seasar.framework.container.S2Container;
import org.seasar.mai.mail.SendMail;

/**
 * @author Satsohi Kimura
 */
public class SendMailImpl extends com.ozacc.mail.impl.SendMailImpl implements SendMail, Cloneable {
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

    private static final int DEFAULT_READ_TIMEOUT = 5000;

    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    private String messageId;

    private int readTimeout = DEFAULT_READ_TIMEOUT;
    
    private S2Container container;

    public SendMailImpl() {
        super();
    }

    public SendMailImpl(String host) {
        super(host);
    }

    public SendMailImpl(SendMailImpl sendMail) {
        copyProperties(this, sendMail);
    }

    private void copyProperties(SendMailImpl src, SendMail dest) {
        dest.setHost(src.getHost());
        dest.setPort(src.getPort());
        dest.setProtocol(src.getProtocol());
        dest.setUsername(src.getUsername());
        dest.setPassword(src.getPassword());
        dest.setReturnPath(src.getReturnPath());
        dest.setCharset(src.getCharset());
        dest.setConnectionTimeout(src.getConnectionTimeout());
        dest.setMessageId(src.getMessageId());
        dest.setReadTimeout(src.getReadTimeout());
    }

    public Object clone() {
        SendMail sendMail = (SendMail) container.getComponent(SendMail.class);
        copyProperties(this, sendMail);
        return sendMail;
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

    public void setContainer(S2Container container) {
        this.container = container;
    }

}
