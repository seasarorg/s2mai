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

import java.util.HashMap;
import java.util.Map;

import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.property.mail.MailPropertyWriter;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;

/**
 * @author rokugen
 */
public class MailPropertyWriterFactoryImpl implements MailPropertyWriterFactory ,S2MaiConstants{
    private Map writerMap = new HashMap();
    
    public MailPropertyWriterFactoryImpl(){
        writerMap.put(FROM, new MailPropertyWriterFrom());
        writerMap.put(TO, new MailPropertyWriterTo());
        writerMap.put(CC, new MailPropertyWriterCc());
    }

    public MailPropertyWriter getMailPropertyWriter(String propName) {
        
        return (MailPropertyWriter)writerMap.get(propName);
    }

}
