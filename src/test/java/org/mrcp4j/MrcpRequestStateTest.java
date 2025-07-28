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
 * Unit tests for MrcpRequestState enum.
 */
public class MrcpRequestStateTest {

    @Test
    public void testToString() {
        assertEquals("PENDING", MrcpRequestState.PENDING.toString());
        assertEquals("IN-PROGRESS", MrcpRequestState.IN_PROGRESS.toString());
        assertEquals("COMPLETE", MrcpRequestState.COMPLETE.toString());
    }

    @Test
    public void testFromStringValidCases() {
        assertEquals(MrcpRequestState.PENDING, MrcpRequestState.fromString("PENDING"));
        assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("IN-PROGRESS"));
        assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.fromString("COMPLETE"));
    }

    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(MrcpRequestState.PENDING, MrcpRequestState.fromString("pending"));
        assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("in-progress"));
        assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.fromString("complete"));
        assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.fromString("In-Progress"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringInvalidValue() {
        MrcpRequestState.fromString("INVALID-STATE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringNullValue() {
        MrcpRequestState.fromString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromStringEmptyValue() {
        MrcpRequestState.fromString("");
    }

    @Test
    public void testEnumValues() {
        MrcpRequestState[] values = MrcpRequestState.values();
        assertEquals(3, values.length);
        
        // Verify all expected values are present
        boolean foundPending = false;
        boolean foundInProgress = false;
        boolean foundComplete = false;
        
        for (MrcpRequestState value : values) {
            switch (value) {
                case PENDING:
                    foundPending = true;
                    break;
                case IN_PROGRESS:
                    foundInProgress = true;
                    break;
                case COMPLETE:
                    foundComplete = true;
                    break;
            }
        }
        
        assertTrue("PENDING not found", foundPending);
        assertTrue("IN_PROGRESS not found", foundInProgress);
        assertTrue("COMPLETE not found", foundComplete);
    }

    @Test
    public void testValueOf() {
        assertEquals(MrcpRequestState.PENDING, MrcpRequestState.valueOf("PENDING"));
        assertEquals(MrcpRequestState.IN_PROGRESS, MrcpRequestState.valueOf("IN_PROGRESS"));
        assertEquals(MrcpRequestState.COMPLETE, MrcpRequestState.valueOf("COMPLETE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        MrcpRequestState.valueOf("INVALID_ENUM_VALUE");
    }

    @Test
    public void testAllStatesHaveValidStrings() {
        for (MrcpRequestState state : MrcpRequestState.values()) {
            assertNotNull("State name should not be null", state.toString());
            assertFalse("State name should not be empty", state.toString().isEmpty());
            
            // Test round trip conversion
            assertEquals("Round trip conversion should work", state, MrcpRequestState.fromString(state.toString()));
        }
    }

    @Test
    public void testStateTransitionLogic() {
        // Test that all expected states are available for state machine logic
        assertNotNull("PENDING state should exist", MrcpRequestState.PENDING);
        assertNotNull("IN_PROGRESS state should exist", MrcpRequestState.IN_PROGRESS);
        assertNotNull("COMPLETE state should exist", MrcpRequestState.COMPLETE);
        
        // Test ordering (ordinal values)
        assertTrue("PENDING should come before IN_PROGRESS", 
                   MrcpRequestState.PENDING.ordinal() < MrcpRequestState.IN_PROGRESS.ordinal());
        assertTrue("IN_PROGRESS should come before COMPLETE", 
                   MrcpRequestState.IN_PROGRESS.ordinal() < MrcpRequestState.COMPLETE.ordinal());
    }

    @Test
    public void testStringFormatMatching() {
        // Verify the exact string format matches MRCP specification
        assertEquals("PENDING", MrcpRequestState.PENDING.toString());
        assertEquals("IN-PROGRESS", MrcpRequestState.IN_PROGRESS.toString());
        assertEquals("COMPLETE", MrcpRequestState.COMPLETE.toString());
    }
}