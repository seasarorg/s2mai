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
package org.seasar.mai.bean.impl;

import java.util.List;
import java.util.Map;

import org.seasar.extension.jdbc.types.ValueTypes;
import org.seasar.mai.bean.ArrayWrapper;
import org.seasar.mai.bean.ListWrapper;
import org.seasar.mai.bean.MapWrapper;
import org.seasar.mai.bean.WrapperValueConverter;

/**
 * @author rokugen
 */
public class WrapperValueConverterImpl implements WrapperValueConverter{

    public Object convert(Object value) {
        if(value == null){
            return null;
        }
     
        Class clazz = value.getClass();
        if(ValueTypes.isSimpleType(clazz)){
            return value;
        }
        
        if(clazz.isArray()){
            return new ArrayWrapper(value);
        }
        
        if(List.class.isAssignableFrom(clazz)){
            return new ListWrapper((List)value);
        }
        
        if(Map.class.isAssignableFrom(clazz)){
            return new MapWrapper((Map)value);
        }
        
        return null;
    }

}
