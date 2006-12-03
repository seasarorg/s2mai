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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.mail.MailAddress;
import org.seasar.mai.mail.impl.SendMailImpl;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class PropertyWriterForBeanImplTest extends S2TestCase {
    private PropertyWriterForBeanImpl propertyWriterImpl;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PropertyWriterForBeanImplTest.class);
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
        
        assertEquals("host", testDto.getHost(), sendMail.getHost());
        assertEquals("port", Integer.parseInt(testDto.getPort()),sendMail.getPort());
        assertEquals("username", testDto.getUsername(), sendMail.getUsername());
        assertEquals("password", testDto.getPassword(), sendMail.getPassword());
    }
    
    public void testSetMailProperty() throws UnsupportedEncodingException, MalformedURLException{
        Mail mail = new Mail();        
        TestDto testDto = new TestDto();
        testDto.setFrom("from@address");
        testDto.setSubject("件名です");
        
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
        testDto.setReturnPath("returnPath@address");
        testDto.setAttachedFile(new AttachedFile(new URL("http://example.com"),"添付ファイル"));
        testDto.setDummy("dummy");
        
        propertyWriterImpl.setMailProperty(mail,testDto);
        assertEquals("from address only", testDto.getFrom(),mail.getFrom().getAddress());
        
        InternetAddress[] actualToAddrs = mail.getTo();
        InternetAddress[] actualCcAddrs = mail.getCc();
        InternetAddress[] actualBccAddrs = mail.getBcc();
        
        assertEquals("subject", testDto.getSubject(), mail.getSubject());
        assertEquals("to address 1", (String)toList.get(0), actualToAddrs[0].getAddress());
        assertEquals("to address 2", ((InternetAddress)toList.get(1)).getAddress(), actualToAddrs[1].getAddress());
        assertEquals("to name 2", ((InternetAddress)toList.get(1)).getPersonal(), actualToAddrs[1].getPersonal());
        assertEquals("cc address 1", (String)ccList.get(0), actualCcAddrs[0].getAddress());
        assertEquals("bcc address 1", (String)bccList.get(0), actualBccAddrs[0].getAddress());
        assertEquals("bcc address 2", ((InternetAddress)bccList.get(1)).getAddress(), actualBccAddrs[1].getAddress());
        assertEquals("bcc name 2", ((InternetAddress)bccList.get(1)).getPersonal(), actualBccAddrs[1].getPersonal());
        assertEquals("replyTo address ", testDto.getReplyTo(), mail.getReplyTo().getAddress());
        assertEquals("return path address", testDto.getReturnPath(), mail.getReturnPath().getAddress());
        assertEquals("attached file", testDto.getAttachedFile().getFileName(), mail.getAttachmentFiles()[0].getName());
        
        
        
        mail = new Mail();
        TestDto2 testDto2 = new TestDto2();
        InternetAddress from = new InternetAddress();
        from.setAddress("test@mail.address");
        from.setPersonal("test user");
        testDto2.setFrom(from);
        testDto2.setTo("to@address");
        MailAddress ccAddress = new MailAddress("cc@adderss","CC送信先");
        testDto2.setCc(ccAddress);
        testDto2.setFiles(new AttachedFile[]{
                new AttachedFile(new URL("http://example.com"),"添付ファイル1"),
                new AttachedFile(new URL("http://example.com"),"添付ファイル2")
                });
        testDto2.setTempuFile(new AttachedFile(new URL("http://example.com"),"添付ファイル3"));
        
        propertyWriterImpl.setMailProperty(mail,testDto2);
        assertEquals("from address", testDto2.getFrom().getAddress(),mail.getFrom().getAddress());
        assertEquals("from name", testDto2.getFrom().getPersonal(),mail.getFrom().getPersonal());
        assertEquals("to count", 1, mail.getTo().length);
        assertEquals("to address", testDto2.getTo(), mail.getTo()[0].getAddress());
        assertEquals("cc address", testDto2.getCc().getAddress(), mail.getCc()[0].getAddress());
        assertEquals("cc name", testDto2.getCc().getPersonal(), mail.getCc()[0].getPersonal());
        assertEquals("attached file 1", testDto2.getFiles()[0].getFileName(), mail.getAttachmentFiles()[0].getName());
        assertEquals("attached file 2", testDto2.getFiles()[1].getFileName(), mail.getAttachmentFiles()[1].getName());
        assertEquals("attached file 3", testDto2.getTempuFile().getFileName(), mail.getAttachmentFiles()[2].getName());        
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
        List fileList = new ArrayList();
        fileList.add(new AttachedFile(new URL("http://example.com"),"添付ファイル1"));
        fileList.add(new AttachedFile(new URL("http://example.com"),"添付ファイル2"));
        testDto3.setAttachedFile(fileList);        
        
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
        assertEquals("attached file 1",((AttachedFile)fileList.get(0)).getFileName(), mail.getAttachmentFiles()[0].getName());
        assertEquals("attached file 2",((AttachedFile)fileList.get(1)).getFileName(), mail.getAttachmentFiles()[1].getName());
        
    }
    
    public class TestDto{
        private String host;
        private String port;
        private String username;
        private String password;
        private String subject;
        private String from;
        private List to;
        private List cc;
        private List bcc;
        private String replyTo;
        private String returnPath;
        private AttachedFile attachedFile;
        private String dummy;
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
        public final String getSubject() {return subject;}
        public final void setSubject(String subject) {this.subject = subject;}
        public final String getReturnPath() {return returnPath;}
        public final void setReturnPath(String returnPath) {this.returnPath = returnPath;}
        public final String getDummy() {return dummy;}
        public final void setDummy(String dummy) {this.dummy = dummy;}
        public final AttachedFile getAttachedFile() {return attachedFile;}
        public final void setAttachedFile(AttachedFile attachedFile) {this.attachedFile = attachedFile;}
        
    }
    
    public class TestDto2{
        private InternetAddress from;
        private String to;
        private MailAddress cc;
        private AttachedFile[] files;
        private AttachedFile tempuFile;
        
        public InternetAddress getFrom() {return from;}
        public void setFrom(InternetAddress from) {this.from = from;}
        public String getTo() {return to;}
        public void setTo(String to) {this.to = to;}
        public final MailAddress getCc() {return cc;}
        public final void setCc(MailAddress cc) {this.cc = cc;}
        public final AttachedFile[] getFiles() {return files;}
        public final void setFiles(AttachedFile[] files) {this.files = files;}
        public final AttachedFile getTempuFile() {return tempuFile;}
        public final void setTempuFile(AttachedFile tempuFile) {this.tempuFile = tempuFile;}
    }
    
    public class TestDto3{
        private Object[] to;
        private String[] cc;
        private InternetAddress[] bcc;
        private List attachedFile;
        public Object[] getTo() {return to;}
        public void setTo(Object[] to) {this.to = to;}
        public final InternetAddress[] getBcc() {return bcc;}
        public final void setBcc(InternetAddress[] bcc) {this.bcc = bcc;}
        public final String[] getCc() {return cc;}
        public final void setCc(String[] cc) {this.cc = cc;}
        public final List getAttachedFile() {return attachedFile;}
        public final void setAttachedFile(List attachedFile) {this.attachedFile = attachedFile;}
    }

    /**
     * @param propertyWriterImpl The propertyWriterImpl to set.
     */
    public void setPropertyWriterImpl(PropertyWriterForBeanImpl propertyWriterImpl) {
        this.propertyWriterImpl = propertyWriterImpl;
    }

}

