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
 * Unit tests for ObjectWrapper class.
 */
public class ObjectWrapperTest {

    @Test
    public void testConstructorAndGetObject() {
        String testObject = "test string";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(testObject);
        
        assertEquals(testObject, wrapper.getObject());
    }

    @Test
    public void testConstructorWithNull() {
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(null);
        
        assertNull(wrapper.getObject());
    }

    @Test
    public void testWithDifferentTypes() {
        // Test with Integer
        Integer intValue = 42;
        ObjectWrapper<Integer> intWrapper = new ObjectWrapper<Integer>(intValue);
        assertEquals(intValue, intWrapper.getObject());
        
        // Test with Boolean
        Boolean boolValue = true;
        ObjectWrapper<Boolean> boolWrapper = new ObjectWrapper<Boolean>(boolValue);
        assertEquals(boolValue, boolWrapper.getObject());
        
        // Test with custom object
        Object customObject = new Object();
        ObjectWrapper<Object> objectWrapper = new ObjectWrapper<Object>(customObject);
        assertEquals(customObject, objectWrapper.getObject());
    }

    @Test
    public void testGenericTypePreservation() {
        String originalString = "test value";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(originalString);
        
        // The returned object should be the exact same reference
        assertSame("Should return same reference", originalString, wrapper.getObject());
    }

    @Test
    public void testWrappingNullValues() {
        ObjectWrapper<String> nullStringWrapper = new ObjectWrapper<String>(null);
        ObjectWrapper<Integer> nullIntWrapper = new ObjectWrapper<Integer>(null);
        ObjectWrapper<Object> nullObjectWrapper = new ObjectWrapper<Object>(null);
        
        assertNull("String wrapper should return null", nullStringWrapper.getObject());
        assertNull("Integer wrapper should return null", nullIntWrapper.getObject());
        assertNull("Object wrapper should return null", nullObjectWrapper.getObject());
    }

    @Test
    public void testMultipleCallsConsistency() {
        String testValue = "consistent value";
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(testValue);
        
        // Multiple calls should return the same value
        assertEquals("First call", testValue, wrapper.getObject());
        assertEquals("Second call", testValue, wrapper.getObject());
        assertEquals("Third call", testValue, wrapper.getObject());
        
        // And they should be the same reference
        assertSame("Should be same reference across calls", wrapper.getObject(), wrapper.getObject());
    }

    @Test
    public void testWrapperPurpose() {
        // This test demonstrates the primary purpose: wrapping null values
        // that might not be accepted by certain APIs
        
        String nullValue = null;
        ObjectWrapper<String> wrapper = new ObjectWrapper<String>(nullValue);
        
        // The wrapper itself is not null, even though it wraps a null
        assertNotNull("Wrapper itself should not be null", wrapper);
        assertNull("But wrapped value should be null", wrapper.getObject());
    }

    @Test
    public void testWithComplexObjects() {
        // Test with an array
        String[] array = {"item1", "item2", "item3"};
        ObjectWrapper<String[]> arrayWrapper = new ObjectWrapper<String[]>(array);
        assertArrayEquals(array, arrayWrapper.getObject());
        
        // Test with a StringBuilder
        StringBuilder sb = new StringBuilder("test");
        ObjectWrapper<StringBuilder> sbWrapper = new ObjectWrapper<StringBuilder>(sb);
        assertEquals(sb, sbWrapper.getObject());
        assertSame(sb, sbWrapper.getObject());
    }

    @Test
    public void testImmutabilityOfWrapper() {
        // Test that the wrapper doesn't change the wrapped object
        StringBuilder mutableObject = new StringBuilder("initial");
        ObjectWrapper<StringBuilder> wrapper = new ObjectWrapper<StringBuilder>(mutableObject);
        
        // Modify the original object
        mutableObject.append(" modified");
        
        // The wrapper should still return the same reference (which is now modified)
        assertSame("Should return same mutable reference", mutableObject, wrapper.getObject());
        assertEquals("Should reflect modifications", "initial modified", wrapper.getObject().toString());
    }
}