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

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author rokugen
 */
public class ArrayIteratorWrapper implements Iterator {
    protected Object array;
    protected int length;
    protected int index;
    
    public ArrayIteratorWrapper(Object array){
        this.array = array;
        length = Array.getLength(array);        
    }

    public boolean hasNext() {
        return index < length;
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException("length:" + length + ", index:"
                    + index);
        }
        return WrapperFactory.convert(Array.get(array, index++));
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

}
