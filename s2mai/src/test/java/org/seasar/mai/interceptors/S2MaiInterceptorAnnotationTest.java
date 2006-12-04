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

import javax.mail.internet.InternetAddress;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.unit.SendMailTestUtil;

import com.ozacc.mail.Mail;

/**
 * @author Satsohi Kimura
 */
public class S2MaiInterceptorAnnotationTest extends S2TestCase {
    private AnnotationTestMai annotationTestMai;

    protected void setUp() throws Exception {
        include("test.dicon");
        SendMailTestUtil.init();
    }

    /*
     * Test method for 'org.seasar.mai.interceptors.S2MaiIntereceptor.invoke(MethodInvocation)'
     */
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
        
        Mail expected = new Mail();
        expected.setFrom(new InternetAddress("takeuchi"));
        expected.addTo("kei");
        expected.addTo("rokugen");
        expected.addCc("kimura");
        expected.setSubject("件名置き換えテスト");
        StringBuffer text = new StringBuffer();
        text.append("                  ==================================== ").append("\r\n");
        text.append("                       Tech Info Library - Japan ").append("\r\n");
        text.append("                         2,006年11月22日 No.12,345 ").append("\r\n");
        text.append("                  ==================================== ").append("\r\n");
        text.append("").append("\r\n");
        text.append("【本日の内容】").append("\r\n");
        text.append("  1.本日のトピック").append("\r\n");
        text.append("  2.お知らせ").append("\r\n");
        text.append("    - サポートページの更新情報").append("\r\n");
        text.append("  3.今週のサポート情報一覧").append("\r\n");
        text.append("  4.Advanced Search（サポート情報検索）サイトについて").append("\r\n");
        text.append("  5.ソフトウェアアップデート情報").append("\r\n");
        expected.setText(text.toString());
        assertEquals(expected.toString(), SendMailTestUtil.getMail(0).toString());
        
        expected.setReturnPath(new InternetAddress("kimura"));
        expected.setFrom("takeuchi");
        expected.clearTo();
        expected.addTo("rokugen");
        expected.clearCc();
        expected.addCc("kei");
        expected.addCc("rokugen");
        expected.addBcc("rokugen");
        expected.setSubject("アノテーションテスト-diconなしアノテーションあり - No.12,345");
        assertEquals(expected.toString(), SendMailTestUtil.getMail(1).toString());
        // TODO assert
        // assertEquals(expected.toString(), SendMailTestUtil.getMail(2).toString());
    }
    

    /**
     * @param annotationTestMai The annotationTestMai to set.
     */
    public final void setAnnotationTestMai(AnnotationTestMai annotationTestMai) {
        this.annotationTestMai = annotationTestMai;
    }

}
