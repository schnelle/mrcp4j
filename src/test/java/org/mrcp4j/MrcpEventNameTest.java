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
 * Unit tests for MrcpEventName enum.
 */
public class MrcpEventNameTest {

    @Test
    public void testToString() {
        assertEquals("SPEECH-MARKER", MrcpEventName.SPEECH_MARKER.toString());
        assertEquals("SPEAK-COMPLETE", MrcpEventName.SPEAK_COMPLETE.toString());
        assertEquals("START-OF-INPUT", MrcpEventName.START_OF_INPUT.toString());
        assertEquals("RECOGNITION-COMPLETE", MrcpEventName.RECOGNITION_COMPLETE.toString());
        assertEquals("INTERPRETATION-COMPLETE", MrcpEventName.INTERPRETATION_COMPLETE.toString());
        assertEquals("RECORD-COMPLETE", MrcpEventName.RECORD_COMPLETE.toString());
        assertEquals("VERIFICATION-COMPLETE", MrcpEventName.VERIFICATION_COMPLETE.toString());
    }

    @Test
    public void testFromStringValidCases() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.fromString("SPEECH-MARKER"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.fromString("SPEAK-COMPLETE"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.fromString("START-OF-INPUT"));
        assertEquals(MrcpEventName.RECOGNITION_COMPLETE, MrcpEventName.fromString("RECOGNITION-COMPLETE"));
        assertEquals(MrcpEventName.INTERPRETATION_COMPLETE, MrcpEventName.fromString("INTERPRETATION-COMPLETE"));
        assertEquals(MrcpEventName.RECORD_COMPLETE, MrcpEventName.fromString("RECORD-COMPLETE"));
        assertEquals(MrcpEventName.VERIFICATION_COMPLETE, MrcpEventName.fromString("VERIFICATION-COMPLETE"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.fromString("speech-marker"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.fromString("Speak-Complete"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.fromString("start-of-input"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalidValue() {
        MrcpEventName.fromString("INVALID-EVENT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNullValue() {
        MrcpEventName.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmptyValue() {
        MrcpEventName.fromString("");
    }

    @Test
    public void testEnumValues() {
        MrcpEventName[] values = MrcpEventName.values();
        assertEquals(7, values.length);
        
        // Verify all expected values are present
        boolean foundSpeechMarker = false;
        boolean foundSpeakComplete = false;
        boolean foundStartOfInput = false;
        boolean foundRecognitionComplete = false;
        boolean foundInterpretationComplete = false;
        boolean foundRecordComplete = false;
        boolean foundVerificationComplete = false;
        
        for (MrcpEventName value : values) {
            switch (value) {
                case SPEECH_MARKER:
                    foundSpeechMarker = true;
                    break;
                case SPEAK_COMPLETE:
                    foundSpeakComplete = true;
                    break;
                case START_OF_INPUT:
                    foundStartOfInput = true;
                    break;
                case RECOGNITION_COMPLETE:
                    foundRecognitionComplete = true;
                    break;
                case INTERPRETATION_COMPLETE:
                    foundInterpretationComplete = true;
                    break;
                case RECORD_COMPLETE:
                    foundRecordComplete = true;
                    break;
                case VERIFICATION_COMPLETE:
                    foundVerificationComplete = true;
                    break;
            }
        }
        
        assertTrue("SPEECH_MARKER not found", foundSpeechMarker);
        assertTrue("SPEAK_COMPLETE not found", foundSpeakComplete);
        assertTrue("START_OF_INPUT not found", foundStartOfInput);
        assertTrue("RECOGNITION_COMPLETE not found", foundRecognitionComplete);
        assertTrue("INTERPRETATION_COMPLETE not found", foundInterpretationComplete);
        assertTrue("RECORD_COMPLETE not found", foundRecordComplete);
        assertTrue("VERIFICATION_COMPLETE not found", foundVerificationComplete);
    }

    @Test
    public void testValueOf() {
        assertEquals(MrcpEventName.SPEECH_MARKER, MrcpEventName.valueOf("SPEECH_MARKER"));
        assertEquals(MrcpEventName.SPEAK_COMPLETE, MrcpEventName.valueOf("SPEAK_COMPLETE"));
        assertEquals(MrcpEventName.START_OF_INPUT, MrcpEventName.valueOf("START_OF_INPUT"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        MrcpEventName.valueOf("INVALID_ENUM_VALUE");
    }
}