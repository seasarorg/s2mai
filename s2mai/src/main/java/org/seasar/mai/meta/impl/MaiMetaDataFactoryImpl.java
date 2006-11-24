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
package org.seasar.mai.meta.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.AnnotationReaderFactory;
import org.seasar.mai.meta.MaiMetaData;
import org.seasar.mai.meta.MaiMetaDataFactory;
import org.seasar.mai.property.mail.MailPropertyWriterFactory;

/**
 * @author Satoshi Kimura
 * 
 */
public class MaiMetaDataFactoryImpl implements MaiMetaDataFactory {
    private AnnotationReaderFactory annotationReaderFactory;
    private MailPropertyWriterFactory mailPropertyWriterFactory;

    private Map maiMetaDataCache = new HashMap();

    public MaiMetaData getMaiMetaData(Class maiClass) {
        if (maiMetaDataCache.containsKey(maiClass)) {
            return (MaiMetaData) maiMetaDataCache.get(maiClass);
        }
        AnnotationReader annotationReader = annotationReaderFactory.createMaiAnnotationReader();
        MaiMetaData metaData = new MaiMetaDataImpl(maiClass, annotationReader, mailPropertyWriterFactory);
        maiMetaDataCache.put(maiClass, metaData);
        return metaData;
    }

    public void setAnnotationReaderFactory(AnnotationReaderFactory annotationReaderFactory) {
        this.annotationReaderFactory = annotationReaderFactory;
    }

    /**
     * @param mailPropertyWriterFactory The mailPropertyWriterFactory to set.
     */
    public final void setMailPropertyWriterFactory(MailPropertyWriterFactory mailPropertyWriterFactory) {
        this.mailPropertyWriterFactory = mailPropertyWriterFactory;
    }
}
