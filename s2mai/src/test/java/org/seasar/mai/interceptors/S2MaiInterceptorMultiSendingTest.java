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

import javax.mail.internet.InternetAddress;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.unit.SendMailTestUtil;

/**
 * @author rokugen
 */
public class S2MaiInterceptorMultiSendingTest extends S2TestCase {
    private AnnotationTestMai annotationTestMai;

    protected void setUp() throws Exception {
        include("test.dicon");
        SendMailTestUtil.init();
    }
    
    public void testMultiSendingBySameMethod() throws Exception{
        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        annotationTestMai.sendMail(data);
        data.setNo(34567);
        data.setTo(new InternetAddress("kei2","kei2"));
        annotationTestMai.sendMail(data);
        data.setNo(12345);
        data.setTo(null);
        annotationTestMai.sendMail(data);
        
        Mail expected = SendMailTestUtil.createExpectedMailByFile(this,"S2MaiInterceptorMultiSendingTest_expectedText.txt");
        expected.setFrom(new InternetAddress("takeuchi"));
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.addCc("kimura");        
        
        assertEquals(expected.toString(), SendMailTestUtil.getActualMail(0).toString());
        
        expected = SendMailTestUtil.createExpectedMailByFile(this,"S2MaiInterceptorMultiSendingTest_expectedText2.txt");
        expected.setFrom(new InternetAddress("takeuchi"));
        expected.addTo(new InternetAddress("kei2","kei2"));        
        expected.addCc("kimura");
        
        assertEquals(expected.toString(), SendMailTestUtil.getActualMail(1).toString());
        
        expected = SendMailTestUtil.createExpectedMailByFile(this,"S2MaiInterceptorMultiSendingTest_expectedText.txt");
        expected.setFrom(new InternetAddress("takeuchi"));
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.addCc("kimura");        
        
        assertEquals(expected.toString(), SendMailTestUtil.getActualMail(2).toString());
        
    }    

    /**
     * @param annotationTestMai
     *            The annotationTestMai to set.
     */
    public final void setAnnotationTestMai(AnnotationTestMai annotationTestMai) {
        this.annotationTestMai = annotationTestMai;
    }

}
