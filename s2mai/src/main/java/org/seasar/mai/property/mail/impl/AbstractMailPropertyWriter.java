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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public abstract class AbstractMailPropertyWriter implements MailPropertyWriter{


    public void setProperty(Mail mail, Object data) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(data.getClass());
        PropertyDesc pd = beanDesc.getPropertyDesc(this.getPropertyName());            
        Object value = pd.getValue(data);
        if(value != null){
            this.init(mail);
            this.setPropertyToMail(mail,value);
        }
    }
    
    private void setPropertyToMail(Mail mail, Object value){
        if(value instanceof String){ 
            this.setPropertyImpl(mail,(String)value);            
        }else if(value instanceof InternetAddress){
            InternetAddress iaValue = (InternetAddress)value;
            try {
                //TODO ISO-2022-JP外出し
                iaValue.setPersonal(iaValue.getPersonal(),"ISO-2022-JP");
            } catch (UnsupportedEncodingException e) {
                // TODO キャッチしてどうしよう。
                e.printStackTrace();
            }
            this.setPropertyImpl(mail,iaValue);
        }else if(value instanceof List){
            Object addrValue = null;
            for(Iterator itr = ((List)value).iterator(); itr.hasNext();){
                addrValue = itr.next();
                this.setPropertyToMail(mail, addrValue);
            }
        }else if(value instanceof Object[]){
            List addrList = Arrays.asList((Object[])value);
            this.setPropertyToMail(mail, addrList);
        }
    }
    
    protected abstract void init(Mail mail);
    
    protected abstract String getPropertyName();
    
    protected abstract void setPropertyImpl(Mail mail, String value);
    protected abstract void setPropertyImpl(Mail mail, InternetAddress value);    

}
