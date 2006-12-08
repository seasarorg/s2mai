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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.template.TemplateProcessor;

/**
 */
public class VelocityProcessor implements TemplateProcessor {

    private static Logger logger = Logger.getLogger(VelocityProcessor.class);

    private String configFile;

    private VelocityEngine engine;

    public void init() {
        logger.log("DMAI0001", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          

        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
        this.engine = new VelocityEngine();

        // VelocityEngineの初期化
        try {

            if(this.configFile != null) {
                Properties properties = new Properties();
                properties.load(this.getClass().getClassLoader().getResourceAsStream(this.configFile));
                this.engine.init(properties);
                runtimeServices.init(properties);
            } else {
                this.engine.init();
                runtimeServices.init();                
            }

        } catch(IOException e) {
             throw new IORuntimeException(e);
         } catch(Exception e) {
             throw new RuntimeException(e);
         }
        
        logger.log("DMAI0002", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          
    }

    public String process(String templateText, Object context) {
        logger.log("DMAI0001", new Object[] { new Throwable().getStackTrace()[0].getClassName() + "@"
                + new Throwable().getStackTrace()[0].getMethodName() });

        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
        try {
            StringReader reader = new StringReader(templateText);
            SimpleNode node = runtimeServices.parse(reader, reader.toString());
            Template template = new Template();
            template.setData(node);
            template.initDocument();
            Writer writer = new StringWriter();
            template.merge((Context) context, writer);
            logger.log("DMAI0002", new Object[] { new Throwable().getStackTrace()[0].getClassName() + "@"
                    + new Throwable().getStackTrace()[0].getMethodName() });
            return writer.toString();
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseErrorException e) {
            throw new RuntimeException(e);
        } catch (MethodInvocationException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String processResource(String path, Object context) {
        logger.log("DMAI0001", new Object[] {new Throwable().getStackTrace()[0].getClassName() + "@" + new Throwable().getStackTrace()[0].getMethodName()});                          

        Writer writer = new StringWriter();
 
        // テンプレートとコンテキストのマージ
        try {
            this.engine.mergeTemplate(path, (Context)context, writer);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundRuntimeException(path);
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

    public String getExt() {
        return S2MaiConstants.VELOCITY_EXT;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

}
