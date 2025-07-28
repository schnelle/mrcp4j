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
package org.mrcp4j.message;

import org.junit.Test;
import org.mrcp4j.MrcpEventName;
import org.mrcp4j.MrcpRequestState;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpEvent class.
 */
public class MrcpEventTest {

    @Test
    public void testEventNameGetterSetter() {
        MrcpEvent event = new MrcpEvent();
        assertNull(event.getEventName()); // Default value
        
        event.setEventName(MrcpEventName.SPEECH_MARKER);
        assertEquals(MrcpEventName.SPEECH_MARKER, event.getEventName());
        
        event.setEventName(MrcpEventName.RECOGNITION_COMPLETE);
        assertEquals(MrcpEventName.RECOGNITION_COMPLETE, event.getEventName());
    }

    @Test
    public void testEventNameSetNull() {
        MrcpEvent event = new MrcpEvent();
        event.setEventName(MrcpEventName.SPEAK_COMPLETE);
        assertNotNull(event.getEventName());
        
        event.setEventName(null);
        assertNull(event.getEventName());
    }

    @Test
    public void testAppendStartLine() {
        MrcpEvent event = new MrcpEvent();
        event.setVersion("MRCP/2.0");
        event.setMessageLength(120);
        event.setRequestID(98765);
        event.setEventName(MrcpEventName.RECOGNITION_COMPLETE);
        event.setRequestState(MrcpRequestState.COMPLETE);
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = event.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("120"));
        assertTrue("Should contain event name", startLine.contains("RECOGNITION-COMPLETE"));
        assertTrue("Should contain request ID", startLine.contains("98765"));
        assertTrue("Should contain request state", startLine.contains("COMPLETE"));
        assertTrue("Should end with CRLF", startLine.endsWith(MrcpMessage.CRLF));
    }

    @Test
    public void testAppendStartLineWithNullEventName() {
        MrcpEvent event = new MrcpEvent();
        event.setVersion("MRCP/2.0");
        event.setMessageLength(100);
        event.setRequestID(12345);
        event.setRequestState(MrcpRequestState.IN_PROGRESS);
        // eventName is null
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = event.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue("Should contain version", startLine.contains("MRCP/2.0"));
        assertTrue("Should contain message length", startLine.contains("100"));
        assertTrue("Should contain request ID", startLine.contains("12345"));
        assertTrue("Should contain request state", startLine.contains("IN-PROGRESS"));
        assertTrue("Should contain null", startLine.contains("null"));
    }

    @Test
    public void testInheritanceFromMrcpServerMessage() {
        MrcpEvent event = new MrcpEvent();
        assertTrue(event instanceof MrcpServerMessage);
        assertTrue(event instanceof MrcpMessage);
    }

    @Test
    public void testRequestStateInheritance() {
        MrcpEvent event = new MrcpEvent();
        
        // Test default state from parent class
        assertEquals(MrcpRequestState.PENDING, event.getRequestState());
        
        // Test setting different states
        event.setRequestState(MrcpRequestState.IN_PROGRESS);
        assertEquals(MrcpRequestState.IN_PROGRESS, event.getRequestState());
        
        event.setRequestState(MrcpRequestState.COMPLETE);
        assertEquals(MrcpRequestState.COMPLETE, event.getRequestState());
    }

    @Test
    public void testAllEventNames() {
        MrcpEvent event = new MrcpEvent();
        
        // Test setting all available event names
        for (MrcpEventName eventName : MrcpEventName.values()) {
            event.setEventName(eventName);
            assertEquals("Event name should be set correctly", eventName, event.getEventName());
        }
    }

    @Test
    public void testStartLineFormatOrder() {
        MrcpEvent event = new MrcpEvent();
        event.setVersion("MRCP/2.0");
        event.setMessageLength(150);
        event.setRequestID(54321);
        event.setEventName(MrcpEventName.START_OF_INPUT);
        event.setRequestState(MrcpRequestState.COMPLETE);
        
        StringBuilder sb = new StringBuilder();
        event.appendStartLine(sb);
        String startLine = sb.toString();
        
        // Check the order of elements in start line
        String[] parts = startLine.replace(MrcpMessage.CRLF, "").split(" ");
        assertEquals("Should have 5 parts", 5, parts.length);
        assertEquals("First part should be version", "MRCP/2.0", parts[0]);
        assertEquals("Second part should be message length", "150", parts[1]);
        assertEquals("Third part should be event name", "START-OF-INPUT", parts[2]);
        assertEquals("Fourth part should be request ID", "54321", parts[3]);
        assertEquals("Fifth part should be request state", "COMPLETE", parts[4]);
    }
}