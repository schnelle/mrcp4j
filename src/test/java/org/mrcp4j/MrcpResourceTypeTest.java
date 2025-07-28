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
 * Unit tests for MrcpResourceType enum.
 */
public class MrcpResourceTypeTest {

    @Test
    public void testToString() {
        assertEquals("speechrecog", MrcpResourceType.SPEECHRECOG.toString());
        assertEquals("dtmfrecog", MrcpResourceType.DTMFRECOG.toString());
        assertEquals("speechsynth", MrcpResourceType.SPEECHSYNTH.toString());
        assertEquals("basicsynth", MrcpResourceType.BASICSYNTH.toString());
        assertEquals("speakverify", MrcpResourceType.SPEAKVERIFY.toString());
        assertEquals("recorder", MrcpResourceType.RECORDER.toString());
    }

    @Test
    public void testFromStringValidCases() {
        assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromString("speechrecog"));
        assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromString("dtmfrecog"));
        assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromString("speechsynth"));
        assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromString("basicsynth"));
        assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromString("speakverify"));
        assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromString("recorder"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromString("SPEECHRECOG"));
        assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromString("DtmfRecog"));
        assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromString("SpeechSynth"));
        assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromString("RECORDER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalidValue() {
        MrcpResourceType.fromString("invalid-resource");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNullValue() {
        MrcpResourceType.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmptyValue() {
        MrcpResourceType.fromString("");
    }

    @Test
    public void testFromChannelIDValidCases() {
        assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromChannelID("12345@speechrecog"));
        assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.fromChannelID("67890@dtmfrecog"));
        assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.fromChannelID("abcdef@speechsynth"));
        assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.fromChannelID("xyz123@basicsynth"));
        assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.fromChannelID("test@speakverify"));
        assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromChannelID("channel@recorder"));
    }

    @Test
    public void testFromChannelIDCaseInsensitive() {
        assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.fromChannelID("12345@SPEECHRECOG"));
        assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.fromChannelID("test@Recorder"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDInvalidFormat() {
        MrcpResourceType.fromChannelID("invalid-channel-id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDNoAtSymbol() {
        MrcpResourceType.fromChannelID("12345speechrecog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDMultipleAtSymbols() {
        MrcpResourceType.fromChannelID("12345@speech@recog");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDEmptyChannelID() {
        MrcpResourceType.fromChannelID("");
    }

    @Test(expected = NullPointerException.class)
    public void testFromChannelIDNullChannelID() {
        MrcpResourceType.fromChannelID(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromChannelIDInvalidResourceType() {
        MrcpResourceType.fromChannelID("12345@invalidresource");
    }

    @Test
    public void testEnumValues() {
        MrcpResourceType[] values = MrcpResourceType.values();
        assertEquals(6, values.length);
        
        // Verify all expected values are present
        boolean foundSpeechrecog = false;
        boolean foundDtmfrecog = false;
        boolean foundSpeechsynth = false;
        boolean foundBasicsynth = false;
        boolean foundSpeakverify = false;
        boolean foundRecorder = false;
        
        for (MrcpResourceType value : values) {
            switch (value) {
                case SPEECHRECOG:
                    foundSpeechrecog = true;
                    break;
                case DTMFRECOG:
                    foundDtmfrecog = true;
                    break;
                case SPEECHSYNTH:
                    foundSpeechsynth = true;
                    break;
                case BASICSYNTH:
                    foundBasicsynth = true;
                    break;
                case SPEAKVERIFY:
                    foundSpeakverify = true;
                    break;
                case RECORDER:
                    foundRecorder = true;
                    break;
            }
        }
        
        assertTrue("SPEECHRECOG not found", foundSpeechrecog);
        assertTrue("DTMFRECOG not found", foundDtmfrecog);
        assertTrue("SPEECHSYNTH not found", foundSpeechsynth);
        assertTrue("BASICSYNTH not found", foundBasicsynth);
        assertTrue("SPEAKVERIFY not found", foundSpeakverify);
        assertTrue("RECORDER not found", foundRecorder);
    }

    @Test
    public void testValueOf() {
        assertEquals(MrcpResourceType.SPEECHRECOG, MrcpResourceType.valueOf("SPEECHRECOG"));
        assertEquals(MrcpResourceType.DTMFRECOG, MrcpResourceType.valueOf("DTMFRECOG"));
        assertEquals(MrcpResourceType.SPEECHSYNTH, MrcpResourceType.valueOf("SPEECHSYNTH"));
        assertEquals(MrcpResourceType.BASICSYNTH, MrcpResourceType.valueOf("BASICSYNTH"));
        assertEquals(MrcpResourceType.SPEAKVERIFY, MrcpResourceType.valueOf("SPEAKVERIFY"));
        assertEquals(MrcpResourceType.RECORDER, MrcpResourceType.valueOf("RECORDER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        MrcpResourceType.valueOf("INVALID_ENUM_VALUE");
    }

    @Test
    public void testAllResourceTypesHaveValidStrings() {
        for (MrcpResourceType resourceType : MrcpResourceType.values()) {
            assertNotNull("Resource type name should not be null", resourceType.toString());
            assertFalse("Resource type name should not be empty", resourceType.toString().isEmpty());
            
            // Test round trip conversion
            assertEquals("Round trip conversion should work", resourceType, MrcpResourceType.fromString(resourceType.toString()));
        }
    }

    @Test
    public void testChannelIDRoundTrip() {
        String[] testChannelIDs = {
            "12345@speechrecog",
            "67890@dtmfrecog", 
            "abcdef@speechsynth",
            "xyz123@basicsynth",
            "test@speakverify",
            "channel@recorder"
        };
        
        for (String channelID : testChannelIDs) {
            MrcpResourceType resourceType = MrcpResourceType.fromChannelID(channelID);
            assertNotNull("Resource type should not be null", resourceType);
            
            // Verify the resource type matches what we expect
            String expectedResourceType = channelID.split("@")[1];
            assertEquals("Resource type should match channel ID", expectedResourceType, resourceType.toString());
        }
    }
}