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
package org.seasar.mai.bean.impl;

import org.seasar.mai.bean.WrapperValueConverter;

/**
 * @author rokugen
 */
public class TigerWrapperValueConverterImpl extends WrapperValueConverterImpl implements WrapperValueConverter {

	public Object convert(Object value) {
		Object converted = super.convert(value);
		if(converted != null){
			return converted;
		}
		Class<?> clazz = value.getClass();
		
		if(clazz.getSuperclass().isEnum()){
            return value;
        }
		return null;
	}

}
