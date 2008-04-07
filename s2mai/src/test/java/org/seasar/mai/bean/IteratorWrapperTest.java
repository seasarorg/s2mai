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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author rokugen
 */
public class IteratorWrapperTest extends TestCase {

    public IteratorWrapperTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testNext(){
        List list = new ArrayList();
        list.add("elem1");
        list.add("elem2");
        IteratorWrapper actual = new IteratorWrapper(list.iterator());
        assertEquals("elem1", actual.next());
        assertEquals("elem2", actual.next());
        assertFalse(actual.hasNext());
        
        list = new ArrayList();
        list.add(new BeanClass("elem1"));
        list.add(new BeanClass("elem2"));
        
        actual = new IteratorWrapper(list.iterator());
        Map elem = (Map) actual.next();
        assertEquals("elem1", elem.get("name"));
        elem = (Map) actual.next();
        assertEquals("elem2", elem.get("name"));
        
    }
    
    private class BeanClass{
        public String name;
        BeanClass(String name){
            this.name = name;
        }
    }

}
