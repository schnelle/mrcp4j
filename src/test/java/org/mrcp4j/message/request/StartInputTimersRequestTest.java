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
 * Unit tests for StartInputTimersRequest class.
 */
public class StartInputTimersRequestTest {

    @Test
    public void testConstructor() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        
        assertEquals(MrcpMethodName.START_INPUT_TIMERS, request.getMethodName());
        assertEquals("START-INPUT-TIMERS", request.getMethodNameAsString());
    }

    @Test
    public void testInheritanceFromMrcpRequest() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        
        assertTrue(request instanceof MrcpRequest);
        assertTrue(request instanceof org.mrcp4j.message.MrcpMessage);
    }

    @Test
    public void testMrcpMessagePropertiesInheritance() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        
        // Test inherited properties from MrcpMessage
        request.setVersion("MRCP/2.0");
        assertEquals("MRCP/2.0", request.getVersion());
        
        request.setMessageLength(80);
        assertEquals(80, request.getMessageLength());
        
        request.setRequestID(99999L);
        assertEquals(99999L, request.getRequestID());
    }

    @Test
    public void testAppendStartLine() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        request.setVersion("MRCP/2.0");
        request.setMessageLength(120);
        request.setRequestID(55555);
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = request.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("120"));
        assertTrue("Should contain method name", startLine.contains("START-INPUT-TIMERS"));
        assertTrue("Should contain request ID", startLine.contains("55555"));
        assertTrue("Should end with CRLF", startLine.endsWith(org.mrcp4j.message.MrcpMessage.CRLF));
    }

    @Test
    public void testStartLineFormat() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        request.setVersion("MRCP/2.0");
        request.setMessageLength(100);
        request.setRequestID(12345);
        
        StringBuilder sb = new StringBuilder();
        request.appendStartLine(sb);
        String startLine = sb.toString();
        
        // Check the exact format
        String expectedFormat = "MRCP/2.0 100 START-INPUT-TIMERS 12345\r\n";
        assertEquals("Start line should match expected format", expectedFormat, startLine);
    }

    @Test
    public void testMethodNameConsistency() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        
        // Verify the method name is consistently START_INPUT_TIMERS
        assertEquals("Method name should be START_INPUT_TIMERS", 
                     MrcpMethodName.START_INPUT_TIMERS, request.getMethodName());
        assertEquals("Method name string should be START-INPUT-TIMERS", 
                     "START-INPUT-TIMERS", request.getMethodNameAsString());
        assertEquals("Method name should match enum toString", 
                     MrcpMethodName.START_INPUT_TIMERS.toString(), request.getMethodNameAsString());
    }

    @Test
    public void testMultipleInstances() {
        // Test that multiple instances work independently
        StartInputTimersRequest request1 = new StartInputTimersRequest();
        StartInputTimersRequest request2 = new StartInputTimersRequest();
        
        request1.setRequestID(1111);
        request2.setRequestID(2222);
        
        assertEquals("Request 1 should have ID 1111", 1111, request1.getRequestID());
        assertEquals("Request 2 should have ID 2222", 2222, request2.getRequestID());
        
        // Both should have the same method name
        assertEquals("Both should have same method name", 
                     request1.getMethodName(), request2.getMethodName());
    }

    @Test
    public void testDefaultValues() {
        StartInputTimersRequest request = new StartInputTimersRequest();
        
        // Test default values from parent classes
        assertEquals("Default request ID should be -1", -1, request.getRequestID());
        assertEquals("Default message length should be -1", -1, request.getMessageLength());
        assertNull("Default version should be null", request.getVersion());
        
        // But method name should be set
        assertNotNull("Method name should not be null", request.getMethodName());
        assertEquals("Method name should be START_INPUT_TIMERS", 
                     MrcpMethodName.START_INPUT_TIMERS, request.getMethodName());
    }
}