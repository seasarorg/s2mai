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
package org.seasar.mai.mail;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * メールを保持するクラスです。送信もします。
 * @author rokugen
 */
public class Mail {    

    /** <code>ISO-2022-JP</code> */
    public static final String JIS_CHARSET = "ISO-2022-JP";

    private String charset = JIS_CHARSET;

    protected String text;

    protected InternetAddress from;

    protected String subject;

    protected List to;

    protected List cc;

    protected List bcc;

    protected InternetAddress returnPath;

    protected InternetAddress replyTo;

    protected Map headers;

    protected String htmlText;

    protected List attachedFiles;

    /**
     * コンストラクタ。
     */
    public Mail() {}

//    /**
//     * コンストラクタ。
//     * 宛先や差出人の名前をエンコードする時に使用する文字コードを指定します。
//     * <p>
//     * 日本語環境で利用する場合は通常設定する必要はありません。
//     * 
//     * @param charset エンコードに使用する文字コード
//     */
//    public Mail(String charset) {
//        this();
//        this.charset = charset;
//    }

    public Mail(Mail original) {
        this.bcc = original.bcc;
        this.cc = original.cc;
        this.charset = original.charset;
        this.from = original.from;
        this.replyTo = original.replyTo;
        this.returnPath = original.returnPath;
        this.subject = original.subject;
        this.text = original.text;
        this.to = original.to;
        this.headers = original.headers;
        this.htmlText = original.htmlText;
        this.attachedFiles = original.attachedFiles;        
    }

    public String getCharset() {
        return charset;
    }

    public void addTo(InternetAddress address) {
        if (to == null) {
            to = new ArrayList();
        }
        to.add(address);
    }

