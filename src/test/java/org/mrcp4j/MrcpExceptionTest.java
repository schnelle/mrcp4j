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
package org.mrcp4j;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpException class.
 */
public class MrcpExceptionTest {

    @Test
    public void testDefaultConstructor() {
        MrcpException exception = new MrcpException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Test error message";
        MrcpException exception = new MrcpException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Test error message";
        RuntimeException cause = new RuntimeException("Root cause");
        MrcpException exception = new MrcpException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        RuntimeException cause = new RuntimeException("Root cause");
        MrcpException exception = new MrcpException(cause);
        assertEquals(cause, exception.getCause());
        // The message should include the cause's toString representation
        assertTrue(exception.getMessage().contains("RuntimeException"));
    }

    @Test
    public void testMessageConstructorWithNull() {
        MrcpException exception = new MrcpException((String) null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCauseConstructorWithNull() {
        MrcpException exception = new MrcpException((Throwable) null);
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNulls() {
        MrcpException exception = new MrcpException(null, null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testInheritanceFromException() {
        MrcpException exception = new MrcpException("test");
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    public void testStackTrace() {
        MrcpException exception = new MrcpException("Test message");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    public void testSerialization() {
        // Test that the exception can be used as a serializable object
        MrcpException exception = new MrcpException("Test message");
        assertNotNull(exception.toString());
        assertTrue(exception.toString().contains("MrcpException"));
        assertTrue(exception.toString().contains("Test message"));
    }
}