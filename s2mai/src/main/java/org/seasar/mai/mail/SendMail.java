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
package org.seasar.mai.mail;

/**
 * @author Satsohi Kimura
 */
public interface SendMail extends com.ozacc.mail.SendMail {
    Object clone();

    String getCharset();

    String getHost();

    String getPassword();

    int getPort();

    String getProtocol();

    String getReturnPath();

    String getUsername();

    int getConnectionTimeout();

    String getMessageId();

    int getReadTimeout();

    void setCharset(String charset);

    void setConnectionTimeout(int connectionTimeout);

    void setHost(String host);

    void setMessageId(String messageId);

    void setPassword(String password);

    void setPort(int port);

    void setProtocol(String protocol);

    void setReadTimeout(int readTimeout);

    void setReturnPath(String returnPath);

    void setUsername(String username);
}
