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
package tools4j.validator.features;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import javax.json.JsonArray;

import org.junit.Before;
import org.junit.Test;

import tools4j.validator.DateValidator;

public class DateFeature {
	private DateValidator v1;
	private DateValidator v2;
	private DateValidator v3;
	
	@Before
	public void before() {
		v1 = new DateValidator();
		v2 = new DateValidator();
		v3 = new DateValidator();
	}

	@Test
	public void test_resetConstraints() {
		v1.setPattern("");
		v1.setValid(true);
		v1.setPast(true);
		v1.setFuture(true);
		
		v1.setConstraints("{}");
		assertTrue(((JsonArray)v1.getConstraintsAsJsonObject().get("constraints")).size()==0);
	}
	
	@Test
	public void test_Pattern_invalid() {
		v1.setPattern("yyyy-mm-dd");
		v1.validateString("2014.12-24");
		assertTrue(v1.getViolationMessage()!=null);
		assertEquals("Pattern", v1.getViolationConstraint());
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2014.12-24");
		assertTrue(v2.getViolationMessage()!=null);
		assertEquals("Pattern", v2.getViolationConstraint());
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2014.12-24");
		assertTrue(v3.getViolationMessage()!=null);
		assertEquals("Pattern", v3.getViolationConstraint());
	}
	
	@Test
	public void test_Pattern_valid() {
		v1.setPattern("yyyy-mm-dd");
		v1.validateString("2014-12-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2014-12-24");
		assertTrue(v2.getViolationMessage()==null);
		assertTrue(v2.getViolationConstraint()==null);
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2014-12-24");
		assertTrue(v3.getViolationMessage()==null);
		assertTrue(v3.getViolationConstraint()==null);
	}
	
	@Test
	public void test_Valid_invalid() {
		v1.setValid(true);
		v1.validate(new GregorianCalendar(2014, 12, 32));
		assertTrue(v1.getViolationMessage()!=null);
		assertEquals("Valid", v1.getViolationConstraint());
		v1.setPattern("yyyy-mm-dd");
		v1.validateString("2014-12-32");
		assertTrue(v1.getViolationMessage()!=null);
		assertEquals("Pattern|Valid", v1.getViolationConstraint());
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validate(new GregorianCalendar(2014, 12, 32));
		assertTrue(v2.getViolationMessage()!=null);
		assertEquals("Valid", v2.getViolationConstraint());
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validate(new GregorianCalendar(2014, 12, 32));
		assertTrue(v3.getViolationMessage()!=null);
		assertEquals("Valid", v3.getViolationConstraint());
	}
	
	@Test
	public void test_Valid_valid() {
		v1.setValid(false);
		v1.setPattern("yyyy-mm-dd");
		v1.validateString("2014-12-32");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		v1.setValid(true);
		v1.validateString("2014-12-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2014-12-24");
		assertTrue(v2.getViolationMessage()==null);
		assertTrue(v2.getViolationConstraint()==null);
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2014-12-24");
		assertTrue(v3.getViolationMessage()==null);
		assertTrue(v3.getViolationConstraint()==null);
	}
	
	@Test
	public void test_Past_invalid() {
		v1.setPattern("yyyy-mm-dd");
		v1.setPast(true);
		v1.validateString("2194-12-24");
		assertTrue(v1.getViolationMessage()!=null);
		assertEquals("Past", v1.getViolationConstraint());
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2194-12-24");
		assertTrue(v2.getViolationMessage()!=null);
		assertEquals("Past", v2.getViolationConstraint());
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2194-12-24");
		assertTrue(v3.getViolationMessage()!=null);
		assertEquals("Past", v3.getViolationConstraint());
	}
	
	@Test
	public void test_Past_valid() {
		v1.setPattern("yyyy-mm-dd");
		v1.setPast(false);
		v1.validateString("2194-02-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		v1.setPast(true);
		v1.validateString("2014-02-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2014-02-24");
		assertTrue(v2.getViolationMessage()==null);
		assertTrue(v2.getViolationConstraint()==null);
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2014-02-24");
		assertTrue(v3.getViolationMessage()==null);
		assertTrue(v3.getViolationConstraint()==null);
	}
	
	@Test
	public void test_Future_invalid() {
		v1.setPattern("yyyy-mm-dd");
		v1.setFuture(true);
		v1.validateString("2014-02-24");
		assertTrue(v1.getViolationMessage()!=null);
		assertEquals("Future", v1.getViolationConstraint());
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2014-02-24");
		assertTrue(v2.getViolationMessage()!=null);
		assertEquals("Future", v2.getViolationConstraint());
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2014-02-24");
		assertTrue(v3.getViolationMessage()!=null);
		assertEquals("Future", v3.getViolationConstraint());
	}
	
	@Test
	public void test_Future_valid() {
		v1.setPattern("yyyy-mm-dd");
		v1.setFuture(false);
		v1.validateString("2014-02-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		v1.setFuture(true);
		v1.validateString("2194-02-24");
		assertTrue(v1.getViolationMessage()==null);
		assertTrue(v1.getViolationConstraint()==null);
		
		v2.setConstraints(v1.getConstraintsAsJsonObject());
		v2.validateString("2194-02-24");
		assertTrue(v2.getViolationMessage()==null);
		assertTrue(v2.getViolationConstraint()==null);
		
		v3.setConstraints(v1.getConstraintsAsJsonObject().toString());
		v3.validateString("2194-02-24");
		assertTrue(v3.getViolationMessage()==null);
		assertTrue(v3.getViolationConstraint()==null);
	}
}
