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
package org.seasar.mai.annotation.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.AnnotationReaderFactory;
import org.seasar.mai.annotation.Bcc;
import org.seasar.mai.annotation.Cc;
import org.seasar.mai.annotation.From;
import org.seasar.mai.annotation.MailAddr;
import org.seasar.mai.annotation.ReplyTo;
import org.seasar.mai.annotation.ReturnPath;
import org.seasar.mai.annotation.Subject;
import org.seasar.mai.annotation.To;
import org.seasar.mai.mail.MailAddress;



/**
 * @author rokugen
 */
public class TigerAnnotationReaderTest extends S2TestCase {
	private AnnotationReaderFactory factory;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TigerAnnotationReaderTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		include("TigerAnnotationReaderTest.dicon");
	}
	
	@SuppressWarnings("unchecked")
    public void testGetValue() throws SecurityException, NoSuchMethodException{
		AnnotationReader annotationReader = factory.createMaiAnnotationReader();
        
        Method method = TigerTestMai1.class.getMethod("sendMail",null);
		
		assertEquals("class", TigerAnnotationReader.class, annotationReader.getClass());
        assertEquals("to 1 address", "to1@address", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getAddress());
        assertEquals("to 1 name", "TO1送信先", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getPersonal());
        assertEquals("to 2 address", "to2@address", ((List<MailAddress>)annotationReader.getTo(method)).get(1).getAddress());
        assertEquals("from 1 address", "from1@address", ((MailAddress)annotationReader.getFrom(method)).getAddress());
        assertEquals("from 1 name", "FROM1名前", ((MailAddress)annotationReader.getFrom(method)).getPersonal());
        assertEquals("subject 1 ", "件名1", (String)annotationReader.getSubject(method));
        assertEquals("cc 1 address", "cc1@address", ((List<MailAddress>)annotationReader.getCc(method)).get(0).getAddress());
        assertEquals("cc 1 name", "CC1送信先", ((List<MailAddress>)annotationReader.getCc(method)).get(0).getPersonal());
        assertEquals("bcc 1 address", "bcc1@address", ((String[])annotationReader.getBcc(method))[0]);
        assertEquals("bcc 2 address", "bcc2@address", ((String[])annotationReader.getBcc(method))[1]);
        assertEquals("replyTo 1 address", "replyTo1@address", ((MailAddress)annotationReader.getReplyTo(method)).getAddress());
        assertEquals("replyTo 1 name", "REPLYTO1名前", ((MailAddress)annotationReader.getReplyTo(method)).getPersonal());
        assertEquals("returnPath 1 address", "returnPath1@address", (String)annotationReader.getReturnPath(method));
        
        
        method = TigerTestMai1.class.getMethod("sendMail2",null);
        assertEquals("to 3 address", "to3@address", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getAddress());
        assertEquals("to 3 name", "TO3送信先", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getPersonal());
        assertEquals("from 2 address", "from2@address", ((MailAddress)annotationReader.getFrom(method)).getAddress());
        assertEquals("from 2 name", "FROM2名前", ((MailAddress)annotationReader.getFrom(method)).getPersonal());
        assertEquals("subject 2 ", "件名2", (String)annotationReader.getSubject(method));
        assertEquals("cc 2 address", "cc2@address", ((List<MailAddress>)annotationReader.getCc(method)).get(0).getAddress());
        assertEquals("cc 2 name", "CC2送信先", ((List<MailAddress>)annotationReader.getCc(method)).get(0).getPersonal());
        assertEquals("cc 3 address", "cc3@address", ((List<MailAddress>)annotationReader.getCc(method)).get(1).getAddress());
        assertEquals("cc 3 name", "", ((List<MailAddress>)annotationReader.getCc(method)).get(1).getPersonal());
        assertEquals("bcc 1 address", "bcc3@address", ((String[])annotationReader.getBcc(method))[0]);
        assertEquals("replyTo 2 address", "replyTo2@address", ((MailAddress)annotationReader.getReplyTo(method)).getAddress());
        assertEquals("replyTo 2 name", "REPLYTO2名前", ((MailAddress)annotationReader.getReplyTo(method)).getPersonal());
        assertEquals("returnPath 2 address", "returnPath2@address", (String)annotationReader.getReturnPath(method));
        
	}
	
	/**
	 * TigerAnnotationReadearがクラスパスにあっても定数アノテーションを使った場合のテスト
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public void testGetValueWithoutTiger() throws SecurityException, NoSuchMethodException{
		AnnotationReader annotationReader = factory.createMaiAnnotationReader();
        
        Method method = TigerTestMai2.class.getMethod("sendMail",null);
        assertEquals("subject", "件名", (String)annotationReader.getSubject(method));
        assertEquals("from", "from@address", ((MailAddress)annotationReader.getFrom(method)).getAddress());
		assertEquals("to", "to@address", (String)annotationReader.getTo(method));
		assertEquals("cc1", "cc1@address", ((String[])annotationReader.getCc(method))[0]);
		assertEquals("cc2", "cc2@address", ((String[])annotationReader.getCc(method))[1]);
		assertEquals("bcc", "bcc@address", (String)annotationReader.getBcc(method));
		assertEquals("replyTo", "replyTo@address", (String)annotationReader.getReplyTo(method));
		assertEquals("returnPath", "returnPath@address", (String)annotationReader.getReturnPath(method));
		
		
	}
	
	@Subject("件名1")
    @From(@MailAddr(address="from1@address",personal="FROM1名前"))
    @To({@MailAddr(address="to1@address",personal="TO1送信先"),@MailAddr(address="to2@address")})
    @Cc(@MailAddr(address="cc1@address",personal="CC1送信先"))
    @Bcc({"bcc1@address","bcc2@address"})
    @ReplyTo(@MailAddr(address="replyTo1@address",personal="REPLYTO1名前"))
    @ReturnPath("returnPath1@address")
    public interface TigerTestMai1{
        void sendMail();
        
        @Subject("件名2")
        @From(@MailAddr(address="from2@address",personal="FROM2名前"))
        @To(@MailAddr(address="to3@address",personal="TO3送信先"))
        @Cc({@MailAddr(address="cc2@address",personal="CC2送信先"),
        	@MailAddr(address="cc3@address")})        
        @Bcc("bcc3@address")
        @ReplyTo(@MailAddr(address="replyTo2@address",personal="REPLYTO2名前"))
        @ReturnPath("returnPath2@address")    
        void sendMail2();
    }
	
	public interface TigerTestMai2{
		static final String SUBJECT = "件名";
		static final MailAddress FROM = new MailAddress("from@address","FROM名前");
		static final String TO = "to@address";
		static final String[] CC = {"cc1@address","cc2@address"};
		static final String BCC = "bcc@address";
		static final String REPLY_TO = "replyTo@address";
		static final String RETURN_PATH = "returnPath@address";
		
		void sendMail();
	}
    

	/**
	 * @param factory The factory to set.
	 */
	public final void setFactory(AnnotationReaderFactory factory) {
		this.factory = factory;
	}


}
