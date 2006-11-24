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
package org.seasar.mai.property.server.impl;

import org.seasar.mai.mail.SendMail;
import org.seasar.mai.property.server.ServerPropertyWriter;


/**
 * @author rokugen
 */
public abstract class AbstractServerPropertyWriter implements ServerPropertyWriter {

    public void setProperty(SendMail sendMail, Object value) {
        if(value instanceof String){
            
            this.setPropertyToMail(sendMail,(String)value);
            
        }else if(value instanceof Integer){
            Integer intValue = (Integer)value;
            this.setPropertyToMail(sendMail,intValue.toString());
            
        }

    }

    protected abstract void setPropertyToMail(SendMail sendMail, String value);

}
