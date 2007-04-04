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
package org.seasar.mai.interceptors;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.TextUtil;
import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.mail.MailAddress;
import org.seasar.mai.unit.SendMailTestUtil;

import com.ozacc.mail.Mail;
import com.ozacc.mail.Mail.AttachmentFile;

/**
 * @author Satsohi Kimura
 */
public class S2MaiInterceptorTest extends S2TestCase {
    private TestMai testMai;

    protected void setUp() throws Exception {
        include("test.dicon");
        SendMailTestUtil.init();
    }

    /*
     * Test method for 'org.seasar.mai.interceptors.S2MaiIntereceptor.invoke(MethodInvocation)'
     */
    public void testInvoke() {

        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        testMai.sendMail(data);

        String expected = TextUtil.readUTF8(super.convertPath("S2MaiInterceptorTest_testInvoke.txt"));
        assertEquals(expected, SendMailTestUtil.getMail(0).getText());
    }

    public void testInvokeWithDynamicProperty() throws UnsupportedEncodingException {
        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);

        // プロパティ動的にセット
        data.setFrom("kei");
        data.setTo(new InternetAddress("rokugen", "六"));
        data.setCc(new MailAddress("rokugen", "六のCC"));
        data.setSubject("件名です");

        String path = this.getClass().getResource("attachedFileTest.txt").getPath();
        File file = new File(path);
        AttachedFile af = new AttachedFile(file, "添付ファイル.txt");
        data.setAttachedFile(af);
        testMai.sendMail(data);

        Mail mail = SendMailTestUtil.getMail(0);
        assertEquals("kei", mail.getFrom().getAddress());
        assertEquals("rokugen", mail.getTo()[0].getAddress());
        assertEquals("六", mail.getTo()[0].getPersonal());
        assertEquals("rokugen", mail.getCc()[0].getAddress());
        assertEquals("六のCC", mail.getCc()[0].getPersonal());
        assertEquals("件名です", mail.getSubject());
        AttachmentFile[] attachmentFiles = mail.getAttachmentFiles();
        assertEquals(1, attachmentFiles.length);
        assertEquals("添付ファイル.txt", attachmentFiles[0].getName());

    }

}
