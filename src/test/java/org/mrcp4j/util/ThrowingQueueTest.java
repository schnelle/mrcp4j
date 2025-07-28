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
package org.mrcp4j.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ThrowingQueue class.
 */
public class ThrowingQueueTest {

    @Test
    public void testPutAndTakeElement() throws InterruptedException, RuntimeException {
        ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        
        String testElement = "test element";
        queue.put(testElement);
        
        String result = queue.take();
        assertEquals(testElement, result);
    }

    @Test
    public void testPutAndTakeException() throws InterruptedException {
        ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        
        RuntimeException testException = new RuntimeException("Test exception");
        queue.put(testException);
        
        try {
            queue.take();
            fail("Should have thrown the exception");
        } catch (RuntimeException e) {
            assertEquals(testException, e);
            assertEquals("Test exception", e.getMessage());
        }
    }

    @Test
    public void testMixedElementsAndExceptions() throws InterruptedException, RuntimeException {
        ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        
        // Put an element, then an exception, then another element
        queue.put("first element");
        queue.put(new RuntimeException("test error"));
        queue.put("second element");
        
        // Take first element
        String first = queue.take();
        assertEquals("first element", first);
        
        // Take exception
        try {
            queue.take();
            fail("Should have thrown exception");
        } catch (RuntimeException e) {
            assertEquals("test error", e.getMessage());
        }
        
        // Take second element
        String second = queue.take();
        assertEquals("second element", second);
    }

    @Test
    public void testWithNullElement() throws InterruptedException, RuntimeException {
        ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        
        queue.put((String) null);
        String result = queue.take();
        assertNull(result);
    }

    @Test
    public void testWithDifferentTypes() throws InterruptedException, IllegalArgumentException {
        ThrowingQueue<Integer, IllegalArgumentException> queue = new ThrowingQueue<Integer, IllegalArgumentException>();
        
        queue.put(42);
        queue.put(new IllegalArgumentException("Invalid argument"));
        queue.put(100);
        
        Integer first = queue.take();
        assertEquals(Integer.valueOf(42), first);
        
        try {
            queue.take();
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid argument", e.getMessage());
        }
        
        Integer second = queue.take();
        assertEquals(Integer.valueOf(100), second);
    }

    @Test
    public void testGenericTypePreservation() throws InterruptedException, Exception {
        ThrowingQueue<StringBuilder, Exception> queue = new ThrowingQueue<StringBuilder, Exception>();
        
        StringBuilder original = new StringBuilder("test");
        queue.put(original);
        
        StringBuilder result = queue.take();
        assertSame("Should return the same reference", original, result);
        assertEquals("test", result.toString());
    }

    @Test
    public void testFIFOOrder() throws InterruptedException, RuntimeException {
        ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        
        // Put multiple elements
        queue.put("first");
        queue.put("second");
        queue.put("third");
        
        // Should come out in FIFO order
        assertEquals("first", queue.take());
        assertEquals("second", queue.take());
        assertEquals("third", queue.take());
    }

    @Test
    public void testExceptionInheritance() throws InterruptedException {
        ThrowingQueue<String, Exception> queue = new ThrowingQueue<String, Exception>();
        
        // Put a RuntimeException (which extends Exception)
        RuntimeException runtimeException = new RuntimeException("runtime error");
        queue.put(runtimeException);
        
        try {
            queue.take();
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertEquals(runtimeException, e);
            assertTrue("Should be a RuntimeException", e instanceof RuntimeException);
        }
    }

    @Test
    public void testConcurrency() throws InterruptedException, RuntimeException {
        final ThrowingQueue<String, RuntimeException> queue = new ThrowingQueue<String, RuntimeException>();
        final String[] results = new String[1];
        final Exception[] exceptions = new Exception[1];
        
        // Start a thread that will take from the queue
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    results[0] = queue.take();
                } catch (Exception e) {
                    exceptions[0] = e;
                }
            }
        });
        
        consumer.start();
        
        // Give the consumer thread a moment to start waiting
        Thread.sleep(100);
        
        // Put an element
        queue.put("async element");
        
        // Wait for consumer to finish
        consumer.join(1000);
        
        assertNull("Should not have thrown exception", exceptions[0]);
        assertEquals("async element", results[0]);
    }
}