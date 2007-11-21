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
package org.seasar.mai.xa;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.mai.interceptors.TestData;
import org.seasar.mai.interceptors.TestMai;
import org.seasar.mai.mail.AttachedFile;

/**
 * @author Satsohi Kimura
 */
public class TestServiceImpl implements TestService {

    private DataSource dataSource;

    private TestMai mai;

    public void execute() {
        sendMail();
        getConnection();
    }

    public void executeMultipleSending() {
        sendMailMulti();
        getConnection();

    }

    public void throwSQLException() {
        getConnection();

        sendMail();

        throw new SQLRuntimeException(new SQLException());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    private void sendMail() {
        TestData data = new TestData();
        data.setDay(22);
        data.setMonth(11);
        data.setYear(2006);
        data.setNo(12345);
        mai.sendMail(data);
    }

    private void sendMailMulti() {
        try {
            TestData data = new TestData();
            data.setDay(22);
            data.setMonth(11);
            data.setYear(2006);
            data.setNo(12345);

            data.setAttachedFile(new AttachedFile(new URL("http://example.com"), "file1.txt"));
            mai.sendMail(data);

            data.setNo(3456);
            data.setAttachedFile(new AttachedFile(new URL("http://example.com"), "file2.txt"));
            mai.sendMail(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setMai(TestMai mai) {
        this.mai = mai;
    }

}
