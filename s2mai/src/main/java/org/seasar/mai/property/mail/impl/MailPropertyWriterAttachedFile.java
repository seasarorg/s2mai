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

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class MailPropertyWriterAttachedFile implements MailPropertyWriter{

    public void setProperty(Mail mail, Object value) {
        if(value instanceof File){
            mail.addFile((File)value);
        }else if(value instanceof AttachedFile){
            AttachedFile af = (AttachedFile)value;
            
            if(af.getFile() != null){
                if(af.getFileName() == null){
                    mail.addFile(af.getFile());
                }else{
                    mail.addFile(af.getFile(), af.getFileName());
                }
            }else if(af.getInputStream() != null){
                mail.addFile(af.getInputStream(), af.getFileName());
            }else if(af.getUrl() != null){
                mail.addFile(af.getUrl(), af.getFileName());
            }                     
            
        }else if(value instanceof List){
            
            Object addrValue = null;
            for(Iterator itr = ((List)value).iterator(); itr.hasNext();){
                addrValue = itr.next();
                this.setProperty(mail, addrValue);
            }
            
        }else if(value instanceof Object[]){
            
            List addrList = Arrays.asList((Object[])value);
            this.setProperty(mail, addrList);
        }        
    }

    public void init(Mail mail) {
        mail.clearFile();
    }

}
