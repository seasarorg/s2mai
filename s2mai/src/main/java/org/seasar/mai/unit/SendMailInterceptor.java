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
package org.seasar.mai.unit;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.mai.mail.Mail;

/**
 * @author Satsohi Kimura
 * @author rokugen
 */
public class SendMailInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 3747445958429132574L;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getName();
        if (name.equals("send") == false) {
            return invocation.proceed();
        }

        SendMailTestUtil.addMail((Mail) invocation.getArguments()[0]);
        return null;
    }

}
