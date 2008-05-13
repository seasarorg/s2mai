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
package org.seasar.mai.unit;

import javax.mail.internet.InternetAddress;

import org.seasar.mai.interceptors.AnnotationTestMai;
import org.seasar.mai.interceptors.TestData;
import org.seasar.mai.mail.Mail;

/**
 * @author rokugen
 */
public class S2MaiTestCaseTest extends S2MaiTestCase {
    private AnnotationTestMai annotationTestMai;
    protected void setUp() throws Exception {
        include("test.dicon");        
    }
    
    public void testInvoke() throws Exception {

        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        annotationTestMai.sendMail(data);
        annotationTestMai.sendMail2(data);
        // methodのdiconあり
        annotationTestMai.sendMail3(data);

        Mail expected = createExpectedMailByFile("S2MaiTestCaseTest_expectedText.txt");
        expected.setFrom(new InternetAddress("takeuchi"));
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.addCc("kimura");        
        
        assertEquals(expected, getActualMail(0));

        expected.setReturnPath(new InternetAddress("kimura"));
        expected.setFrom("takeuchi");
        expected.clearTo();
        expected.addTo("rokugen");
        expected.clearCc();
        expected.addCc("kei");
        expected.addCc("rokugen");
        expected.addBcc("rokugen");
        expected.setSubject("アノテーションテスト-diconなしアノテーションあり - No.12,345");
        assertEquals(expected, getActualMail(1));

        expected.setReturnPath(new InternetAddress());
        expected.setFrom("takeuchi");
        expected.clearTo();
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.clearCc();
        expected.addCc("takeuchi");
        expected.clearBcc();
        expected.addBcc("kei");
        expected.addBcc("rokugen");
        expected.setReplyTo("rokugen");
        expected.setSubject("アノテーションテスト-methoddicon - No.12,345");

        assertEquals("3通目",expected, getActualMail(2));
        
        assertEquals(3, sentMailCount());
    }

    public void testInvoke2() throws Exception {

        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        // methodのdiconあり
        annotationTestMai.sendMail3(data);

        Mail expected = createExpectedMailByFile("S2MaiTestCaseTest_expected_nosubject.txt");
        expected.setReturnPath(new InternetAddress());
        expected.setFrom("takeuchi");
        expected.clearTo();
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.clearCc();
        expected.addCc("takeuchi");
        expected.clearBcc();
        expected.addBcc("kei");
        expected.addBcc("rokugen");
        expected.setReplyTo("rokugen");
        expected.setSubject("アノテーションテスト-methoddicon - No.12,345");

        
        assertEquals("2個目のテストメソッド",expected, getActualMail(0));

    }

    public void setAnnotationTestMai(AnnotationTestMai annotationTestMai) {
        this.annotationTestMai = annotationTestMai;
    } 

}
