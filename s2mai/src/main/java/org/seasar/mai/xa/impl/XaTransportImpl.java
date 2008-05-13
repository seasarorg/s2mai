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
package org.seasar.mai.xa.impl;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.seasar.framework.util.TransactionUtil;
import org.seasar.mai.mail.Mail;
import org.seasar.mai.mail.SendMail;
import org.seasar.mai.mail.Transport;
import org.seasar.mai.util.TransactionManagerUtil;
import org.seasar.mai.xa.Invocation;
import org.seasar.mai.xa.MailStack;

/**
 * @author Satsohi Kimura
 */
public class XaTransportImpl implements Transport {

    private TransactionManager transactionManager;

    public void send(Mail mail, SendMail sendMail) {
        Invocation invocation = new Invocation(sendMail, mail);

        if (TransactionManagerUtil.hasTransaction(transactionManager)) {
            MailStack mailStack = getMailStack();
            if (mailStack != null) {
                mailStack.push(invocation);
                return;
            }
        }
        invocation.send();
    }

    private MailStack getMailStack() {
        Transaction tx = TransactionManagerUtil.getTransaction(transactionManager);
        if (tx == null) {
            return null;
        }
        XAResourceImpl resource = new XAResourceImpl();
        TransactionUtil.enlistResource(tx, resource);
        return resource.getMailStack();
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