    public void addTo(String email) throws IllegalArgumentException {
        try {
            addTo(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addTo(MailAddress address) throws IllegalArgumentException {
        try {
            addTo(new InternetAddress(address.getAddress(), address.getPersonal(), charset));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress[] getTo() {
        if (to == null) {
            return new InternetAddress[0];
        }
        return (InternetAddress[])to.toArray(new InternetAddress[to.size()]);
    }

    public void addCc(InternetAddress address) {
        if (cc == null) {
            cc = new ArrayList();
        }
        cc.add(address);
    }

    public void addCc(String email) throws IllegalArgumentException {
        try {
            addCc(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void addCc(MailAddress address) throws IllegalArgumentException {
        try {
            addCc(new InternetAddress(address.getAddress(), address.getPersonal(), charset));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress[] getCc() {
        if (cc == null) {
            return new InternetAddress[0];
        }
        return (InternetAddress[])cc.toArray(new InternetAddress[cc.size()]);
    }

    public void addBcc(InternetAddress address) {
        if (bcc == null) {
            bcc = new ArrayList();
        }
        bcc.add(address);
    }

    public void addBcc(String email) throws IllegalArgumentException {
        try {
            addBcc(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress[] getBcc() {
        if (bcc == null) {
            return new InternetAddress[0];
        }
        return (InternetAddress[])bcc.toArray(new InternetAddress[bcc.size()]);
    }

    public void setFrom(InternetAddress address) {
        from = address;
    }

    public void setFrom(String email) throws IllegalArgumentException {
        try {
            setFrom(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void setFrom(MailAddress address) throws IllegalArgumentException {
        try {
            setFrom(new InternetAddress(address.getAddress(), address.getPersonal(), charset));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setReturnPath(InternetAddress address) {
        returnPath = address;
    }

    public void setReturnPath(String email) throws IllegalArgumentException {
        try {
            setReturnPath(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress getReturnPath() {
        return returnPath;
    }

    public void setReplyTo(InternetAddress address) {
        replyTo = address;
    }

    public void setReplyTo(String email) throws IllegalArgumentException {
        try {
            setReplyTo(new InternetAddress(email));
        } catch (AddressException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public String getSubject() {
        if (subject == null) {
            return "";
        }
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        if (text == null) {
            return "";
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public void addXHeader(String name, String value) {
//        if (headers == null) {
//            headers = new HashMap();
//        }
//        if (name.startsWith("X-")) {
//            headers.put(name, value);
//        } else {
//            headers.put("X-" + name, value);
//        }
//    }

    public void addHeader(String name, String value) {
        if (headers == null) {
            headers = new HashMap();
        }
        headers.put(name, value);
    }

    public Map getHeaders() {
        if (headers == null) {
            return null;
        }
        return Collections.unmodifiableMap(headers);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(1000);
        buf.append("Mail\n");
        buf.append("Return-Path: ").append(returnPath).append("\n");
        buf.append("From: ").append(from != null ? from.toUnicodeString() : null).append("\n");
        buf.append("To: ").append(arrayToCommaDelimitedString(to)).append("\n");
        buf.append("Cc: ").append(arrayToCommaDelimitedString(cc)).append("\n");
        buf.append("Bcc: ").append(arrayToCommaDelimitedString(bcc)).append("\n");
        buf.append("Subject: ").append(subject).append("\n");

        if (headers != null) {
            for (Iterator itr = headers.keySet().iterator(); itr.hasNext();) {
                String header = (String)itr.next();
                String value = (String)headers.get(header);
                buf.append(header).append(": ").append(value).append("\n");
            }
        }

        buf.append("\n");
        buf.append(text);

        if (htmlText != null) {
            buf.append("\n\n-----\n\n");
            buf.append(htmlText);
        }

        return buf.toString();
    }

    protected String arrayToCommaDelimitedString(List list) {
        if (list == null) {
            return "null";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0, num = list.size(); i < num; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(((InternetAddress)list.get(i)).toUnicodeString());
            }
            return sb.toString();
        }
    }

    /**
     * セットされている送信先アドレス(Toアドレス)を全てクリアします。
     *
     * @since 1.0.2
     */
    public void clearTo() {
        to = null;
    }

    /**
     * セットされているCCアドレスを全てクリアします。
     *
     * @since 1.0.2
     */
    public void clearCc() {
        cc = null;
    }

    /**
     * セットされているBCCアドレスを全てクリアします。
     *
     * @since 1.0.2
     */
    public void clearBcc() {
        bcc = null;
    }

    /**
     * HTMLの本文をセットします。
     * 
     * @since 1.1
     * 
     * @param htmlText HTMLの本文
     */
    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    /**
     * HTMLの本文を返します。
     * 
     * @since 1.1
     * 
     * @return HTMLの本文。またはnull。
     */
    public String getHtmlText() {
        return htmlText;
    }

    public void addFile(File file) {
        addFile(file, file.getName());
    }

    public void addFile(File file, String fileName) {
        if (attachedFiles == null) {
            initAttachedFiles();
        }
        attachedFiles.add(new AttachedFile(file,fileName));
    }

    public void addFile(URL url, String fileName) {
        if (attachedFiles == null) {
            initAttachedFiles();
        }
        attachedFiles.add(new AttachedFile(url,fileName));
    }

    public void addFile(InputStream is, String fileName) {
        if (attachedFiles == null) {
            initAttachedFiles();
        }
        attachedFiles.add(new AttachedFile(is,fileName));
    }

    private void initAttachedFiles() {
        attachedFiles = new ArrayList();
    }

    public AttachedFile[] getAttachedFiles() {
        if (attachedFiles == null) {
            return new AttachedFile[0];
        }
        return (AttachedFile[])attachedFiles
                .toArray(new AttachedFile[attachedFiles.size()]);
    }

    public boolean isHtmlMail() {
        return (htmlText != null);
    }

    public boolean isFileAttached() {
        return attachedFiles != null && attachedFiles.size() > 0;
    }

    public boolean isMultipartMail() {
        return isHtmlMail() || isFileAttached();
    }

    public void clearFile() {
        initAttachedFiles();
    }

    
}
