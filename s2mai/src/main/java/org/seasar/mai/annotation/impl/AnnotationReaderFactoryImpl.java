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
package org.seasar.mai.annotation.impl;

import java.lang.reflect.Constructor;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;
import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.AnnotationReaderFactory;

/**
 * @author Satsohi Kimura
 */
public class AnnotationReaderFactoryImpl implements AnnotationReaderFactory {

    private static final String TIGER_ANNOTATION_HANDLER_CLASS_NAME = "org.seasar.mai.annotation.impl.TigerAnnotationReader";

    private AnnotationReader annotationHandler;

    public AnnotationReaderFactoryImpl() {
        annotationHandler = new ConstantAnnotationReader();
        try {
            final Class clazz = ClassUtil.forName(TIGER_ANNOTATION_HANDLER_CLASS_NAME);
            final Constructor ctor = ClassUtil.getConstructor(clazz, new Class[] { AnnotationReader.class });
            annotationHandler = (AnnotationReader) ConstructorUtil
                    .newInstance(ctor, new Object[] { annotationHandler });
        } catch (final ClassNotFoundRuntimeException ignore) {
        }
    }

    public AnnotationReader createMaiAnnotationReader() {
        return annotationHandler;
    }

}
