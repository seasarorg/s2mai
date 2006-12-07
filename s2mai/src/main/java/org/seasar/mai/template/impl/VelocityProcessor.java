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
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.seasar.framework.exception.ParseRuntimeException;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.template.TemplateProcessor;

/**
 */
public class VelocityProcessor implements TemplateProcessor {

    private static Logger logger = Logger.getLogger(VelocityProcessor.class);
    private VelocityEngine engine;

    public void init() {
        logger.log("DMAI0001", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          

        // VelocityEngineの設定
        this.engine = new VelocityEngine();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("input.encoding", "UTF8");
        
        // VelocityEngineの初期化
        try {
            this.engine.init(properties);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        logger.log("DMAI0002", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          
    }

    public String process(String templateText, Object context) {
        logger.log("DMAI0001", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          

        //TODO どうやるんだろう？

        logger.log("DMAI0002", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          
        return null;
    }

    public String processResource(String path, Object context) {
        logger.log("DMAI0001", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          

        String pathWithExt = path + "." + S2MaiConstants.VELOCITY_EXT;
        Writer writer = new StringWriter();
 
        // テンプレートとコンテキストのマージ
        try {
            this.engine.mergeTemplate(pathWithExt, (Context)context, writer);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundRuntimeException(pathWithExt);
        } catch (ParseErrorException e) {
            throw new RuntimeException(e);
        } catch (MethodInvocationException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.log("DMAI0002", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          
        return writer.toString();
    }

}
