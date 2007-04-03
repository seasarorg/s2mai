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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.extension.jta.TransactionImpl;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.mai.util.TransactionManagerUtil;
import org.seasar.mai.xa.XAResourceImpl;

/**
 * @author Satoshi Kimura
 */
public class XAResourceSortInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4033855019500248834L;

    private TransactionManager transactionManager;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            sortXAResource();
        }
    }

    private void sortXAResource() {
        Transaction transaction = TransactionManagerUtil.getTransaction(transactionManager);
        if (transaction == null) {
            return;
        }
        if (transaction instanceof TransactionImpl == false) {
            return;
        }
        if (TransactionManagerUtil.hasTransaction(transactionManager) == false) {
            return;
        }
        List xaResourceWrappers = getXAResourceWrappers(transaction);
        sortXAResource(xaResourceWrappers);
    }

    private List getXAResourceWrappers(Transaction transaction) {
        TransactionImpl transactionImpl = (TransactionImpl) transaction;
        Field field = ClassUtil.getDeclaredField(TransactionImpl.class, "xaResourceWrappers_");
        field.setAccessible(true);
        List xaResourceWrappers = (List) FieldUtil.get(field, transactionImpl);
        return xaResourceWrappers;
    }

    private void sortXAResource(List xaResourceWrappers) {
        List mailResources = new ArrayList();

        Method getXAResourceMethod = ClassUtil.getDeclaredMethod(ClassUtil
                .forName("org.seasar.extension.jta.XAResourceWrapper"), "getXAResource", new Class[] {});
        getXAResourceMethod.setAccessible(true);

        for (int i = 0; i < xaResourceWrappers.size(); i++) {
            Object wrapper = xaResourceWrappers.get(i);
            XAResource physicalXAResource = (XAResource) MethodUtil.invoke(getXAResourceMethod, wrapper,
                    new Object[] {});
            if (physicalXAResource instanceof XAResourceImpl) {
                xaResourceWrappers.remove(i);
                mailResources.add(wrapper);
                i--;
            }
        }
        xaResourceWrappers.addAll(mailResources);
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
