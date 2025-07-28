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
package org.mrcp4j.client;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpFactory class.
 */
public class MrcpFactoryTest {

    @Test
    public void testNewInstance() {
        MrcpFactory factory = MrcpFactory.newInstance();
        assertNotNull("Factory should not be null", factory);
    }

    @Test
    public void testNewInstanceCreatesNewInstances() {
        MrcpFactory factory1 = MrcpFactory.newInstance();
        MrcpFactory factory2 = MrcpFactory.newInstance();
        
        assertNotNull("First factory should not be null", factory1);
        assertNotNull("Second factory should not be null", factory2);
        assertNotSame("Should create different instances", factory1, factory2);
    }

    @Test
    public void testCreateProvider() {
        MrcpFactory factory = MrcpFactory.newInstance();
        MrcpProvider provider = factory.createProvider();
        
        assertNotNull("Provider should not be null", provider);
        assertTrue("Should be instance of MrcpProvider", provider instanceof MrcpProvider);
    }

    @Test
    public void testCreateProviderCreatesNewInstances() {
        MrcpFactory factory = MrcpFactory.newInstance();
        MrcpProvider provider1 = factory.createProvider();
        MrcpProvider provider2 = factory.createProvider();
        
        assertNotNull("First provider should not be null", provider1);
        assertNotNull("Second provider should not be null", provider2);
        assertNotSame("Should create different provider instances", provider1, provider2);
    }

    @Test
    public void testMultipleFactoriesCreateProviders() {
        MrcpFactory factory1 = MrcpFactory.newInstance();
        MrcpFactory factory2 = MrcpFactory.newInstance();
        
        MrcpProvider provider1 = factory1.createProvider();
        MrcpProvider provider2 = factory2.createProvider();
        
        assertNotNull("Provider from factory1 should not be null", provider1);
        assertNotNull("Provider from factory2 should not be null", provider2);
        assertNotSame("Different factories should create different providers", provider1, provider2);
    }

    @Test
    public void testFactoryPattern() {
        // Test the factory pattern implementation
        MrcpFactory factory = MrcpFactory.newInstance();
        
        // Should not be able to instantiate directly (constructor is private)
        // This is verified by the fact that we must use newInstance()
        assertNotNull("Factory should be created via newInstance", factory);
        
        // Factory should be able to create providers
        MrcpProvider provider = factory.createProvider();
        assertNotNull("Factory should create providers", provider);
    }

    @Test
    public void testSingletonPattern() {
        // Note: This is NOT a singleton pattern, each newInstance() creates a new factory
        // This test verifies that behavior
        MrcpFactory factory1 = MrcpFactory.newInstance();
        MrcpFactory factory2 = MrcpFactory.newInstance();
        
        assertNotSame("Each newInstance() should create a new factory", factory1, factory2);
    }

    @Test
    public void testFactoryClassStructure() {
        MrcpFactory factory = MrcpFactory.newInstance();
        
        // Class should be in the correct package
        assertEquals("org.mrcp4j.client.MrcpFactory", factory.getClass().getName());
        
        // Should be a concrete class
        assertFalse("Should not be abstract", java.lang.reflect.Modifier.isAbstract(factory.getClass().getModifiers()));
        assertFalse("Should not be final", java.lang.reflect.Modifier.isFinal(factory.getClass().getModifiers()));
    }

    @Test
    public void testFactoryConsistency() {
        // Test that the same factory instance consistently creates providers
        MrcpFactory factory = MrcpFactory.newInstance();
        
        for (int i = 0; i < 5; i++) {
            MrcpProvider provider = factory.createProvider();
            assertNotNull("Provider " + i + " should not be null", provider);
            assertTrue("Provider " + i + " should be MrcpProvider instance", provider instanceof MrcpProvider);
        }
    }

    @Test
    public void testProviderTypeConsistency() {
        MrcpFactory factory = MrcpFactory.newInstance();
        MrcpProvider provider = factory.createProvider();
        
        // Verify the provider type
        assertEquals("Provider should be exactly MrcpProvider type", MrcpProvider.class, provider.getClass());
    }

    @Test
    public void testFactoryMethodsNotNull() {
        MrcpFactory factory = MrcpFactory.newInstance();
        
        // Test that factory methods don't return null
        assertNotNull("newInstance should not return null", factory);
        assertNotNull("createProvider should not return null", factory.createProvider());
    }

    @Test
    public void testFactoryDocumentationPattern() {
        // Test that the factory follows the documented pattern for potential future multiple implementations
        MrcpFactory factory = MrcpFactory.newInstance();
        MrcpProvider provider = factory.createProvider();
        
        // Currently returns MrcpProvider, but architecture supports multiple implementations
        assertNotNull("Factory should create provider instances", provider);
        assertTrue("Provider should implement the provider contract", provider instanceof MrcpProvider);
    }

    @Test
    public void testStaticFactoryMethod() {
        // Test the static factory method behavior
        assertNotNull("Static newInstance should work", MrcpFactory.newInstance());
        
        // Multiple calls should work
        MrcpFactory factory1 = MrcpFactory.newInstance();
        MrcpFactory factory2 = MrcpFactory.newInstance();
        MrcpFactory factory3 = MrcpFactory.newInstance();
        
        assertNotNull(factory1);
        assertNotNull(factory2);
        assertNotNull(factory3);
    }
}