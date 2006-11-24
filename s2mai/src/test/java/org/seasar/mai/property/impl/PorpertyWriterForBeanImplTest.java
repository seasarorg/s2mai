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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.impl.SendMailImpl;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class PorpertyWriterForBeanImplTest extends S2TestCase {
    private PropertyWriterForBeanImpl propertyWriterImpl;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PorpertyWriterForBeanImplTest.class);
    }

    protected void setUp() throws Exception {
        include("PropertyWriterForBeanImplTest.dicon");
    }
    
    public void testSetSendMailProperty(){
        SendMailImpl sendMail = new SendMailImpl();
        TestDto testDto = new TestDto();
        testDto.setHost("test@hoge.jp");
        testDto.setPort("1234");
        testDto.setUsername("tesetuser");
        testDto.setPassword("testpw");
        
        propertyWriterImpl.setServerProperty(sendMail,testDto);
        
        assertEquals("host", sendMail.getHost(), testDto.getHost());
        assertEquals("port",sendMail.getPort(), Integer.parseInt(testDto.getPort()));
        assertEquals("username",sendMail.getUsername(),testDto.getUsername());
        assertEquals("password",sendMail.getPassword(), testDto.getPassword());
    }
    
    public void testSetMailProperty() throws UnsupportedEncodingException{
        Mail mail = new Mail();
        TestDto testDto = new TestDto();
        testDto.setFrom("from@address");
        
        List toList = new ArrayList();        
        toList.add("to1@localhost.locadomain");
        InternetAddress toAddr = new InternetAddress();
        toAddr.setAddress("to2@localhost.localdomain");
        toAddr.setPersonal("テスト送信先");
        toList.add(toAddr);        
        testDto.setTo(toList);
        
        List ccList = new ArrayList();
        ccList.add("cc@address");        
        testDto.setCc(ccList);
        
        List bccList = new ArrayList();
        bccList.add("bcc@address1");        
        InternetAddress bccAddr = new InternetAddress();
        bccAddr.setAddress("bcc@address2");
        bccAddr.setPersonal("BCC送信先2");
        bccList.add(bccAddr);
        testDto.setBcc(bccList);
        
        testDto.setReplyTo("replyTo@address");
        
        propertyWriterImpl.setMailProperty(mail,testDto);
        assertEquals("from address only", testDto.getFrom(),mail.getFrom().getAddress());
        
        InternetAddress[] actualToAddrs = mail.getTo();
        InternetAddress[] actualCcAddrs = mail.getCc();
        InternetAddress[] actualBccAddrs = mail.getBcc();
        
        
        assertEquals("to address 1", (String)toList.get(0), actualToAddrs[0].getAddress());
        assertEquals("to address 2", ((InternetAddress)toList.get(1)).getAddress(), actualToAddrs[1].getAddress());
        assertEquals("to name 2", ((InternetAddress)toList.get(1)).getPersonal(), actualToAddrs[1].getPersonal());
        assertEquals("cc address 1", (String)ccList.get(0), actualCcAddrs[0].getAddress());
        assertEquals("bcc address 1", (String)bccList.get(0), actualBccAddrs[0].getAddress());
        assertEquals("bcc address 2", ((InternetAddress)bccList.get(1)).getAddress(), actualBccAddrs[1].getAddress());
        assertEquals("bcc name 2", ((InternetAddress)bccList.get(1)).getPersonal(), actualBccAddrs[1].getPersonal());
        assertEquals("replyTo address 1", testDto.getReplyTo(), mail.getReplyTo().getAddress());
        
        
        
        mail = new Mail();
        TestDto2 testDto2 = new TestDto2();
        InternetAddress from = new InternetAddress();
        from.setAddress("test@mail.address");
        from.setPersonal("test user");
        testDto2.setFrom(from);
        testDto2.setTo("to@address");
        
        propertyWriterImpl.setMailProperty(mail,testDto2);
        assertEquals("from address", testDto2.getFrom().getAddress(),mail.getFrom().getAddress());
        assertEquals("from name", testDto2.getFrom().getPersonal(),mail.getFrom().getPersonal());
        assertEquals("to count", 1, mail.getTo().length);
        assertEquals("to address", testDto2.getTo(), mail.getTo()[0].getAddress());
        
        //配列の場合とか
        mail = new Mail();
        TestDto3 testDto3 = new TestDto3();
        Object[] tos = new Object[]{
                "test@address",new InternetAddress("test2@address","送信先")  };
        testDto3.setTo(tos);
        String[] ccs = new String[]{
                "cc1@address","cc2@address"};
        testDto3.setCc(ccs);
        
        InternetAddress[] bccs = new InternetAddress[]{
            new InternetAddress("bcc1@address", "BCC送信先1"),
            new InternetAddress("bcc2@address", "BCC送信先2")
        };
        testDto3.setBcc(bccs);
        
        propertyWriterImpl.setMailProperty(mail,testDto3);
        assertEquals("to address 1", (String)tos[0], mail.getTo()[0].getAddress());
        assertEquals("to address 2", ((InternetAddress)tos[1]).getAddress(), mail.getTo()[1].getAddress());
        assertEquals("to name 2",((InternetAddress)tos[1]).getPersonal(), mail.getTo()[1].getPersonal());
        assertEquals("cc address 1", ccs[0], mail.getCc()[0].getAddress());
        assertEquals("cc address 2", ccs[1], mail.getCc()[1].getAddress());
        assertEquals("bcc address 1", bccs[0].getAddress(), mail.getBcc()[0].getAddress());
        assertEquals("bcc name 1",bccs[0].getPersonal(), mail.getBcc()[0].getPersonal());
        assertEquals("bcc address 2", bccs[1].getAddress(), mail.getBcc()[1].getAddress());
        assertEquals("bcc name 2",bccs[1].getPersonal(), mail.getBcc()[1].getPersonal());
        
    }
    
    public class TestDto{
        private String host;
        private String port;
        private String username;
        private String password;
        private String from;
        private List to;
        private List cc;
        private List bcc;
        private String replyTo;
        public String getHost() {return host;}
        public void setHost(String host) {this.host = host;}
        public String getPassword() {return password;}
        public void setPassword(String password) {this.password = password;}
        public String getPort() {return port;}
        public void setPort(String port) {this.port = port;}
        public String getUsername() {return username;}
        public void setUsername(String username) {this.username = username;}
        public String getFrom() {return from;}
        public void setFrom(String from) {this.from = from;}
        public List getTo() {return to;}
        public void setTo(List to) {this.to = to;}
        public List getCc() {return cc;}
        public void setCc(List cc) {this.cc = cc;}
        public final List getBcc() {return bcc;}
        public final void setBcc(List bcc) {this.bcc = bcc;}
        public final String getReplyTo() {return replyTo;}
        public final void setReplyTo(String replyTo) {this.replyTo = replyTo;}
        
    }
    
    public class TestDto2{
        private InternetAddress from;
        private String to;        
        public InternetAddress getFrom() {return from;}
        public void setFrom(InternetAddress from) {this.from = from;}
        public String getTo() {return to;}
        public void setTo(String to) {this.to = to;}
    }
    
    public class TestDto3{
        private Object[] to;
        private String[] cc;
        private InternetAddress[] bcc;
        public Object[] getTo() {return to;}
        public void setTo(Object[] to) {this.to = to;}
        public final InternetAddress[] getBcc() {return bcc;}
        public final void setBcc(InternetAddress[] bcc) {this.bcc = bcc;}
        public final String[] getCc() {return cc;}
        public final void setCc(String[] cc) {this.cc = cc;}
    }

    /**
     * @param propertyWriterImpl The propertyWriterImpl to set.
     */
    public void setPropertyWriterImpl(PropertyWriterForBeanImpl propertyWriterImpl) {
        this.propertyWriterImpl = propertyWriterImpl;
    }

}
