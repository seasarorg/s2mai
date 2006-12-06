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
package org.seasar.mai.template.impl;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.template.TemplateProcessor;

/**
 */
public class VelocityProcessor implements TemplateProcessor {

    private VelocityEngine engine;

    public void init() {
        System.out.println("init");
        this.engine = new VelocityEngine();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("input.encoding", "UTF8");
        try {
            this.engine.init(properties);
        } catch(Exception e) {
            System.out.println(e);            
        }
    }

    public String process(String templateText, Object context) {
        System.out.println("process");
        return null;
    }

    public String processResource(String path, Object context) {
        String pathWithExt = path + "." + S2MaiConstants.VELOCITY_EXT;
        Writer writer = new StringWriter();
 
        try {
            System.out.println(this.engine.mergeTemplate(pathWithExt, (Context)context, writer));
        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return writer.toString();

    }

}
