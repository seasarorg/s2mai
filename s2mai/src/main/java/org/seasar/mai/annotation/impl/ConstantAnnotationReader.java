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
package org.seasar.mai.annotation.impl;

import java.lang.reflect.Method;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.mai.annotation.AnnotationReader;

public class ConstantAnnotationReader implements AnnotationReader {
    private static final String TO = "TO";    
    private static final String CC = "CC";
    private static final String BCC = "BCC";
    private static final String SUBJECT = "SUBJECT";
    private static final String REPLY_TO = "REPLY_TO";
    private static final String RETURN_PATH = "RETURN_PATH";
    private static final String FROM = "FROM";
    
    private Object getAnnotationValue(String annotation, Method method){
        Class mai = method.getDeclaringClass();
        BeanDesc maiBeanDesc = BeanDescFactory.getBeanDesc(mai);
        
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        sb.append("_");
        sb.append(annotation);
        String fieldName = sb.toString();
        
        Object value = null;
        if(maiBeanDesc.hasField(fieldName)){
            value = maiBeanDesc.getFieldValue(sb.toString(),null);            
        }else{
            if(maiBeanDesc.hasField(annotation)){
                value = maiBeanDesc.getFieldValue(annotation, null);
            }            
        }
        
        return value;        
    }

    public Object getTo(Method method) {

        return getAnnotationValue(TO, method);
    }

    public Object getCc(Method method) {
        return getAnnotationValue(CC, method);
    }

    public Object getBcc(Method method) {
        return getAnnotationValue(BCC, method);
    }

    public Object getSubject(Method method) {
        return getAnnotationValue(SUBJECT, method);
    }

    public Object getReplyTo(Method method) {
        return getAnnotationValue(REPLY_TO, method);
    }

    public Object getReturnPath(Method method) {
        return getAnnotationValue(RETURN_PATH, method);
    }

    public Object getFrom(Method method) {
        return getAnnotationValue(FROM, method);
    }

}