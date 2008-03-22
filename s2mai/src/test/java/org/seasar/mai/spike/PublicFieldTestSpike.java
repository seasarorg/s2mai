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
package org.seasar.mai.spike;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.seasar.framework.util.InputStreamReaderUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.ReaderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.mai.template.impl.VelocityProcessorImpl;
import org.seasar.mai.util.FreeMarkerUtil;

import junit.framework.TestCase;

/**
 * @author rokugen
 */
public class PublicFieldTestSpike extends TestCase {
    private static final String TEMPLATE_FM = "org/seasar/mai/spike/maptest_template.ftl";
    private static final String TEMPLATE_VELO = "/src/test/resources/org/seasar/mai/spike/maptest_template.vm";
    private static final String RESULT = "org/seasar/mai/spike/maptest_result.txt";

    public PublicFieldTestSpike(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    private Map getData(){
        Map data = new HashMap();
        data.put("number", new Integer(112));
        data.put("name", "rokugen");
        List children = new ArrayList();
        ChildDto child = new ChildDto();
        child.setChildName("child1");
        children.add(child);
        child = new ChildDto();
        child.setChildName("child2");
        children.add(child);
        child = new ChildDto();
        child.setChildName("child3");
        children.add(child);
        data.put("children", children);
        
        return data;
    }
    
    private String getExpected(){
        InputStream is = null;
        try {
            is = ResourceUtil.getResourceAsStream(RESULT);
            Reader reader = InputStreamReaderUtil.create(is, "UTF-8");
            return ReaderUtil.readText(reader);
        } finally {
            InputStreamUtil.close(is);
        }
    }
    
    public void testFreeMarkerWithMap()throws Exception{
        Map data = getData();
        Map root = new HashMap();
        root.put("data", data);
        
        String actual = FreeMarkerUtil.processResource(TEMPLATE_FM, root);
        String expected = getExpected();
        assertEquals(expected, actual);
        
    }
    
    public void testVelocityWithMap()throws Exception{
        Map data = getData();
        VelocityProcessorImpl velo = new VelocityProcessorImpl();
        VelocityContext context = new VelocityContext();
        context.put("data", data);
        velo.init();
        String actual = velo.processResource(TEMPLATE_VELO, context);
        String expected = getExpected();
        assertEquals(expected, actual);
        
    }
    
    
   public class ChildDto{
        private String childName;

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }
        
    }

}
