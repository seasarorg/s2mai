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

import java.util.ListIterator;

/**
 * @author rokugen
 */
public class ListIteratorWrapper implements ListIterator {
    protected ListIterator listIterator;
    
    public ListIteratorWrapper(ListIterator listIterator){
        this.listIterator = listIterator;
    }

    public void add(Object arg0) {
        listIterator.add(arg0);
    }

    public boolean hasNext() {
        return listIterator.hasNext();
    }

    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }
    
    public Object next() {
        return WrapperFactory.convert(listIterator.next());
    }

    public int nextIndex() {
        return listIterator.nextIndex();
    }

    public Object previous() {
        return WrapperFactory.convert(listIterator.previous());
    }

    public int previousIndex() {
        return listIterator.previousIndex();
    }

    public void remove() {
        listIterator.remove();

    }

    public void set(Object arg0) {
        listIterator.set(arg0);
    }

}
