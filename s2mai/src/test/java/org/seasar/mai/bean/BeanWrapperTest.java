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

import java.util.Map;

import junit.framework.TestCase;

/**
 * @author rokugen
 */
public class BeanWrapperTest extends TestCase {

    public BeanWrapperTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testContainsKey()throws Exception{
        BeanClass bean = new BeanClass(1,"name1");
        Map actual = (Map)WrapperFactory.convert(bean);
        assertTrue(actual.containsKey("id"));
        assertTrue(actual.containsKey("name"));
        assertTrue(actual.containsKey("child"));
        assertFalse(actual.containsKey("children"));
    }
    
    public void testGet()throws Exception{
        BeanClass bean = new BeanClass(1,"name1");
        bean.child = new BeanClass(2,"name2");
        
        Map actual = (Map)WrapperFactory.convert(bean);
        assertEquals(1, ((Integer)actual.get("id")).intValue());
        assertEquals("name1", (String)actual.get("name"));
        Map child = (Map)actual.get("child");
        assertEquals(2, ((Integer)child.get("id")).intValue());
        assertEquals("name2", (String)child.get("name"));
        
        assertNull(actual.get("children"));
        
    }
    
    class BeanClass{
        public int id;
        public String name;
        public BeanClass child;
        
        
        BeanClass(int id, String name){
            this.id = id;
            this.name = name;
        }
        
        
    }

}
