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
package org.seasar.mai.mail;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author rokugen
 */
public class AttachedFile {
    private String fileName;

    private File file;

    private InputStream inputStream;

    private URL url;

    public AttachedFile(File file) {
        this.file = file;
    }

    public AttachedFile(File file, String fileName) {
        this.file = file;
        this.fileName = fileName;
    }

    public AttachedFile(InputStream inputStream, String fileName) {
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    public AttachedFile(URL url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    /**
     * @return Returns the file.
     */
    public final File getFile() {
        return file;
    }

    /**
     * @return Returns the fileName.
     */
    public final String getFileName() {
        return fileName;
    }

    /**
     * @return Returns the inputStream.
     */
    public final InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return Returns the url.
     */
    public final URL getUrl() {
        return url;
    }

}
