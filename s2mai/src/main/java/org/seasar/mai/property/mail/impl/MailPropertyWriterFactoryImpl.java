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

import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.property.mail.MailPropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;

/**
 * @author rokugen
 */
public class MailPropertyWriterFactoryImpl implements MailPropertyWriterFactory, S2MaiConstants {
    private S2Container container;

    public MailPropertyWriter getMailPropertyWriter(String propName) {
        
        if(isMailProperty(propName) == false){
            return null;
        }
        
        try {
            return (MailPropertyWriter) container.getComponent(propName + "Writer");
        } catch (ComponentNotFoundRuntimeException ignore) {
        }
        return null;
    }
    
    protected boolean isMailProperty(String propName){        
        for(int i =0; i < MAIL_PROPERTIES.length; i++){
            if(MAIL_PROPERTIES[i].equals(propName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param container
     *            The container to set.
     */
    public final void setContainer(S2Container container) {
        this.container = container;
    }

}
