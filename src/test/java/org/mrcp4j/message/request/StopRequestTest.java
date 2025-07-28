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
 * Unit tests for StopRequest class.
 */
public class StopRequestTest {

    @Test
    public void testConstructor() {
        StopRequest request = new StopRequest();
        
        assertEquals(MrcpMethodName.STOP, request.getMethodName());
        assertEquals("STOP", request.getMethodNameAsString());
    }

    @Test
    public void testInheritanceFromMrcpRequest() {
        StopRequest request = new StopRequest();
        
        assertTrue(request instanceof MrcpRequest);
        assertTrue(request instanceof org.mrcp4j.message.MrcpMessage);
    }

    @Test
    public void testMrcpMessagePropertiesInheritance() {
        StopRequest request = new StopRequest();
        
        // Test inherited properties from MrcpMessage
        request.setVersion("MRCP/2.0");
        assertEquals("MRCP/2.0", request.getVersion());
        
        request.setMessageLength(60);
        assertEquals(60, request.getMessageLength());
        
        request.setRequestID(77777L);
        assertEquals(77777L, request.getRequestID());
    }

    @Test
    public void testAppendStartLine() {
        StopRequest request = new StopRequest();
        request.setVersion("MRCP/2.0");
        request.setMessageLength(80);
        request.setRequestID(88888);
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = request.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("80"));
        assertTrue("Should contain method name", startLine.contains("STOP"));
        assertTrue("Should contain request ID", startLine.contains("88888"));
        assertTrue("Should end with CRLF", startLine.endsWith(org.mrcp4j.message.MrcpMessage.CRLF));
    }

    @Test
    public void testStartLineFormat() {
        StopRequest request = new StopRequest();
        request.setVersion("MRCP/2.0");
        request.setMessageLength(50);
        request.setRequestID(99999);
        
        StringBuilder sb = new StringBuilder();
        request.appendStartLine(sb);
        String startLine = sb.toString();
        
        // Check the exact format
        String expectedFormat = "MRCP/2.0 50 STOP 99999\r\n";
        assertEquals("Start line should match expected format", expectedFormat, startLine);
    }

    @Test
    public void testMethodNameConsistency() {
        StopRequest request = new StopRequest();
        
        // Verify the method name is consistently STOP
        assertEquals("Method name should be STOP", 
                     MrcpMethodName.STOP, request.getMethodName());
        assertEquals("Method name string should be STOP", 
                     "STOP", request.getMethodNameAsString());
        assertEquals("Method name should match enum toString", 
                     MrcpMethodName.STOP.toString(), request.getMethodNameAsString());
    }

    @Test
    public void testMultipleInstances() {
        // Test that multiple instances work independently
        StopRequest request1 = new StopRequest();
        StopRequest request2 = new StopRequest();
        
        request1.setRequestID(1001);
        request2.setRequestID(1002);
        
        assertEquals("Request 1 should have ID 1001", 1001, request1.getRequestID());
        assertEquals("Request 2 should have ID 1002", 1002, request2.getRequestID());
        
        // Both should have the same method name
        assertEquals("Both should have same method name", 
                     request1.getMethodName(), request2.getMethodName());
    }

    @Test
    public void testDefaultValues() {
        StopRequest request = new StopRequest();
        
        // Test default values from parent classes
        assertEquals("Default request ID should be -1", -1, request.getRequestID());
        assertEquals("Default message length should be -1", -1, request.getMessageLength());
        assertNull("Default version should be null", request.getVersion());
        
        // But method name should be set
        assertNotNull("Method name should not be null", request.getMethodName());
        assertEquals("Method name should be STOP", 
                     MrcpMethodName.STOP, request.getMethodName());
    }

    @Test
    public void testStopRequestPurpose() {
        // Test that this request type represents the STOP method correctly
        StopRequest request = new StopRequest();
        
        assertEquals("Should represent STOP method", MrcpMethodName.STOP, request.getMethodName());
        
        // STOP is a generic method used across multiple resource types
        assertTrue("STOP should be a valid method name", 
                   MrcpMethodName.STOP.toString().equals("STOP"));
    }

    @Test
    public void testClassStructure() {
        StopRequest request = new StopRequest();
        
        // Class should be in the correct package
        assertEquals("org.mrcp4j.message.request.StopRequest", request.getClass().getName());
        
        // Should be a concrete class
        assertFalse("Should not be abstract", java.lang.reflect.Modifier.isAbstract(request.getClass().getModifiers()));
        assertFalse("Should not be final", java.lang.reflect.Modifier.isFinal(request.getClass().getModifiers()));
        assertTrue("Should be public", java.lang.reflect.Modifier.isPublic(request.getClass().getModifiers()));
    }

    @Test
    public void testRequestTypeEquality() {
        StopRequest request1 = new StopRequest();
        StopRequest request2 = new StopRequest();
        
        // Different instances should have the same method name
        assertEquals("Different instances should have same method name", 
                     request1.getMethodName(), request2.getMethodName());
        assertEquals("Different instances should have same method name string", 
                     request1.getMethodNameAsString(), request2.getMethodNameAsString());
    }
}