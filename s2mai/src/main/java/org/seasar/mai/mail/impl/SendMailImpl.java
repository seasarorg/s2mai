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
package org.seasar.mai.mail.impl;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.mail.SendMail;


/**
 * @author Satsohi Kimura
 * @author rokugen
 */
public class SendMailImpl implements SendMail{


    private static Log log = LogFactory.getLog(SendMailImpl.class);

    /** デフォルトのプロトコル。「smtp」 */
    public static final String DEFAULT_PROTOCOL = "smtp";

    /**
     * デフォルトのポート。「-1」<br>
     * -1はプロトコルに応じた適切なポートを設定する特別な値。
     * */
    public static final int DEFAULT_PORT = -1;

    /** デフォルトのSMTPサーバ。「localhost」 */
    public static final String DEFAULT_HOST = "localhost";

    /** ISO-2022-JP */
    public static final String JIS_CHARSET = "ISO-2022-JP";

    private static final String RETURN_PATH_KEY = "mail.smtp.from";

    /** 接続タイムアウト */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

    /** 読込タイムアウト */
    private static final int DEFAULT_READ_TIMEOUT = 5000;

    private String protocol = DEFAULT_PROTOCOL;

    private String host = DEFAULT_HOST;

    private int port = DEFAULT_PORT;

    private String username;

    private String password;

    private String charset = JIS_CHARSET;

    private String returnPath;


    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    private int readTimeout = DEFAULT_READ_TIMEOUT;

    /**
     * コンストラクタ。
     */
    public SendMailImpl() {}

    
    public void send(Mail mail) {
        // TODO Auto-generated method stub
        
    }

    /**
     * エンコーディングに使用する文字コードを返します。
     * 
     * @return エンコーディングに使用する文字コード
     */
    public String getCharset() {
        return charset;
    }

    /**
     * メールの件名や本文のエンコーディングに使用する文字コードを指定します。
     * デフォルトは<code>ISO-2022-JP</code>です。
     * <p>
     * 日本語環境で利用する場合は通常変更する必要はありません。
     * 
     * @param charset エンコーディングに使用する文字コード
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * セットされたSMTPサーバのホスト名、またはIPアドレスを返します。
     * 
     * @return SMTPサーバのホスト名、またはIPアドレス
     */
    public String getHost() {
        return host;
    }

    /**
     * SMTPサーバのホスト名、またはIPアドレスをセットします。
     * デフォルトは localhost です。
     * 
     * @param host SMTPサーバのホスト名、またはIPアドレス
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return SMTPサーバ認証パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * SMTPサーバの接続認証が必要な場合にパスワードをセットします。
     * 
     * @param password SMTPサーバ認証パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return SMTPサーバのポート番号
     */
    public int getPort() {
        return port;
    }

    /**
     * SMTPサーバのポート番号をセットします。
     * 
     * @param port SMTPサーバのポート番号
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * プロトコルを返します。
     * 
     * @return プロトコル
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * プロトコルをセットします。デフォルトは「smtp」。
     * 
     * @param protocol プロトコル
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return Return-Pathアドレス
     */
    public String getReturnPath() {
        return returnPath;
    }

    /**
     * Return-Pathアドレスをセットします。
     * <p>
     * 送信するMailインスタンスに指定されたFromアドレス以外のアドレスをReturn-Pathとしたい場合に使用します。
     * ここでセットされたReturn-Pathより、MailインスタンスにセットされたReturn-Pathが優先されます。
     * 
     * @param returnPath Return-Pathアドレス
     */
    public void setReturnPath(String returnPath) {
        this.returnPath = returnPath;
    }

    /**
     * @return SMTPサーバ認証ユーザ名
     */
    public String getUsername() {
        return username;
    }

    /**
     * SMTPサーバの接続認証が必要な場合にユーザ名をセットします。
     * 
     * @param username SMTPサーバ認証ユーザ名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * SMTPサーバとの接続タイムアウトをセットします。
     * 単位はミリ秒。デフォルトは5,000ミリ秒(5秒)です。
     * <p>
     * -1を指定すると無限大になりますが、お薦めしません。
     * 
     * @since 1.1.4
     * @param connectionTimeout SMTPサーバとの接続タイムアウト
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * SMTPサーバへの送信時のタイムアウトをセットします。
     * 単位はミリ秒。デフォルトは5,000ミリ秒(5秒)です。<br>
     * 送信時にタイムアウトすると、<code>com.ozacc.mail.MailSendException</code>がスローされます。
     * <p>
     * -1を指定すると無限大になりますが、お薦めしません。
     * 
     * @since 1.1.4
     * @param readTimeout SMTPサーバへの送受信時のタイムアウト
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }


    public int getConnectionTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }


    public int getReadTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }



}
