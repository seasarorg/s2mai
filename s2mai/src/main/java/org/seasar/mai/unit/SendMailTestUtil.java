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

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.util.MailTextUtil;

import com.ozacc.mail.Mail;

/**
 * @author Satsohi Kimura
 */
public class SendMailTestUtil {
    private static List mailList;

    public static final void addMail(Mail mail) {
        mailList.add(mail);
    }

    public static final Mail getActualMail(int index) {
        return (Mail) mailList.get(index);
    }

    public static final void init() {
        mailList = new ArrayList();
    }

    public static final void clear() {
        mailList.clear();
    }
    
    private static final String getStringFromFile(Object testObject, String fileName){
        Class testClass = testObject.getClass();
        S2Container container = S2ContainerFactory.create(S2MaiConstants.MAIL_PROPERTIES_DICON);
        String encoding = (String) container.getComponent(S2MaiConstants.TEMPLATE_ENCODING);
        InputStream is = null;
        try {
            String path = ResourceUtil.convertPath(fileName, testClass); 
            is = ResourceUtil.getResourceAsStream(path);
            Reader reader = InputStreamReaderUtil.create(is, encoding);
            return ReaderUtil.readText(reader);
        } finally {
            InputStreamUtil.close(is);
        }
    }

    public static final String getTextFromFile(Object testObject, String fileName) {
        String str = getStringFromFile(testObject, fileName);
        return MailTextUtil.getText(str);
    }

    public static final Mail createExpectedMailByFile(Object testObject, String fileName) {
        String fileText = getStringFromFile(testObject, fileName);
        String subject = MailTextUtil.getSubject(fileText);
        String text = MailTextUtil.getText(fileText);
        
        Mail expected = new Mail();
        expected.setSubject(subject);
        expected.setText(text);
        return expected;
    }

}
