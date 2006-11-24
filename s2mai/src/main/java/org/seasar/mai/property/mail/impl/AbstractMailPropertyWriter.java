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
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public abstract class AbstractMailPropertyWriter implements MailPropertyWriter{
    private S2Container container;

    
    public void setProperty(Mail mail, Object value){
        
        if(value instanceof String){
            
            this.setPropertyToMail(mail,(String)value);
            
        }else if(value instanceof InternetAddress){
            
            InternetAddress iaValue = (InternetAddress)value;
            try {
                //TODO もうっちょっとマシなやり方できないかなあと
                iaValue.setPersonal(this.toSafetyText(iaValue.getPersonal()));
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
            this.setPropertyToMail(mail,iaValue);
            
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
    
    private String toSafetyText(String text) {
        if (text == null) {
            text = "";
        }
        try {
            String charset = (String)container.getComponent(S2MaiConstants.MAIL_CHARSET);
            MimeUtility.encodeText(text, charset, "B"); 
            return text;
        } catch (BufferOverflowException e) {
            return toSafetyText(text + " ");
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
    
    protected abstract void setPropertyToMail(Mail mail, String value);
    protected abstract void setPropertyToMail(Mail mail, InternetAddress value);

    /**
     * @param container The container to set.
     */
    public final void setContainer(S2Container container) {
        this.container = container;
    }    

}
