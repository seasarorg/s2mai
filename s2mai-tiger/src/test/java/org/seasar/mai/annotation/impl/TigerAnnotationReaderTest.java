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
import org.seasar.mai.annotation.MailAddr;
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
        
        Method method = TestMai.class.getMethod("sendMail",null);
		
		assertEquals("class", TigerAnnotationReader.class, annotationReader.getClass());
        assertEquals("to 1 address", "to1@address", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getAddress());
        assertEquals("to 1 name", "TO1送信先", ((List<MailAddress>)annotationReader.getTo(method)).get(0).getPersonal());
        assertEquals("to 2 address", "to2@address", ((List<MailAddress>)annotationReader.getTo(method)).get(1).getAddress());
	}
    
    @To({@MailAddr(address="to1@address",personal="TO1送信先"),@MailAddr(address="to2@address")})
    public interface TestMai{
        void sendMail();        
    }
    

	/**
	 * @param factory The factory to set.
	 */
	public final void setFactory(AnnotationReaderFactory factory) {
		this.factory = factory;
	}


}
