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
package org.seasar.mai.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Satsohi Kimura
 */
public class FreeMarkerrUtil {
    private static final Configuration defaultConfig = new Configuration();

    public static void setDefaultEncoding(String enc) {
        Configuration config = getDefaultConfig();
        config.setDefaultEncoding(enc);
    }

    public static String getDefaultEncoding() {
        Configuration config = getDefaultConfig();
        return config.getDefaultEncoding();
    }

    public static String processResource(String path, Object data) {
        InputStream is = ResourceUtil.getResourceAsStream(path);
        Reader reader = InputStreamReaderUtil.create(is, getDefaultEncoding());
        String text = ReaderUtil.readText(reader);
        return process(text, data);
    }

    public static String process(String templateText, Object data) {
        try {
            Configuration config = getDefaultConfig();
            Template template = new Template("Template", new StringReader(templateText), config);
            StringWriter stringWriter = new StringWriter();
            template.process(data, stringWriter);
            stringWriter.flush();
            return stringWriter.toString();
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private static Configuration getDefaultConfig() {
        return defaultConfig;
    }
}
