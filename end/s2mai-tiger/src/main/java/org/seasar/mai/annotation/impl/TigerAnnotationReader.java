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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.Bcc;
import org.seasar.mai.annotation.Cc;
import org.seasar.mai.annotation.From;
import org.seasar.mai.annotation.MailAddr;
import org.seasar.mai.annotation.ReplyTo;
import org.seasar.mai.annotation.ReturnPath;
import org.seasar.mai.annotation.Subject;
import org.seasar.mai.annotation.To;
import org.seasar.mai.mail.MailAddress;

/**
 * @author rokugen
 */
public class TigerAnnotationReader implements AnnotationReader {
	private AnnotationReader next;
	
	public TigerAnnotationReader(final AnnotationReader next) {
		this.next = next;
	}

	public Object getTo(Method method) {
		final To to = getAnnotation(method, To.class);
		if (to != null) {			
            return convertMailAddress(to.value());
        }
        if (next != null) {
            return next.getTo(method);
        }
		return null;
	}

	public Object getCc(Method method) {
		final Cc cc = getAnnotation(method, Cc.class);
		if (cc != null) {			
            return convertMailAddress(cc.value());
        }
        if (next != null) {
            return next.getCc(method);
        }		
		return null;
	}

	public Object getBcc(Method method) {
		final Bcc bcc = getAnnotation(method, Bcc.class);
		if (bcc != null) {
            return bcc.value();
        }
        if (next != null) {
            return next.getBcc(method);
        }
		return null;
	}

	public Object getSubject(Method method) {
		final Subject subject = getAnnotation(method, Subject.class);
		if (subject != null) {			
            return subject.value();
        }
        if (next != null) {
            return next.getSubject(method);
        }
		return null;
	}

	public Object getReplyTo(Method method) {
		final ReplyTo replyTo = getAnnotation(method, ReplyTo.class);
        if (replyTo != null) {           
            return convertMailAddress(replyTo.value());
        }
        if (next != null) {
            return next.getReplyTo(method);
        }
		return null;
	}

	public Object getReturnPath(Method method) {
		final ReturnPath returnPath = getAnnotation(method, ReturnPath.class);
		if (returnPath != null) {			
            return returnPath.value();
        }
        if (next != null) {
            return next.getReturnPath(method);
        }
		return null;
	}

	public Object getFrom(Method method) {
        final From from = getAnnotation(method, From.class);
        if (from != null) {           
            return convertMailAddress(from.value());
        }
        if (next != null) {
            return next.getFrom(method);
        }
        return null;
	}
    
    protected MailAddress convertMailAddress(MailAddr addr){
        return  new MailAddress(addr.address(), addr.personal());
    }
	
	protected List<MailAddress> convertMailAddress(MailAddr[] addrs){
		List<MailAddress> list = new ArrayList<MailAddress>();
		for(int i=0; i < addrs.length; i++){
			MailAddress mailAddress = this.convertMailAddress(addrs[i]);
			list.add(mailAddress);
		}
		
		return list;		
	}
	
    protected <T extends Annotation> T getAnnotation(final Method method, final Class<T> annotationType) {
    	final T annotation = method.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        Class<?> clazz = method.getDeclaringClass();        
        return clazz.getAnnotation(annotationType);
    }

}
