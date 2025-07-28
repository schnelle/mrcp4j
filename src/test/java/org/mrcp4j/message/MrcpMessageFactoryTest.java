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
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpMessageFactory class.
 */
public class MrcpMessageFactoryTest {

    @Test
    public void testClassExists() {
        // Test that the class can be instantiated
        MrcpMessageFactory factory = new MrcpMessageFactory();
        assertNotNull(factory);
    }

    @Test
    public void testInstanceCreation() {
        // Test multiple instances can be created
        MrcpMessageFactory factory1 = new MrcpMessageFactory();
        MrcpMessageFactory factory2 = new MrcpMessageFactory();
        
        assertNotNull(factory1);
        assertNotNull(factory2);
        assertNotSame("Should be different instances", factory1, factory2);
    }

    @Test
    public void testClassStructure() {
        // Test that the class is properly structured
        MrcpMessageFactory factory = new MrcpMessageFactory();
        
        // Class should be in the correct package
        assertEquals("org.mrcp4j.message.MrcpMessageFactory", factory.getClass().getName());
        
        // Should be a concrete class
        assertFalse("Should not be abstract", java.lang.reflect.Modifier.isAbstract(factory.getClass().getModifiers()));
        assertFalse("Should not be final", java.lang.reflect.Modifier.isFinal(factory.getClass().getModifiers()));
    }

    @Test
    public void testToString() {
        MrcpMessageFactory factory = new MrcpMessageFactory();
        String toString = factory.toString();
        
        assertNotNull("toString should not return null", toString);
        assertTrue("toString should contain class name", toString.contains("MrcpMessageFactory"));
    }

    @Test
    public void testEquals() {
        MrcpMessageFactory factory1 = new MrcpMessageFactory();
        MrcpMessageFactory factory2 = new MrcpMessageFactory();
        
        // Test reflexivity
        assertTrue("Should be equal to itself", factory1.equals(factory1));
        
        // Test with null
        assertFalse("Should not be equal to null", factory1.equals(null));
        
        // Test with different type
        assertFalse("Should not be equal to string", factory1.equals("not a factory"));
    }

    @Test
    public void testHashCode() {
        MrcpMessageFactory factory = new MrcpMessageFactory();
        int hashCode1 = factory.hashCode();
        int hashCode2 = factory.hashCode();
        
        // Hash code should be consistent
        assertEquals("Hash code should be consistent", hashCode1, hashCode2);
    }

    @Test
    public void testEmptyClassFunctionality() {
        // Since this is currently an empty class, test that it behaves as expected
        MrcpMessageFactory factory = new MrcpMessageFactory();
        
        // Should be instantiable without errors
        assertNotNull(factory);
        
        // Should have the standard Object methods
        assertNotNull(factory.toString());
        assertTrue(factory.equals(factory));
        assertTrue(factory.hashCode() == factory.hashCode());
    }

    @Test
    public void testClassInheritance() {
        MrcpMessageFactory factory = new MrcpMessageFactory();
        
        // Should inherit from Object
        assertTrue("Should be instance of Object", factory instanceof Object);
        
        // Should be exactly MrcpMessageFactory type
        assertEquals("Should be exactly MrcpMessageFactory", MrcpMessageFactory.class, factory.getClass());
    }
}