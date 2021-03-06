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
package org.seasar.mai.interceptors;

import javax.mail.internet.InternetAddress;

import org.seasar.mai.mail.AttachedFile;
import org.seasar.mai.mail.MailAddress;

/**
 * @author Satsohi Kimura
 */
public class TestData {
    private String from;

    private InternetAddress to;

    private MailAddress cc;

    private String subject;

    private AttachedFile attachedFile;

    private int no;

    private int year;

    private int month;

    private int day;
    
    private String XHeader;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Returns the from.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     *            The from to set.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return Returns the to.
     */
    public InternetAddress getTo() {
        return to;
    }

    /**
     * @param to
     *            The to to set.
     */
    public void setTo(InternetAddress to) {
        this.to = to;
    }

    /**
     * @return Returns the subject.
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            The subject to set.
     */
    public final void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return Returns the cc.
     */
    public final MailAddress getCc() {
        return cc;
    }

    /**
     * @param cc
     *            The cc to set.
     */
    public final void setCc(MailAddress cc) {
        this.cc = cc;
    }

    /**
     * @return Returns the attachedFile.
     */
    public final AttachedFile getAttachedFile() {
        return attachedFile;
    }

    /**
     * @param attachedFile
     *            The attachedFile to set.
     */
    public final void setAttachedFile(AttachedFile attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getXHeader() {
        return XHeader;
    }

    public void setXHeader(String header) {
        XHeader = header;
    }

}
