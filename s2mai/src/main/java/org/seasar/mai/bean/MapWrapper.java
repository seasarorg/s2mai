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
import java.util.Map;
import java.util.Set;

/**
 * @author rokugen
 */
public class MapWrapper implements Map{
    
    protected Map map;
    
    public MapWrapper(Map map){
        this.map = map;
    }

    public void clear() {
        map.clear();        
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public Object get(Object key) {
        return WrapperFactory.convert(map.get(key));
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set keySet() {        
        return map.keySet();
    }

    public Object put(Object arg0, Object arg1) {
        return map.put(arg0, arg1);
    }

    public void putAll(Map arg0) {
        map.putAll(arg0);
    }

    public Object remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
        return map.values();
    }

}
