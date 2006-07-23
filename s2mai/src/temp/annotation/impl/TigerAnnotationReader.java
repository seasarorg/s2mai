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
package org.seasar.mai.annotation.impl;

import java.lang.reflect.Method;

import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.Bcc;
import org.seasar.mai.annotation.Cc;
import org.seasar.mai.annotation.From;
import org.seasar.mai.annotation.ReplyTo;
import org.seasar.mai.annotation.ReturnPath;
import org.seasar.mai.annotation.Subject;
import org.seasar.mai.annotation.To;

/**
 * @author Satsohi Kimura
 */
public class TigerAnnotationReader implements AnnotationReader {

    public String[] getTo(Method method) {
        To to = method.getAnnotation(To.class);
        if (to == null) {
            to = method.getDeclaringClass().getAnnotation(To.class);
        }
        if (to == null) {
            return null;
        }
        return to.value();
    }

    public String[] getCc(Method method) {
        Cc cc = method.getAnnotation(Cc.class);
        if (cc == null) {
            cc = method.getDeclaringClass().getAnnotation(Cc.class);
        }
        if (cc == null) {
            return null;
        }
        return cc.value();
    }

    public String[] getBcc(Method method) {
        Bcc bcc = method.getAnnotation(Bcc.class);
        if (bcc == null) {
            bcc = method.getDeclaringClass().getAnnotation(Bcc.class);
        }
        if (bcc == null) {
            return null;
        }
        return bcc.value();
    }

    public String getSubject(Method method) {
        Subject subject = method.getAnnotation(Subject.class);
        if (subject == null) {
            subject = method.getDeclaringClass().getAnnotation(Subject.class);
        }
        if (subject == null) {
            return null;
        }
        return subject.value();
    }

    public String getFrom(Method method) {
        From from = method.getAnnotation(From.class);
        if (from == null) {
            from = method.getDeclaringClass().getAnnotation(From.class);
        }
        if (from == null) {
            return null;
        }
        return from.value();
    }

    public String getReplyTo(Method method) {
        ReplyTo replyTo = method.getAnnotation(ReplyTo.class);
        if (replyTo == null) {
            replyTo = method.getDeclaringClass().getAnnotation(ReplyTo.class);
        }
        if (replyTo == null) {
            return null;
        }
        return replyTo.value();
    }

    public String getReturnPath(Method method) {
        ReturnPath returnPath = method.getAnnotation(ReturnPath.class);
        if (returnPath == null) {
            returnPath = method.getDeclaringClass().getAnnotation(ReturnPath.class);
        }
        if (returnPath == null) {
            return null;
        }
        return returnPath.value();
    }

}
