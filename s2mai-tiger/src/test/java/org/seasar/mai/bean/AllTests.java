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
package org.seasar.mai.bean;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author rokugen
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.seasar.mai.bean under Tiger Evironment");
		//$JUnit-BEGIN$
		suite.addTestSuite(ArrayIteratorWrapperTest.class);
		suite.addTestSuite(ArrayWrapperTest.class);
		suite.addTestSuite(BeanWrapperTest.class);
		suite.addTestSuite(IteratorWrapperTest.class);
		suite.addTestSuite(ListIteratorWrapperTest.class);		
		//$JUnit-END$
		return suite;
	}

}
