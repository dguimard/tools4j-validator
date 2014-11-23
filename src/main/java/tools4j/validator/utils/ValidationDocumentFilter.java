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
package tools4j.validator.utils;

import java.awt.Window;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import tools4j.validator.StringValidator;

public class ValidationDocumentFilter extends DocumentFilter {
	protected Window window;
	protected StringValidator validator;
	
	public ValidationDocumentFilter(Window window, StringValidator validator) {
		this.window = window;
		this.validator = validator;
	}
	
	@Override
	public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
		String content = fb.getDocument().getText(0, fb.getDocument().getLength());
		String string  = content.substring(offset, offset+length);
		super.remove(fb, offset, length);
		if (!validator.validate(window, fb.getDocument().getText(0, fb.getDocument().getLength())))
			super.insertString(fb, offset, string, null);
	}
	            
	@Override
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		String content = fb.getDocument().getText(0, fb.getDocument().getLength());
		StringBuffer sb = new StringBuffer();
		if (offset < fb.getDocument().getLength()) {
			if (offset>0) sb.append(content.substring(0, offset));
			sb.append(string);
			sb.append(content.substring(offset, content.length()));
		}
		else {
			sb.append(content);
			sb.append(string);
		}
		
		super.insertString(fb, offset, string, attr);
		
		if (!validator.validate(window, sb.toString()))
			super.remove(fb, offset, string.length());
	}
	
	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		insertString(fb, offset, text, attrs);
	}
}
