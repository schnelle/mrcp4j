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
package org.mrcp4j.message.header;

import org.junit.Test;
import org.mrcp4j.MrcpException;
import static org.junit.Assert.*;

/**
 * Unit tests for IllegalValueException class.
 */
public class IllegalValueExceptionTest {

    @Test
    public void testDefaultConstructor() {
        IllegalValueException exception = new IllegalValueException();
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageConstructor() {
        String message = "Invalid header value";
        IllegalValueException exception = new IllegalValueException(message);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        String message = "Invalid header value";
        RuntimeException cause = new RuntimeException("Root cause");
        IllegalValueException exception = new IllegalValueException(message, cause);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testCauseConstructor() {
        RuntimeException cause = new RuntimeException("Root cause");
        IllegalValueException exception = new IllegalValueException(cause);
        assertEquals(cause, exception.getCause());
        // The message should include the cause's toString representation
        assertTrue(exception.getMessage().contains("RuntimeException"));
    }

    @Test
    public void testMessageConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((String) null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testCauseConstructorWithNull() {
        IllegalValueException exception = new IllegalValueException((Throwable) null);
        assertNull(exception.getCause());
    }

    @Test
    public void testMessageAndCauseConstructorWithNulls() {
        IllegalValueException exception = new IllegalValueException(null, null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testInheritanceFromMrcpException() {
        IllegalValueException exception = new IllegalValueException("test");
        assertTrue(exception instanceof MrcpException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    public void testStackTrace() {
        IllegalValueException exception = new IllegalValueException("Test message");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    public void testSerialization() {
        // Test that the exception can be used as a serializable object
        IllegalValueException exception = new IllegalValueException("Test message");
        assertNotNull(exception.toString());
        assertTrue(exception.toString().contains("IllegalValueException"));
        assertTrue(exception.toString().contains("Test message"));
    }

    @Test
    public void testThrowAndCatch() {
        String testMessage = "Invalid MRCP header value";
        
        try {
            throw new IllegalValueException(testMessage);
        } catch (IllegalValueException e) {
            assertEquals(testMessage, e.getMessage());
            assertTrue(e instanceof MrcpException);
        }
    }

    @Test
    public void testChainedExceptions() {
        RuntimeException originalCause = new RuntimeException("Original error");
        IllegalArgumentException intermediateCause = new IllegalArgumentException("Intermediate error", originalCause);
        IllegalValueException finalException = new IllegalValueException("Final error", intermediateCause);
        
        assertEquals("Final error", finalException.getMessage());
        assertEquals(intermediateCause, finalException.getCause());
        assertEquals(originalCause, finalException.getCause().getCause());
    }
}