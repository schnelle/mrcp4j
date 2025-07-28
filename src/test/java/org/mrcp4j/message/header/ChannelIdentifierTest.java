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
import org.mrcp4j.MrcpResourceType;
import static org.junit.Assert.*;

/**
 * Unit tests for ChannelIdentifier class.
 */
public class ChannelIdentifierTest {

    @Test
    public void testConstructorWithValidValues() {
        String channelID = "12345";
        MrcpResourceType resourceType = MrcpResourceType.SPEECHRECOG;
        
        ChannelIdentifier identifier = new ChannelIdentifier(channelID, resourceType);
        
        assertEquals(channelID, identifier.getChannelID());
        assertEquals(resourceType, identifier.getResourceType());
        assertEquals("12345@speechrecog", identifier.toString());
    }

    @Test
    public void testConstructorWithDifferentResourceTypes() {
        String channelID = "test-channel";
        
        for (MrcpResourceType resourceType : MrcpResourceType.values()) {
            ChannelIdentifier identifier = new ChannelIdentifier(channelID, resourceType);
            assertEquals(channelID, identifier.getChannelID());
            assertEquals(resourceType, identifier.getResourceType());
            assertEquals(channelID + "@" + resourceType.toString(), identifier.toString());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullChannelID() {
        new ChannelIdentifier(null, MrcpResourceType.SPEECHRECOG);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullResourceType() {
        new ChannelIdentifier("12345", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithBothNull() {
        new ChannelIdentifier(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyChannelID() {
        new ChannelIdentifier("", MrcpResourceType.SPEECHRECOG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithWhitespaceOnlyChannelID() {
        new ChannelIdentifier("   ", MrcpResourceType.SPEECHRECOG);
    }

    @Test
    public void testConstructorWithWhitespaceInChannelID() {
        // Whitespace should be trimmed in the internal value string, but the original channelID is stored
        ChannelIdentifier identifier = new ChannelIdentifier("  12345  ", MrcpResourceType.SPEECHRECOG);
        assertEquals("  12345  ", identifier.getChannelID()); // Original stored as-is
        assertEquals("12345@speechrecog", identifier.toString()); // But value string is trimmed
    }

    @Test
    public void testToString() {
        ChannelIdentifier identifier = new ChannelIdentifier("test123", MrcpResourceType.DTMFRECOG);
        assertEquals("test123@dtmfrecog", identifier.toString());
    }

    @Test
    public void testEquals() {
        ChannelIdentifier id1 = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        ChannelIdentifier id2 = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        ChannelIdentifier id3 = new ChannelIdentifier("67890", MrcpResourceType.SPEECHRECOG);
        ChannelIdentifier id4 = new ChannelIdentifier("12345", MrcpResourceType.DTMFRECOG);
        
        assertTrue("Same channel ID and resource type should be equal", id1.equals(id2));
        assertFalse("Different channel ID should not be equal", id1.equals(id3));
        assertFalse("Different resource type should not be equal", id1.equals(id4));
        assertTrue("Should be equal to itself", id1.equals(id1));
    }

    @Test
    public void testEqualsWithNull() {
        ChannelIdentifier identifier = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        assertFalse("Should not be equal to null", identifier.equals(null));
    }

    @Test
    public void testEqualsWithDifferentType() {
        ChannelIdentifier identifier = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        assertFalse("Should not be equal to string", identifier.equals("12345@speechrecog"));
        assertFalse("Should not be equal to other object", identifier.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        ChannelIdentifier id1 = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        ChannelIdentifier id2 = new ChannelIdentifier("12345", MrcpResourceType.SPEECHRECOG);
        ChannelIdentifier id3 = new ChannelIdentifier("67890", MrcpResourceType.SPEECHRECOG);
        
        assertEquals("Equal objects should have same hash code", id1.hashCode(), id2.hashCode());
        assertTrue("Hash code should be consistent", id1.hashCode() == id1.hashCode());
        
        // Different objects may or may not have different hash codes, but it's likely they will
        // This is not a requirement but helps with hash table performance
        assertNotEquals("Different objects likely have different hash codes", id1.hashCode(), id3.hashCode());
    }

    @Test
    public void testHashCodeConsistency() {
        ChannelIdentifier identifier = new ChannelIdentifier("test", MrcpResourceType.RECORDER);
        int hashCode1 = identifier.hashCode();
        int hashCode2 = identifier.hashCode();
        assertEquals("Hash code should be consistent", hashCode1, hashCode2);
        
        // Hash code should match the hash code of the toString() value
        assertEquals("Hash code should match value string hash code", identifier.toString().hashCode(), identifier.hashCode());
    }

    @Test
    public void testFactoryFromValueString() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        
        ChannelIdentifier identifier = (ChannelIdentifier) factory.fromValueString("12345@speechrecog");
        assertEquals("12345", identifier.getChannelID());
        assertEquals(MrcpResourceType.SPEECHRECOG, identifier.getResourceType());
        assertEquals("12345@speechrecog", identifier.toString());
    }

    @Test
    public void testFactoryFromValueStringWithWhitespace() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        
        ChannelIdentifier identifier = (ChannelIdentifier) factory.fromValueString("  test123  @  dtmfrecog  ");
        assertEquals("test123", identifier.getChannelID());
        assertEquals(MrcpResourceType.DTMFRECOG, identifier.getResourceType());
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringInvalidFormat() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        factory.fromValueString("invalid-channel-id");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringNoAtSymbol() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        factory.fromValueString("12345speechrecog");
    }

    @Test(expected = IllegalValueException.class)
    public void testFactoryFromValueStringMultipleAtSymbols() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        factory.fromValueString("12345@speech@recog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactoryFromValueStringInvalidResourceType() throws IllegalValueException {
        ChannelIdentifier.Factory factory = new ChannelIdentifier.Factory();
        factory.fromValueString("12345@invalidresource");
    }
}