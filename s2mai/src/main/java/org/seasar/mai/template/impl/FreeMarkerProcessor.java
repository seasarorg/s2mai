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
package org.seasar.mai.template.impl;

import org.seasar.framework.container.S2Container;
import org.seasar.mai.S2MaiConstants;
import org.seasar.mai.template.TemplateProcessor;
import org.seasar.mai.util.FreeMarkerUtil;

/**
 * @author Satsohi Kimura
 */
public class FreeMarkerProcessor implements TemplateProcessor {
    private S2Container container;

    public void init() {
        String enc = (String) container.getComponent(S2MaiConstants.TEMPLATE_ENCODING);
        FreeMarkerUtil.setDefaultEncoding(enc);
    }
    
    public String process(String templateText, Object context) {
        return FreeMarkerUtil.process(templateText, context);
    }

    public String processResource(String path, Object context) {
        String pathWithExt = path + "." + S2MaiConstants.FREEMARKER_EXT;
        return FreeMarkerUtil.processResource(pathWithExt, context);
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

}
