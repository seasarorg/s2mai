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

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * @author rokugen
 */
public class ArrayWrapperTest extends TestCase {

    public ArrayWrapperTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGet() {
        String[] value = new String[]{"1","2","3","4"};
        ArrayWrapper wrapper = new ArrayWrapper(value);
        for(int i=0; i < wrapper.size(); i++){
            String actual = (String) wrapper.get(i);
            assertEquals(value[i], actual);
        }
        
        
    }

    public void testIsEmpty() {
        String[] value = new String[0];
        ArrayWrapper actual = new ArrayWrapper(value);
        assertTrue(actual.isEmpty());
        
        value = new String[]{"1","2"};
        actual = new ArrayWrapper(value);
        assertFalse(actual.isEmpty());
    }

    public void testIterator() {
        String[] value = new String[]{"1","2"};
        ArrayWrapper actual = new ArrayWrapper(value);
        Iterator itr = actual.iterator();
        assertEquals(ArrayIteratorWrapper.class, itr.getClass());        
    }

    public void testSize() {
        String[] value = new String[]{"1","2"};
        ArrayWrapper actual = new ArrayWrapper(value);
        assertEquals(2, actual.size());
        value = new String[]{"1","2","3","4"};
        actual = new ArrayWrapper(value);
        assertEquals(4, actual.size());
        
    }

    public void testToArray() {
        String[] value = new String[]{"1","2"};
        ArrayWrapper wrapper = new ArrayWrapper(value);
        Object[] actuals = wrapper.toArray();
        for(int i=0; i < actuals.length; i++){
            assertEquals(value[i], actuals[i]);
        }   
        
    }

}
