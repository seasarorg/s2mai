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
package org.seasar.mai.util;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.mai.S2MaiConstants;

/**
 * @author kei
 */
public class MailTextUtil {
    public static String getText(String text) {
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return text;
        }

        return text.replaceFirst(".*((\r\n\r\n)|(\n\n)|(\r\r))", "");
    }

    public static String getSubject(String text) {
        if (text.startsWith(S2MaiConstants.TEMPLATE_SUBJECT) == false) {
            return null;
        }
        LineNumberReader reader = new LineNumberReader(new StringReader(text));
        reader.setLineNumber(0);
        String subjectLine = null;
        try {
            subjectLine = reader.readLine();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        return subjectLine.substring(S2MaiConstants.TEMPLATE_SUBJECT.length(), subjectLine.length());
    }    

}
