/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.mai.property.mail;

/**
 * @author rokugen
 */
public class MailAddress {
    private String personal;
    private String address;
    
    public MailAddress() {
     
    }
    
    public MailAddress(String address){
        this.address = address;
    }
    
    public MailAddress(String address, String personal) {
        this.address = address;
        this.personal = personal;
    }
    
    /**
     * @return Returns the address.
     */
    public final String getAddress() {
        return address;
    }
    /**
     * @param address The address to set.
     */
    public final void setAddress(String address) {
        this.address = address;
    }
    /**
     * @return Returns the personal.
     */
    public final String getPersonal() {
        return personal;
    }
    /**
     * @param personal The personal to set.
     */
    public final void setPersonal(String personal) {
        this.personal = personal;
    }

}
