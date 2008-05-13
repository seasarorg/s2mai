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
package org.seasar.mai.property.mail.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.property.mail.MailPropertyWriter;

/**
 * @author rokugen
 */
public class MailPropertyWriterAttachedFile implements MailPropertyWriter {

    public void setProperty(Mail mail, Object value) {
        if (value instanceof File) {
            addFile(mail, value);
        } else if (value instanceof AttachedFile) {
            addAttachedFile(mail, (AttachedFile) value);
        } else if (value instanceof List) {
            addList(mail, value);
        } else if (value instanceof Object[]) {
            addList(mail, Arrays.asList((Object[]) value));
        }
    }

    private void addList(Mail mail, Object value) {
        List listValue = (List) value;
        for (Iterator itr = listValue.iterator(); itr.hasNext();) {
            this.setProperty(mail, itr.next());
        }
    }

    private void addAttachedFile(Mail mail, AttachedFile af) {
        if (af.getFile() != null) {
            if (af.getFileName() == null) {
                mail.addFile(af.getFile());
            } else {
                mail.addFile(af.getFile(), af.getFileName());
            }
        } else if (af.getInputStream() != null) {
            mail.addFile(af.getInputStream(), af.getFileName());
        } else if (af.getUrl() != null) {
            mail.addFile(af.getUrl(), af.getFileName());
        }
    }

    private void addFile(Mail mail, Object value) {
        mail.addFile((File) value);
    }

    public void init(Mail mail) {
        mail.clearFile();
    }

}
