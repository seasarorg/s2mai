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
package org.seasar.mai.intereceptors;

import org.seasar.extension.unit.S2TestCase;

/**
 * @author Satsohi Kimura
 */
public class S2MaiIntereceptorTest extends S2TestCase {
    private TestMai testMai;

    protected void setUp() throws Exception {
        include("test.dicon");
    }

    /*
     * Test method for 'org.seasar.mai.intereceptors.S2MaiIntereceptor.invoke(MethodInvocation)'
     */
    public void testInvoke() {
        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        testMai.sendMail(data);
    }

}
