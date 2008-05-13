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

import org.apache.commons.mail.Email;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.Mail;

/**
 * @author rokugen
 */
public abstract class S2MaiTestCase extends S2TestCase {
    public S2MaiTestCase(){
        
    }
    
    public S2MaiTestCase(String name) {
        super(name);
    }
    
    public void runBare() throws Throwable {
        SendMailTestUtil.init();
        super.runBare();
    }
    
    public void assertEquals(Email expected, Email actual){
        assertEquals(null, expected, actual);
    }
    
    public void assertEquals(String message, Email expected, Email actual){
        assertEquals(message, expected.toString(), actual.toString());
    }
    
    public String getTextFromFile(String fileName){
        return SendMailTestUtil.getTextFromFile(this, fileName);
    }
    
    public Mail createExpectedMailByFile(String fileName){        
        return SendMailTestUtil.createExpectedMailByFile(this, fileName);
    }
    
    public Mail getActualMail(int index){
        return SendMailTestUtil.getActualMail(index);
    }
    
    public int sentMailCount(){
        return SendMailTestUtil.sentMailCount();
    }

}
