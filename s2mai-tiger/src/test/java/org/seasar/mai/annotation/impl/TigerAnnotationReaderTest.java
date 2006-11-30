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

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.annotation.AnnotationReader;
import org.seasar.mai.annotation.AnnotationReaderFactory;



/**
 * @author rokugen
 */
public class TigerAnnotationReaderTest extends S2TestCase {
	private AnnotationReaderFactory factory;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TigerAnnotationReaderTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		include("TigerAnnotationReaderTest.dicon");
	}
	
	public void testGetValue(){
		AnnotationReader annotationReader = factory.createMaiAnnotationReader();
		
		assertEquals("class", TigerAnnotationReader.class, annotationReader.getClass());
	}

	/**
	 * @param factory The factory to set.
	 */
	public final void setFactory(AnnotationReaderFactory factory) {
		this.factory = factory;
	}


}
