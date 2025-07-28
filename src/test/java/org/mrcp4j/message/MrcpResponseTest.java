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
import org.mrcp4j.MrcpRequestState;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpResponse class.
 */
public class MrcpResponseTest {

    @Test
    public void testStatusCodeConstants() {
        assertEquals(200, MrcpResponse.STATUS_SUCCESS);
        assertEquals(201, MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED);
        assertEquals(401, MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertEquals(402, MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE);
        assertEquals(403, MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        assertEquals(404, MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER);
        assertEquals(405, MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        assertEquals(406, MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        assertEquals(407, MrcpResponse.STATUS_OPERATION_FAILED);
        assertEquals(408, MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY);
        assertEquals(409, MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE);
        assertEquals(410, MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER);
        assertEquals(501, MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals(502, MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED);
        assertEquals(503, MrcpResponse.STATUS_PROXY_TIMEOUT);
        assertEquals(504, MrcpResponse.STATUS_MESSAGE_TOO_LARGE);
    }

    @Test
    public void testStatusCodeGetterSetter() {
        MrcpResponse response = createTestResponse();
        assertEquals(-1, response.getStatusCode()); // Default value
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        assertEquals(MrcpResponse.STATUS_SUCCESS, response.getStatusCode());
        
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        assertEquals(MrcpResponse.STATUS_OPERATION_FAILED, response.getStatusCode());
    }

    @Test
    public void testStatusDesc() {
        MrcpResponse response = createTestResponse();
        
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        assertEquals("STATUS_SUCCESS", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        assertEquals("STATUS_METHOD_NOT_ALLOWED", response.getStatusDesc());
        
        response.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        assertEquals("STATUS_SERVER_INTERNAL_ERROR", response.getStatusDesc());
    }

    @Test
    public void testStatusDescForUnknownCode() {
        MrcpResponse response = createTestResponse();
        response.setStatusCode((short) 999); // Unknown status code
        assertNull(response.getStatusDesc());
    }

    @Test
    public void testAppendStartLine() {
        MrcpResponse response = createTestResponse();
        response.setVersion("MRCP/2.0");
        response.setMessageLength(150);
        response.setRequestID(12345);
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        response.setRequestState(MrcpRequestState.COMPLETE);
        
        StringBuilder sb = new StringBuilder();
        StringBuilder result = response.appendStartLine(sb);
        
        assertNotNull(result);
        String startLine = result.toString();
        assertTrue(startLine.contains("MRCP/2.0"));
        assertTrue(startLine.contains("150"));
        assertTrue(startLine.contains("12345"));
        assertTrue(startLine.contains("200"));
        assertTrue(startLine.contains("COMPLETE"));
        assertTrue(startLine.endsWith(MrcpMessage.CRLF));
    }

    @Test
    public void testInheritanceFromMrcpServerMessage() {
        MrcpResponse response = createTestResponse();
        assertTrue(response instanceof MrcpServerMessage);
        assertTrue(response instanceof MrcpMessage);
    }

    @Test
    public void testAllStatusCodesHaveDescriptions() {
        short[] statusCodes = {
            MrcpResponse.STATUS_SUCCESS,
            MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED,
            MrcpResponse.STATUS_METHOD_NOT_ALLOWED,
            MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER,
            MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER,
            MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED,
            MrcpResponse.STATUS_MANDATORY_HEADER_MISSING,
            MrcpResponse.STATUS_OPERATION_FAILED,
            MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE,
            MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER,
            MrcpResponse.STATUS_SERVER_INTERNAL_ERROR,
            MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED,
            MrcpResponse.STATUS_PROXY_TIMEOUT,
            MrcpResponse.STATUS_MESSAGE_TOO_LARGE
        };
        
        MrcpResponse response = createTestResponse();
        for (short statusCode : statusCodes) {
            response.setStatusCode(statusCode);
            String desc = response.getStatusDesc();
            assertNotNull("Status description should not be null for code " + statusCode, desc);
            assertFalse("Status description should not be empty for code " + statusCode, desc.isEmpty());
        }
    }

    @Test
    public void testSuccessCodes() {
        assertTrue("200 should be a success code", MrcpResponse.STATUS_SUCCESS >= 200 && MrcpResponse.STATUS_SUCCESS < 300);
        assertTrue("201 should be a success code", MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED >= 200 && MrcpResponse.STATUS_SUCCESS_SOME_OPTIONAL_HEADERS_IGNORED < 300);
    }

    @Test
    public void testClientErrorCodes() {
        short[] clientErrorCodes = {
            MrcpResponse.STATUS_METHOD_NOT_ALLOWED,
            MrcpResponse.STATUS_METHOD_NOT_VALID_IN_STATE,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER,
            MrcpResponse.STATUS_ILLEGAL_VALUE_FOR_HEADER,
            MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED,
            MrcpResponse.STATUS_MANDATORY_HEADER_MISSING,
            MrcpResponse.STATUS_OPERATION_FAILED,
            MrcpResponse.STATUS_UNRECOGNIZED_MESSAGE_ENTITY,
            MrcpResponse.STATUS_UNSUPPORTED_HEADER_VALUE,
            MrcpResponse.STATUS_NON_MONOTONIC_SEQUENCE_NUMBER
        };
        
        for (short code : clientErrorCodes) {
            assertTrue("Code " + code + " should be in 4xx range", code >= 400 && code < 500);
        }
    }

    @Test
    public void testServerErrorCodes() {
        short[] serverErrorCodes = {
            MrcpResponse.STATUS_SERVER_INTERNAL_ERROR,
            MrcpResponse.STATUS_PROTOCOL_VERSION_NOT_SUPPORTED,
            MrcpResponse.STATUS_PROXY_TIMEOUT,
            MrcpResponse.STATUS_MESSAGE_TOO_LARGE
        };
        
        for (short code : serverErrorCodes) {
            assertTrue("Code " + code + " should be in 5xx range", code >= 500 && code < 600);
        }
    }

    /**
     * Helper method to create a test response instance.
     */
    private MrcpResponse createTestResponse() {
        return new MrcpResponse();
    }
}