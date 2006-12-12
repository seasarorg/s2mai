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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.AlternatorTool;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.IteratorTool;
import org.apache.velocity.tools.generic.ListTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.RenderTool;
import org.apache.velocity.tools.generic.SortTool;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.log.Logger;

/**
 * @author suzuki
 */
public class VelocityContextHelperImplTest extends S2TestCase {

    private static final String PATH = "VelocityContextHelperImplTest.dicon";
    private static Logger logger = Logger.getLogger(VelocityContextHelperImplTest.class);

    private VelocityContextHelperImpl helper;
    
    public VelocityContextHelperImplTest(String name) {
        super(name);
    }
    
    public void testCreateContext() {

        VelocityContext context = (VelocityContext)this.helper.createContext("test");
 
        AlternatorTool alternatorTool = (AlternatorTool)context.get("alternator");
        assertNotNull(alternatorTool);
        DateTool dateTool = (DateTool)context.get("date");
        assertNotNull(dateTool);
        EscapeTool escapeTool = (EscapeTool)context.get("escape");
        assertNotNull(escapeTool);
        IteratorTool iteratorTool = (IteratorTool)context.get("iterator");
        assertNotNull(iteratorTool);
        ListTool listTool = (ListTool)context.get("list");
        assertNotNull(listTool);
        MathTool mathTool = (MathTool)context.get("math");
        assertNotNull(mathTool);
        NumberTool numberTool = (NumberTool)context.get("number");
        assertNotNull(numberTool);
        RenderTool renderTool = (RenderTool)context.get("render");
        assertNotNull(renderTool);
        SortTool sortTool = (SortTool)context.get("sort");
        assertNotNull(sortTool);

        // TODO 入力データがMapのときとそれ以外のときのテスト

    }
    
    protected void setUp() throws Exception {
        this.include(PATH);        
    }
 
}
