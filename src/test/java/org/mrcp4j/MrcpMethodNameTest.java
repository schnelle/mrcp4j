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
 * Unit tests for MrcpMethodName enum.
 */
public class MrcpMethodNameTest {

    @Test
    public void testToStringGenericMethods() {
        assertEquals("SET-PARAMS", MrcpMethodName.SET_PARAMS.toString());
        assertEquals("GET-PARAMS", MrcpMethodName.GET_PARAMS.toString());
    }

    @Test
    public void testToStringSynthesizerMethods() {
        assertEquals("SPEAK", MrcpMethodName.SPEAK.toString());
        assertEquals("STOP", MrcpMethodName.STOP.toString());
        assertEquals("PAUSE", MrcpMethodName.PAUSE.toString());
        assertEquals("RESUME", MrcpMethodName.RESUME.toString());
        assertEquals("BARGE-IN-OCCURRED", MrcpMethodName.BARGE_IN_OCCURRED.toString());
        assertEquals("CONTROL", MrcpMethodName.CONTROL.toString());
        assertEquals("DEFINE-LEXICON", MrcpMethodName.DEFINE_LEXICON.toString());
    }

    @Test
    public void testToStringRecogOnlyMethods() {
        assertEquals("DEFINE-GRAMMAR", MrcpMethodName.DEFINE_GRAMMAR.toString());
        assertEquals("RECOGNIZE", MrcpMethodName.RECOGNIZE.toString());
        assertEquals("INTERPRET", MrcpMethodName.INTERPRET.toString());
        assertEquals("GET-RESULT", MrcpMethodName.GET_RESULT.toString());
        assertEquals("START-INPUT-TIMERS", MrcpMethodName.START_INPUT_TIMERS.toString());
    }

    @Test
    public void testToStringEnrollmentMethods() {
        assertEquals("START-PHRASE-ENROLLMENT", MrcpMethodName.START_PHRASE_ENROLLMENT.toString());
        assertEquals("ENROLLMENT-ROLLBACK", MrcpMethodName.ENROLLMENT_ROLLBACK.toString());
        assertEquals("END-PHRASE-ENROLLMENT", MrcpMethodName.END_PHRASE_ENROLLMENT.toString());
        assertEquals("MODIFY-PHRASE", MrcpMethodName.MODIFY_PHRASE.toString());
        assertEquals("DELETE-PHRASE", MrcpMethodName.DELETE_PHRASE.toString());
    }

    @Test
    public void testToStringRecorderMethods() {
        assertEquals("RECORD", MrcpMethodName.RECORD.toString());
    }

    @Test
    public void testToStringVerificationMethods() {
        assertEquals("START-SESSION", MrcpMethodName.START_SESSION.toString());
        assertEquals("END-SESSION", MrcpMethodName.END_SESSION.toString());
        assertEquals("QUERY-VOICEPRINT", MrcpMethodName.QUERY_VOICEPRINT.toString());
        assertEquals("DELETE-VOICEPRINT", MrcpMethodName.DELETE_VOICEPRINT.toString());
        assertEquals("VERIFY", MrcpMethodName.VERIFY.toString());
        assertEquals("VERIFY-FROM-BUFFER", MrcpMethodName.VERIFY_FROM_BUFFER.toString());
        assertEquals("VERIFY-ROLLBACK", MrcpMethodName.VERIFY_ROLLBACK.toString());
        assertEquals("CLEAR-BUFFER", MrcpMethodName.CLEAR_BUFFER.toString());
        assertEquals("GET-INTERMEDIATE-RESULT", MrcpMethodName.GET_INTERMEDIATE_RESULT.toString());
    }

    @Test
    public void testFromStringValidCases() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("SET-PARAMS"));
        assertEquals(MrcpMethodName.GET_PARAMS, MrcpMethodName.fromString("GET-PARAMS"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("SPEAK"));
        assertEquals(MrcpMethodName.STOP, MrcpMethodName.fromString("STOP"));
        assertEquals(MrcpMethodName.RECOGNIZE, MrcpMethodName.fromString("RECOGNIZE"));
        assertEquals(MrcpMethodName.RECORD, MrcpMethodName.fromString("RECORD"));
        assertEquals(MrcpMethodName.VERIFY, MrcpMethodName.fromString("VERIFY"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.fromString("set-params"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.fromString("Speak"));
        assertEquals(MrcpMethodName.RECOGNIZE, MrcpMethodName.fromString("recognize"));
        assertEquals(MrcpMethodName.BARGE_IN_OCCURRED, MrcpMethodName.fromString("barge-in-occurred"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalidValue() {
        MrcpMethodName.fromString("INVALID-METHOD");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNullValue() {
        MrcpMethodName.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmptyValue() {
        MrcpMethodName.fromString("");
    }

    @Test
    public void testEnumValuesCount() {
        MrcpMethodName[] values = MrcpMethodName.values();
        assertEquals(29, values.length); // Total number of method names defined
    }

    @Test
    public void testValueOf() {
        assertEquals(MrcpMethodName.SET_PARAMS, MrcpMethodName.valueOf("SET_PARAMS"));
        assertEquals(MrcpMethodName.SPEAK, MrcpMethodName.valueOf("SPEAK"));
        assertEquals(MrcpMethodName.RECOGNIZE, MrcpMethodName.valueOf("RECOGNIZE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        MrcpMethodName.valueOf("INVALID_ENUM_VALUE");
    }

    @Test
    public void testAllMethodsHaveValidStrings() {
        for (MrcpMethodName method : MrcpMethodName.values()) {
            assertNotNull("Method name should not be null", method.toString());
            assertFalse("Method name should not be empty", method.toString().isEmpty());
            
            // Test round trip conversion
            assertEquals("Round trip conversion should work", method, MrcpMethodName.fromString(method.toString()));
        }
    }

    @Test
    public void testSpecificMethodCategories() {
        // Test that specific method categories exist
        assertNotNull(MrcpMethodName.SET_PARAMS);
        assertNotNull(MrcpMethodName.GET_PARAMS);
        
        // Synthesizer methods
        assertNotNull(MrcpMethodName.SPEAK);
        assertNotNull(MrcpMethodName.STOP);
        
        // Recognition methods
        assertNotNull(MrcpMethodName.RECOGNIZE);
        assertNotNull(MrcpMethodName.DEFINE_GRAMMAR);
        
        // Recorder methods
        assertNotNull(MrcpMethodName.RECORD);
        
        // Verification methods
        assertNotNull(MrcpMethodName.VERIFY);
        assertNotNull(MrcpMethodName.START_SESSION);
    }
}