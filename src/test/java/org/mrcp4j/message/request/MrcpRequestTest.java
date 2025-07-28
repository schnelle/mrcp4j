/*
 * MRCP4J - Java API implementation of MRCPv2 specification
 *
 * Copyright (C) 2005-2006 SpeechForge - http://www.speechforge.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 * Contact: ngodfredsen@users.sourceforge.net
 *
 */
package org.mrcp4j.message.request;

import org.junit.Test;
import org.mrcp4j.MrcpMethodName;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpRequest class.
 */
public class MrcpRequestTest {

    @Test
    public void testConstructorWithMethodName() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.SPEAK);
        
        assertEquals(MrcpMethodName.SPEAK, request.getMethodName());
        assertEquals("SPEAK", request.getMethodNameAsString());
    }

    @Test
    public void testConstructorWithMethodNameString() {
        TestMrcpRequest request = new TestMrcpRequest("CUSTOM-METHOD");
        
        assertNull(request.getMethodName());
        assertEquals("CUSTOM-METHOD", request.getMethodNameAsString());
    }

    @Test
    public void testGetMethodNameAsStringWithEnumMethod() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.RECOGNIZE);
        
        assertEquals("RECOGNIZE", request.getMethodNameAsString());
        assertEquals(MrcpMethodName.RECOGNIZE.toString(), request.getMethodNameAsString());
    }

    @Test
    public void testGetMethodNameAsStringWithStringMethod() {
        TestMrcpRequest request = new TestMrcpRequest("TEST-METHOD");
        
        assertEquals("TEST-METHOD", request.getMethodNameAsString());
    }

    @Test
    public void testAppendStartLine() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.SET_PARAMS);
        request.setVersion("MRCP/2.0");
        request.setMessageLength(100);
        request.setRequestID(54321);
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = request.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("100"));
        assertTrue("Should contain method name", startLine.contains("SET-PARAMS"));
        assertTrue("Should contain request ID", startLine.contains("54321"));
        assertTrue("Should end with CRLF", startLine.endsWith(MrcpRequest.CRLF));
    }

    @Test
    public void testAppendStartLineWithStringMethod() {
        TestMrcpRequest request = new TestMrcpRequest("CUSTOM-METHOD");
        request.setVersion("MRCP/2.0");
        request.setMessageLength(200);
        request.setRequestID(98765);
        
        StringBuilder sb = new StringBuilder();
        request.appendStartLine(sb);
        String startLine = sb.toString();
        
        assertTrue("Should contain custom method", startLine.contains("CUSTOM-METHOD"));
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("200"));
        assertTrue("Should contain request ID", startLine.contains("98765"));
    }

    @Test
    public void testInheritanceFromMrcpMessage() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.STOP);
        assertTrue(request instanceof MrcpRequest);
        assertTrue(request instanceof org.mrcp4j.message.MrcpMessage);
    }

    @Test
    public void testMrcpMessagePropertiesInheritance() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.PAUSE);
        
        // Test inherited properties from MrcpMessage
        request.setVersion("MRCP/2.0");
        assertEquals("MRCP/2.0", request.getVersion());
        
        request.setMessageLength(150);
        assertEquals(150, request.getMessageLength());
        
        request.setRequestID(12345L);
        assertEquals(12345L, request.getRequestID());
    }

    @Test
    public void testAllMethodNames() {
        // Test that all standard method names work
        for (MrcpMethodName methodName : MrcpMethodName.values()) {
            TestMrcpRequest request = new TestMrcpRequest(methodName);
            assertEquals("Method name should be set correctly", methodName, request.getMethodName());
            assertEquals("Method name as string should match", methodName.toString(), request.getMethodNameAsString());
        }
    }

    @Test
    public void testStartLineFormatOrder() {
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.RECOGNIZE);
        request.setVersion("MRCP/2.0");
        request.setMessageLength(250);
        request.setRequestID(11111);
        
        StringBuilder sb = new StringBuilder();
        request.appendStartLine(sb);
        String startLine = sb.toString();
        
        // Check the order of elements in start line
        String[] parts = startLine.replace(MrcpRequest.CRLF, "").split(" ");
        assertEquals("Should have 4 parts", 4, parts.length);
        assertEquals("First part should be version", "MRCP/2.0", parts[0]);
        assertEquals("Second part should be message length", "250", parts[1]);
        assertEquals("Third part should be method name", "RECOGNIZE", parts[2]);
        assertEquals("Fourth part should be request ID", "11111", parts[3]);
    }

    @Test
    public void testAbstractNature() {
        // Verify that MrcpRequest is abstract by checking we can't instantiate it directly
        // This is tested implicitly by using a concrete test implementation
        TestMrcpRequest request = new TestMrcpRequest(MrcpMethodName.SPEAK);
        assertNotNull(request);
        assertTrue("Should be instance of MrcpRequest", request instanceof MrcpRequest);
    }

    /**
     * Concrete test implementation of MrcpRequest for testing purposes.
     */
    private static class TestMrcpRequest extends MrcpRequest {
        
        public TestMrcpRequest(MrcpMethodName methodName) {
            super(methodName);
        }
        
        public TestMrcpRequest(String methodName) {
            super(methodName);
        }
    }
}