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
package org.seasar.mai.bean;

import java.lang.reflect.Constructor;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;
import org.seasar.mai.bean.impl.WrapperValueConverterImpl;

/**
 * @author rokugen
 */
public class WrapperFactory {
    private static WrapperValueConverter converter;
    private static final String TIGER_CONVERTER_NAME = "org.seasar.mai.bean.impl.TigerWrapperValueConverterImpl"; 
    
    static{
        converter = new WrapperValueConverterImpl();
        try {
            final Class clazz = ClassUtil.forName(TIGER_CONVERTER_NAME);
            final Constructor ctor = ClassUtil.getConstructor(clazz, null);
            converter = (WrapperValueConverter) ConstructorUtil
                    .newInstance(ctor, null);
        } catch (final ClassNotFoundRuntimeException ignore) {
        }

    }

    private WrapperFactory(){       
        
    }
    
    public static Object convert(Object value){
        Object converted = converter.convert(value);
        if(converted != null){
            return converted;
        }
        return new BeanWrapper(value);        
    }
}
