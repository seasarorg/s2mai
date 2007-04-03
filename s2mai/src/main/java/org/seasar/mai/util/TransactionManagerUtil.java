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
package org.seasar.mai.util;

import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

/**
 * @author Satsohi Kimura
 */
public class TransactionManagerUtil {
    public static boolean hasTransaction(TransactionManager transactionManager) {
        if (transactionManager == null) {
            return false;
        }
        try {
            return transactionManager.getStatus() != Status.STATUS_NO_TRANSACTION;
        } catch (SystemException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Transaction getTransaction(TransactionManager transactionManager) {
        if (transactionManager == null) {
            return null;
        }
        return org.seasar.framework.util.TransactionManagerUtil.getTransaction(transactionManager);
    }

}
