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
package org.seasar.mai;

/**
 * @author Satsohi Kimura
 * @author rokugen
 */
public interface S2MaiConstants {
    String FREEMARKER_EXT = "ftl";

    String VELOCITY_EXT = "vm";

    String MAIL_PROPERTIES_DICON = "mailProperties.dicon";

    String DATA_NAME = "data";

    String TEMPLATE_SUBJECT = "Subject: ";

    String SUBJECT = "subject";

    String FROM = "from";

    String TO = "to";

    String CC = "cc";

    String BCC = "bcc";

    String REPLY_TO = "replyTo";

    String RETURN_PATH = "returnPath";

    String ATTACHED_FILE = "attachedFile";
    
    String XHEADER = "XHeader";

    String[] MAIL_PROPERTIES = new String[] { FROM, TO, CC, BCC, REPLY_TO, SUBJECT, RETURN_PATH,ATTACHED_FILE ,XHEADER};

    String HOST = "host";

    String PORT = "port";

    String USERNAME = "username";

    String PASSWORD = "password";

    String MESSAGE_ID = "messageId";

    String[] SERVER_PROPERTIES = new String[] { HOST, PORT, USERNAME, PASSWORD, MESSAGE_ID };

    String TEMPLATE_ENCODING = "templateEncoding";

    String MAIL_CHARSET = "mailCharset";
    
    String XHEADER_DELIMITER = ": ";

}
