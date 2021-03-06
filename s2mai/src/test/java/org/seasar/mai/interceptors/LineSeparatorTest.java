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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.unit.SendMailTestUtil;

/**
 * @author Satsohi Kimura
 */
public class LineSeparatorTest extends S2TestCase {
    private LineSeparatorTestMai mai;

    protected void setUp() throws Exception {
        include("test.dicon");
        SendMailTestUtil.init();
    }

    public void testCrLf() {
        mai.sendMailCrLf();

        Mail mail = SendMailTestUtil.getActualMail(0);
        assertEquals("Test", mail.getSubject());
        assertEquals("MailBody\r\n", mail.getText());
    }

    public void testCr() {
        mai.sendMailCr();

        Mail mail = SendMailTestUtil.getActualMail(0);
        assertEquals("Test", mail.getSubject());
        assertEquals("MailBody\r", mail.getText());
    }

    public void testLf() {
        mai.sendMailLf();

        Mail mail = SendMailTestUtil.getActualMail(0);
        assertEquals("Test", mail.getSubject());
        assertEquals("MailBody\n", mail.getText());
    }
}
