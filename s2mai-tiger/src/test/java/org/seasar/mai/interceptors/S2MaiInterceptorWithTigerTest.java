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
package org.seasar.mai.interceptors;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.unit.SendMailTestUtil;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class S2MaiInterceptorWithTigerTest extends S2TestCase {
	private TigerTestMai tigerTestMai;

	public S2MaiInterceptorWithTigerTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		include("tiger_test.dicon");
		SendMailTestUtil.init();
	}
	
	public void testSendMail(){
		TigerTestDto dto = new TigerTestDto();
		dto.setText("あいうえお");
		tigerTestMai.sendMail(dto);
		tigerTestMai.sendMail2(dto);		
		
		Mail actual = SendMailTestUtil.getMail(0);
		assertEquals("件名1", actual.getSubject());
		assertEquals("FROM1名前", actual.getFrom().getPersonal());
		assertEquals("TO1送信先", actual.getTo()[0].getPersonal());
		assertEquals("CC1送信先", actual.getCc()[0].getPersonal());
		assertEquals("bcc1@address",actual.getBcc()[0].getAddress());
		assertEquals("replyTo1@address", actual.getReplyTo().getAddress());
		assertEquals("returnPath1@address", actual.getReturnPath().getAddress());
		
		actual = SendMailTestUtil.getMail(1);
		assertEquals("件名2", actual.getSubject());
		assertEquals("FROM2名前", actual.getFrom().getPersonal());
		assertEquals("TO3送信先", actual.getTo()[0].getPersonal());
		assertEquals("CC2送信先", actual.getCc()[0].getPersonal());
		assertEquals("bcc3@address",actual.getBcc()[0].getAddress());
		assertEquals("replyTo2@address", actual.getReplyTo().getAddress());
		assertEquals("returnPath2@address", actual.getReturnPath().getAddress());
				
	}

}
