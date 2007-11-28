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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.property.mail.MailPropertyWriter;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class MailPropertyWriterXHeader implements MailPropertyWriter {
    private static final String XHEADER_PREFIX = "X-";

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
            if(isAllowedKey(key) == false){
                continue;
            }
            String val = (String) map.get(key);
            addHeader(mail, key, val);
        }
    }

    private void addString(Mail mail, Object value) {

        String header = (String) value;
        BufferedReader reader = new BufferedReader(new StringReader(header));
        String line = null;        
        try {
            while ((line = reader.readLine()) != null) {
                addLine(line, mail);
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }        
    }
    
    private void addLine(String line, Mail mail){
        int index = line.indexOf(S2MaiConstants.XHEADER_DELIMITER);
        if(index < 0){
            return;
        }
        String key = line.substring(0, index);
        if(isAllowedKey(key) == false){
            return;
        }
        String val = line.substring(index + S2MaiConstants.XHEADER_DELIMITER.length());
        addHeader(mail, key, val);
    }
    
    private boolean isAllowedKey(String value){        
        return !StringUtil.isEmpty(value) && StringUtil.startsWithIgnoreCase(value,XHEADER_PREFIX);        
    }
    
    private void addHeader(Mail mail, String key, String value){
        if(isKeyAlreadyExists(mail, key)){
            return;
        }        
        key = capitalizePrefix(key);
        
        mail.addHeader(key, value);
        
    }
    
    private boolean isKeyAlreadyExists(Mail mail, String newKey){
        Map map = mail.getHeaders();
        if(map == null){
            return false;
        }
        Set keySet = map.keySet();
        for(Iterator itr = keySet.iterator(); itr.hasNext();){
            String key = (String) itr.next();
            if(key.equalsIgnoreCase(newKey)){
                return true;
            }
        }
        return false;
    }
    
    private String capitalizePrefix(String key){
        key = key.trim();
        key = key.replaceAll("^x-", XHEADER_PREFIX);
        return key;
        
    }
    
    

}
