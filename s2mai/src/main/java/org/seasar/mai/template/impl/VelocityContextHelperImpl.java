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
package org.seasar.mai.template.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.template.ContextHelper;

/**
 * @author Satsohi Kimura
 */
public class VelocityContextHelperImpl implements ContextHelper {

    // private static Logger logger = Logger.getLogger(VelocityContextHelperImplTest.class);

    private VelocityContext context = new VelocityContext();

    public void addTool(String name, Object obj) {
        this.context.put(name, obj);
    }

    public Object createContext(Object data) {
        if (data == null) {
            return null;
        }

        Map map = toMap(data);

        VelocityContext context = toVelocityContext(map);

        return context;
    }

    private Map toMap(Object data) {
        if (data instanceof Map) {
            return (Map) data;
        }
        Map map = new HashMap();
        map.put(S2MaiConstants.DATA_NAME, data);
        return map;
    }

    private VelocityContext toVelocityContext(Map map) {
        VelocityContext context = (VelocityContext) this.context.clone();
        Set set = map.keySet();
        for (Iterator iter = set.iterator(); iter.hasNext();) {
            Object key = iter.next();
            context.put(key.toString(), map.get(key));
        }
        return context;
    }

}
