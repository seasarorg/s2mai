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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class MailPropertyWriterXHeaderTest extends TestCase {
    private MailPropertyWriterXHeader writer;

    public MailPropertyWriterXHeaderTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        writer = new MailPropertyWriterXHeader();
        super.setUp();
    }
    
    public void testXHeaderにMapから設定(){
        Mail mail = new Mail();
        Map expected = getExpected();
        
        writer.setProperty(mail, expected);
        Map actual = mail.getHeaders();
        
        assertEquals("size",expected.size(), actual.size());
        assertNotNull("foo not null",actual.get("X-foo"));
        assertNotNull("bar not null",actual.get("X-bar"));
        assertNotNull("mailer not null",actual.get("X-Mailer"));
        
        assertEquals("foo",expected.get("X-foo"), actual.get("X-foo"));
        assertEquals("bar",expected.get("X-bar"), actual.get("X-bar"));
        assertEquals("mailer",expected.get("X-Mailer"), actual.get("X-Mailer"));
        
    }
    
    public void testStringから設定(){
        Mail mail = new Mail();
        Map expected = getExpected();        
        String value = buildHeaderString(expected, "\r\n");
        
        writer.setProperty(mail, value);
        
        Map actual = mail.getHeaders();
        
        assertEquals("size",expected.size(), actual.size());
        assertNotNull("foo not null",actual.get("X-foo"));
        assertNotNull("bar not null",actual.get("X-bar"));
        assertNotNull("mailer not null",actual.get("X-Mailer"));

        assertEquals("foo",expected.get("X-foo"), actual.get("X-foo"));
        assertEquals("bar",expected.get("X-bar"), actual.get("X-bar"));
        assertEquals("mailer",expected.get("X-Mailer"), actual.get("X-Mailer"));
        
    }
    
    public void testStringから設定_改行コードがLF(){
        Mail mail = new Mail();
        Map expected = getExpected();        
        String value = buildHeaderString(expected, "\n");
        
        writer.setProperty(mail, value);
        
        Map actual = mail.getHeaders();
        
        assertEquals("size",expected.size(), actual.size());
        assertNotNull("foo not null",actual.get("X-foo"));
        assertNotNull("bar not null",actual.get("X-bar"));
        assertNotNull("mailer not null",actual.get("X-Mailer"));

        assertEquals("foo",expected.get("X-foo"), actual.get("X-foo"));
        assertEquals("bar",expected.get("X-bar"), actual.get("X-bar"));
        assertEquals("mailer",expected.get("X-Mailer"), actual.get("X-Mailer"));
        
    }
    
    public void testStringから設定_改行コードがCR(){
        Mail mail = new Mail();
        Map expected = getExpected();        
        String value = buildHeaderString(expected, "\r");
        
        writer.setProperty(mail, value);
        
        Map actual = mail.getHeaders();
        
        assertEquals("size",expected.size(), actual.size());
        assertNotNull("foo not null",actual.get("X-foo"));
        assertNotNull("bar not null",actual.get("X-bar"));
        assertNotNull("mailer not null",actual.get("X-Mailer"));

        assertEquals("foo",expected.get("X-foo"), actual.get("X-foo"));
        assertEquals("bar",expected.get("X-bar"), actual.get("X-bar"));
        assertEquals("mailer",expected.get("X-Mailer"), actual.get("X-Mailer"));
        
    }
    
    public void testStringから設定_値にコロンが入ってた場合(){
        Mail mail = new Mail();
        Map expected = getExpectedWithColon();
        String value = buildHeaderString(expected, "\r\n");
        
        writer.setProperty(mail, value);
        
        Map actual = mail.getHeaders();
        
        assertEquals("size",expected.size(), actual.size());
        assertNotNull("foo not null",actual.get("X-foo"));
        assertNotNull("bar not null",actual.get("X-bar"));
        assertNotNull("mailer not null",actual.get("X-Mailer"));

        assertEquals("foo",expected.get("X-foo"), actual.get("X-foo"));
        assertEquals("bar",expected.get("X-bar"), actual.get("X-bar"));
        assertEquals("mailer",expected.get("X-Mailer"), actual.get("X-Mailer"));
        
    }
    
    public void testMapから設定_Mapが空っぽ(){
        Mail mail = new Mail();
        Map expected = new HashMap();

        writer.setProperty(mail, expected);        
        Map actual = mail.getHeaders();        
        assertNull(actual);
        
    }
    
    public void testMapから設定_キーが空っぽ(){
        Mail mail = new Mail();
        Map value = new HashMap();
        value.put(null, "hoge");
        value.put("", "fuga");
        value.put(" ", "funga-");
        writer.setProperty(mail, value);
        System.out.println(mail);
        Map actual = mail.getHeaders();        
        assertNull(actual);
        
    }
    
    public void testStringがコロンまるでなし(){
        Mail mail = new Mail();
        String value = "hogehoge";

        writer.setProperty(mail, value);
        Map actual = mail.getHeaders();
        assertNull(actual);
        
    }
    
    public void testStringが空っぽ(){
        Mail mail = new Mail();
        String value = "";

        writer.setProperty(mail, value);
        Map actual = mail.getHeaders();
        assertNull(actual);        
    }
    
    public void testXで始まらないキーは設定されない(){
        Mail mail = new Mail();
        Map value = new HashMap();
        value.put("Subject", "hoge");
        value.put("X-Subject", "foo");
        value.put("Z-Subject", "bar");

        writer.setProperty(mail, value);        
        Map actual = mail.getHeaders();        
        assertNull(actual.get("Subject"));
        assertEquals("foo", actual.get("X-Subject"));
        assertNull(actual.get("Z-Subject"));
        
        String str = buildHeaderString(value, "\n");
        mail = new Mail();
        writer.setProperty(mail, str);
        actual = mail.getHeaders();        
        assertNull(actual.get("Subject"));
        assertEquals("foo", actual.get("X-Subject"));
        assertNull(actual.get("Z-Subject"));
        
        //ケースセンシティブなんだと思う。
        mail = new Mail();
        value = new HashMap();        
        value.put("x-Subject", "bar");
        value.put("X-Subject", "foo");
        writer.setProperty(mail, value);        
        actual = mail.getHeaders();        
        assertNull(actual.get("x-Subject"));
        assertEquals("foo", actual.get("X-Subject"));
        
        
    }
    
    private Map getExpected(){
        Map expected = new HashMap();
        expected.put("X-foo", "TEST0001@example.com");
        expected.put("X-bar", "2.3");
        expected.put("X-Mailer", "hogeMailer");
        return expected;        
    }
    
    private Map getExpectedWithColon(){
        Map expected = new HashMap();
        expected.put("X-foo", "TEST0001@example.com: hoge");
        expected.put("X-bar", "2.3");
        expected.put("X-Mailer", "hogeMailer");
        return expected;        
    }
    
    private String buildHeaderString(Map map, String lineFeed){
        StringBuffer buf = new StringBuffer();
        Set keys = map.keySet();
        for(Iterator itr = keys.iterator(); itr.hasNext();){
            String key = (String) itr.next();
            String val = (String) map.get(key);
            buf.append(key + ": " + val).append(lineFeed);
        }
        return buf.toString();
    }
    


}


