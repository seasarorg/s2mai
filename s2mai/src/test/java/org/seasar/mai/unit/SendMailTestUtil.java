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
package org.seasar.mai.unit;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;

import com.ozacc.mail.Mail;

/**
 * @author Satsohi Kimura
 */
public class SendMailTestUtil {
    private static List mailList;

    public static final void addMail(Mail mail) {
        mailList.add(mail);
    }

    public static final Mail getMail(int index) {
        return (Mail)mailList.get(index);
    }

    public static final void init() {
        mailList = new ArrayList();
    }

    public static final void clear() {
        mailList.clear();
    }
    
    public static final String getTextFromFile(Class testClass, String fileName, String encoding){
        InputStream is = ResourceUtil.getResourceAsStream(
                testClass.getPackage().getName().replaceAll("\\.", "/") +"/" + fileName);
        Reader reader = InputStreamReaderUtil.create(is,encoding);//TODO encodingをdiconから取得したい
        return ReaderUtil.readText(reader);
    }
}
