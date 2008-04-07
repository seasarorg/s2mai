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

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author rokugen
 */
public class BeanWrapper implements Map {
    protected Object bean;

    protected BeanDesc beanDesc;

    public BeanWrapper(Object bean) {
        this.bean = bean;
        beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
    }

    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        return beanDesc.hasPropertyDesc(key.toString());
    }

    public Object get(Object key) {
        try {
            PropertyDesc pd = beanDesc.getPropertyDesc(key.toString());
            if (!pd.isReadable()) {
                return null;
            }

            return WrapperFactory.convert(pd.getValue(bean));
        } catch (PropertyNotFoundRuntimeException ignore) {
            return null;
        }
    }

    public Set keySet() {
        Set set = new HashSet();
        int size = beanDesc.getPropertyDescSize();
        for (int i = 0; i < size; i++) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            set.add(pd.getPropertyName());
        }
        return set;
    }

    public Object put(Object key, Object value) {
        PropertyDesc pd = beanDesc.getPropertyDesc(key.toString());
        pd.setValue(bean, value);
        return null;
    }

    public Object remove(Object key) {
        return put(key, null);
    }

    public int size() {
        return beanDesc.getPropertyDescSize();
    }

    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("containsValue");
    }

    public Set entrySet() {
        throw new UnsupportedOperationException("entrySet");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("isEmpty");
    }

    public void putAll(Map arg0) {
        throw new UnsupportedOperationException("putAll");
    }

    public Collection values() {
        throw new UnsupportedOperationException("values");
    }

}
