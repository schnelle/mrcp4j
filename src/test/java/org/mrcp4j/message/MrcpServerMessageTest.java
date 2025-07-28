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
import org.mrcp4j.MrcpRequestState;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpServerMessage class.
 */
public class MrcpServerMessageTest {

    @Test
    public void testRequestStateGetterSetter() {
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        
        // Test default value
        assertEquals(MrcpRequestState.PENDING, message.getRequestState());
        
        // Test setting different states
        message.setRequestState(MrcpRequestState.IN_PROGRESS);
        assertEquals(MrcpRequestState.IN_PROGRESS, message.getRequestState());
        
        message.setRequestState(MrcpRequestState.COMPLETE);
        assertEquals(MrcpRequestState.COMPLETE, message.getRequestState());
        
        message.setRequestState(MrcpRequestState.PENDING);
        assertEquals(MrcpRequestState.PENDING, message.getRequestState());
    }

    @Test
    public void testRequestStateSetNull() {
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        message.setRequestState(MrcpRequestState.COMPLETE);
        assertNotNull(message.getRequestState());
        
        message.setRequestState(null);
        assertNull(message.getRequestState());
    }

    @Test
    public void testInheritanceFromMrcpMessage() {
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        assertTrue(message instanceof MrcpMessage);
    }

    @Test
    public void testMrcpMessagePropertiesInheritance() {
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        
        // Test inherited properties from MrcpMessage
        message.setVersion("MRCP/2.0");
        assertEquals("MRCP/2.0", message.getVersion());
        
        message.setMessageLength(200);
        assertEquals(200, message.getMessageLength());
        
        message.setRequestID(12345L);
        assertEquals(12345L, message.getRequestID());
    }

    @Test
    public void testAllRequestStates() {
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        
        // Test setting all available request states
        for (MrcpRequestState state : MrcpRequestState.values()) {
            message.setRequestState(state);
            assertEquals("Request state should be set correctly", state, message.getRequestState());
        }
    }

    @Test
    public void testAbstractNature() {
        // Verify that MrcpServerMessage is abstract by checking we can't instantiate it directly
        // This is tested implicitly by using a concrete test implementation
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        assertNotNull(message);
        assertTrue("Should be instance of MrcpServerMessage", message instanceof MrcpServerMessage);
    }

    @Test
    public void testDefaultRequestState() {
        // Verify that the default request state is PENDING
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        assertEquals("Default request state should be PENDING", MrcpRequestState.PENDING, message.getRequestState());
    }

    @Test
    public void testStartLineMethodExists() {
        // Verify that the abstract appendStartLine method is properly implemented in subclass
        TestMrcpServerMessage message = new TestMrcpServerMessage();
        StringBuilder sb = new StringBuilder();
        StringBuilder result = message.appendStartLine(sb);
        assertNotNull("appendStartLine should return non-null result", result);
        assertTrue("appendStartLine should return StringBuilder with content", result.length() > 0);
    }

    /**
     * Concrete test implementation of MrcpServerMessage for testing purposes.
     */
    private static class TestMrcpServerMessage extends MrcpServerMessage {
        @Override
        protected StringBuilder appendStartLine(StringBuilder sb) {
            sb.append("TEST-MESSAGE");
            sb.append(' ').append(getVersion() != null ? getVersion() : "MRCP/2.0");
            sb.append(' ').append(getMessageLength());
            sb.append(' ').append(getRequestID());
            sb.append(' ').append(getRequestState());
            sb.append(CRLF);
            return sb;
        }
    }
}