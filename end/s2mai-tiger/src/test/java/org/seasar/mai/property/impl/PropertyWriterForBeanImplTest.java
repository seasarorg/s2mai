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
package org.seasar.mai.property.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.mai.mail.AttachedFile;

import com.ozacc.mail.Mail;

/**
 * @author rokugen
 */
public class PropertyWriterForBeanImplTest extends S2TestCase {
	private PropertyWriterForBeanImpl writer;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PropertyWriterForBeanImplTest.class);
	}

	protected void setUp() throws Exception {
		include("PropertyWriterForBeanImplTest.dicon");
	}
	
	public void testSetProperty() throws MalformedURLException{
		TigerTestDto dto = new TigerTestDto();
		List<AttachedFile>list = new ArrayList<AttachedFile>();
		list.add(new AttachedFile(new URL("http://example.com"),"添付ファイル1"));
		list.add(new AttachedFile(new URL("http://example.com"),"添付ファイル2"));
		dto.setFileList(list);
		Mail mail = new Mail();
		
		writer.setMailProperty(mail,dto);
		
		assertEquals("attached file 1", dto.getFileList().get(0).getFileName(), mail.getAttachmentFiles()[0].getName());
		assertEquals("attached file 2", dto.getFileList().get(1).getFileName(), mail.getAttachmentFiles()[1].getName());
		
	}
	
	public class TigerTestDto{
		private List<AttachedFile> fileList;

		public final List<AttachedFile> getFileList() {return fileList;}
		public final void setFileList(List<AttachedFile> fileList) {this.fileList = fileList;}
	}

	/**
	 * @param writer The writer to set.
	 */
	public final void setWriter(PropertyWriterForBeanImpl writer) {
		this.writer = writer;
	}

}
