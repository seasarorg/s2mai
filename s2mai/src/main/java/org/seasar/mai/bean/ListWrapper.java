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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author rokugen
 */
public class ListWrapper implements List{
    protected List list;
    
    public ListWrapper(List list){
        this.list = list;
    }

    public boolean add(Object o) {
        return list.add(o);
    }

    public void add(int index, Object o) {
        list.add(index,o);        
    }

    public boolean addAll(Collection arg0) {
        return list.addAll(arg0);
    }

    public boolean addAll(int arg0, Collection arg1) {        
        return list.addAll(arg0,arg1);
    }

    public void clear() {
        list.clear();        
    }

    public boolean contains(Object o) {        
        return list.contains(o);
    }

    public boolean containsAll(Collection arg0) {
        return list.containsAll(arg0);
    }

    public Object get(int index) {
        return WrapperFactory.convert(list.get(index));
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Iterator iterator() {
        return new IteratorWrapper(list.iterator());
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return new ListIteratorWrapper(list.listIterator());
    }

    public ListIterator listIterator(int index) {
        return new ListIteratorWrapper(list.listIterator(index));
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public Object remove(int index) {
        return list.remove(index);
    }

    public boolean removeAll(Collection arg0) {
        return list.removeAll(arg0);
    }

    public boolean retainAll(Collection arg0) {
        return list.retainAll(arg0);
    }

    public Object set(int arg0, Object arg1) {       
        return list.set(arg0, arg1);
    }

    public int size() {
        return list.size();
    }

    public List subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        Object[] array = new Object[size()];
        int i = 0;
        for (Iterator ite = iterator(); ite.hasNext();) {
            array[i++] = ite.next();
        }
        return array;    
    }

    public Object[] toArray(Object[] arg0) {        
        return toArray();
    }

}
