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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class MailPropertyWriterXHeader implements MailPropertyWriter {

    public void init(Mail mail) {
        // no code.
    }

    public void setProperty(Mail mail, Object value) {
        if (value instanceof Map) {
            addMap(mail, value);
        } else if (value instanceof String) {
            addString(mail, value);
        }
    }

    private void addMap(Mail mail, Object value) {
        Map map = (Map) value;
        Set keys = map.keySet();
        for (Iterator itr = keys.iterator(); itr.hasNext();) {
            String key = (String) itr.next();
            String val = (String) map.get(key);
            mail.addHeader(key, val);
        }
    }

    private void addString(Mail mail, Object value) {

        String header = (String) value;
        BufferedReader reader = new BufferedReader(new StringReader(header));
        String line = null;
        Map map = new HashMap();
        try {
            while ((line = reader.readLine()) != null) {
                addHeaderToMap(line, map);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        addMap(mail, map);
    }
    
    private void addHeaderToMap(String line, Map map){
        int index = line.indexOf(S2MaiConstants.XHEADER_DELIMITER);
        if(index < 0){
            return;
        }
        String key = line.substring(0, index);
        String val = line.substring(index + S2MaiConstants.XHEADER_DELIMITER.length());
        map.put(key, val);
    }
    
    

}
