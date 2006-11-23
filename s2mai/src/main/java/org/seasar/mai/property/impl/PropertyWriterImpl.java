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
package org.seasar.mai.property.impl;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.property.PropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class PropertyWriterImpl implements PropertyWriter,S2MaiConstants {
    private MailPropertyWriterFactory mailPropertyWriterFactory;

    public void setMailProperty(Mail mail, Object bean) {
        if (bean == null) {
            return;
        }
        
        BeanDesc desc = BeanDescFactory.getBeanDesc(bean.getClass());
        MailPropertyWriter propWriter = null;
        if (desc.hasPropertyDesc(FROM)) {        
            propWriter = mailPropertyWriterFactory.getMailPropertyWriter(FROM);
            propWriter.setProperty(mail,bean);
        }
        
        if (desc.hasPropertyDesc(TO)) {            
            propWriter = mailPropertyWriterFactory.getMailPropertyWriter(TO);
            propWriter.setProperty(mail,bean);
        }
        
        if (desc.hasPropertyDesc(CC)) {
            propWriter = mailPropertyWriterFactory.getMailPropertyWriter(CC);
            propWriter.setProperty(mail,bean);
        }
    }

    public void setServerProperty(SendMail sendMail, Object bean) {
        if (bean == null) {
            return;
        }        
        
        BeanDesc desc = BeanDescFactory.getBeanDesc(bean.getClass());
        if(desc.hasPropertyDesc(HOST)){
            setSendMailProperty(HOST,sendMail, bean);
        }
        if(desc.hasPropertyDesc(PORT)){
            setSendMailProperty(PORT,sendMail, bean);
        }
        if(desc.hasPropertyDesc(USERNAME)){
            setSendMailProperty(USERNAME,sendMail, bean);
        }
        if(desc.hasPropertyDesc(PASSWORD)){
            setSendMailProperty(PASSWORD,sendMail, bean);
        }
    }
    
    private void setSendMailProperty(String propName, SendMail target, Object bean){
        
        BeanDesc targetDesc = BeanDescFactory.getBeanDesc(target.getClass());
        PropertyDesc targetPropDesc = targetDesc.getPropertyDesc(propName);
        
        BeanDesc desc = BeanDescFactory.getBeanDesc(bean.getClass());
        PropertyDesc propDesc = desc.getPropertyDesc(propName);
        
        targetPropDesc.setValue(target, propDesc.getValue(bean));        
    }

    /**
     * @param mailPropertyWriterFactory The mailPropertyWriterFactory to set.
     */
    public void setMailPropertyWriterFactory(MailPropertyWriterFactory mailPropertyWriterFactory) {
        this.mailPropertyWriterFactory = mailPropertyWriterFactory;
    }
    


}
