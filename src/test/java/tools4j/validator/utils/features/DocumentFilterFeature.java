/*
 * tools4j-validator - Framework for Validation
 * Copyright (c) 2014, David A. Bauer
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tools4j.validator.utils.features;

//import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

import java.awt.event.KeyEvent;
import java.util.Locale;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DocumentFilterFeature {
	
	protected FrameFixture testbed;
	
	@Before
	public void before() {
		Locale.setDefault(Locale.ENGLISH);
		
		if (System.getProperty("os.name").startsWith("Windows"))
			testbed = new FrameFixture(new Testbed());
	}
	
	@After
	public void tearDown() {
		if (testbed!=null)
			testbed.cleanUp();
	}
	
	@Test
	public void documentFilter_valid() {
		if (testbed!=null) {
			testbed.textBox("tfDocumentFilter").enterText("65.23");
			testbed.textBox("tfDocumentFilter").requireText("65.23");
		}
	}
	
	@Test
	public void documentFilter_invalid() {
		if (testbed!=null) {
			testbed.textBox("tfDocumentFilter").enterText("65.23a");
			testbed.optionPane().pressKey(KeyEvent.VK_ENTER);
			testbed.textBox("tfDocumentFilter").requireText("65.23"); // reset to 65.23
		}
	}
	
	@Test
	public void documentFilter_Output_valid() {
		if (testbed!=null) {
			testbed.textBox("tfDocumentFilter_Output").enterText("65.23");
			testbed.textBox("tfDocumentFilter_Output").requireText("65.23");
		}
	}
	
	@Test
	public void documentFilter_Output_invalid() {
		if (testbed!=null) {
			testbed.textBox("tfDocumentFilter_Output").enterText("65.23a");
			testbed.textBox("tfDocumentFilter_Output").requireText("65.23"); // reset to 65.23
			assertTrue(testbed.label("lblViolationMessage").text().length()>0);
		}
	}
}
