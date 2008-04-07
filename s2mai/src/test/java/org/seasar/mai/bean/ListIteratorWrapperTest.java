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
public class ListIteratorWrapperTest extends TestCase {

    public ListIteratorWrapperTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testNextAndPrevious(){
        List list = new ArrayList();
        list.add(new BeanClass("elem1"));
        list.add(new BeanClass("elem2"));
        ListIteratorWrapper actual = new ListIteratorWrapper(list.listIterator());
        Map bean = (Map) actual.next();
        assertEquals("elem1", bean.get("name"));
        bean = (Map) actual.next();
        assertEquals("elem2", bean.get("name"));
        bean = (Map) actual.previous();
        assertEquals("elem2", bean.get("name"));
        bean = (Map) actual.previous();
        assertEquals("elem1", bean.get("name"));
        
    }
    
    private class BeanClass{
        public String name;
        BeanClass(String name){
            this.name = name;
        }
    }

}
