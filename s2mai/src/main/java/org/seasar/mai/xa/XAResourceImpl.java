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

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.seasar.extension.jta.xa.DefaultXAResource;
import org.seasar.framework.log.Logger;
import org.seasar.mai.mail.MailStack;
import org.seasar.mai.mail.impl.Invocation;
import org.seasar.mai.mail.impl.MailStackImpl;

/**
 * @author Satsohi Kimura
 */
public class XAResourceImpl extends DefaultXAResource implements XAResource {
    private Logger logger = Logger.getLogger(XAResourceImpl.class);

    private MailStack stack = new MailStackImpl();

    public XAResourceImpl() {
    }

    public void doBegin(Xid xid) throws XAException {
        logger.debug("START doBegin()");
        logger.debug("END   doBegin()");
    }

    public void doResume(Xid xid) throws XAException {
        logger.debug("START doResume()");
        stack.clear();
        logger.debug("END   doResume()");
    }

    public void doCommit(Xid xid, boolean onePhase) throws XAException {
        logger.debug("START doCommit()");
        while (stack.empty() == false) {
            Invocation invocation = stack.pop();
            invocation.send();
        }
        logger.debug("END   doCommit()");
    }

    public void doRollback(Xid xid) throws XAException {
        logger.debug("START doRollback()");
        stack.clear();
        logger.debug("END   doRollback()");
    }

    public MailStack getMailStack() {
        return stack;
    }

}
