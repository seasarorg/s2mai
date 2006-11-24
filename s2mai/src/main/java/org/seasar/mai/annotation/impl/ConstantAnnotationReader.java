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

    public Object getTo(Method method) {
        Class mai = method.getDeclaringClass();
        BeanDesc maiBeanDesc = BeanDescFactory.getBeanDesc(mai);
        
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        sb.append("_");
        sb.append(TO);
        String fieldName = sb.toString();
        
        Object value = null;
        if(maiBeanDesc.hasField(fieldName)){
            value = maiBeanDesc.getFieldValue(sb.toString(),null);            
        }else{
            if(maiBeanDesc.hasField(TO)){
                value = maiBeanDesc.getFieldValue(TO,null);            
            }
            
        }        

        return value;
    }

    public String[] getCc(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getBcc(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSubject(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getReplyTo(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getReturnPath(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getFrom(Method method) {
        // TODO Auto-generated method stub
        return null;
    }

}