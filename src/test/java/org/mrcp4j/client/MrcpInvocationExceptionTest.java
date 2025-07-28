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
import org.mrcp4j.MrcpException;
import org.mrcp4j.message.MrcpResponse;
import static org.junit.Assert.*;

/**
 * Unit tests for MrcpInvocationException class.
 */
public class MrcpInvocationExceptionTest {

    @Test
    public void testConstructorWithResponse() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals(response, exception.getResponse());
        assertNotNull(exception.getMessage());
        assertTrue("Message should contain status code", exception.getMessage().contains("407"));
        assertTrue("Message should contain status description", exception.getMessage().contains("STATUS_OPERATION_FAILED"));
    }

    @Test
    public void testConstructorWithDifferentStatusCodes() {
        // Test with client error
        MrcpResponse clientErrorResponse = new MrcpResponse();
        clientErrorResponse.setStatusCode(MrcpResponse.STATUS_METHOD_NOT_ALLOWED);
        
        MrcpInvocationException clientException = new MrcpInvocationException(clientErrorResponse);
        assertEquals(clientErrorResponse, clientException.getResponse());
        assertTrue("Should contain 401 status code", clientException.getMessage().contains("401"));
        
        // Test with server error
        MrcpResponse serverErrorResponse = new MrcpResponse();
        serverErrorResponse.setStatusCode(MrcpResponse.STATUS_SERVER_INTERNAL_ERROR);
        
        MrcpInvocationException serverException = new MrcpInvocationException(serverErrorResponse);
        assertEquals(serverErrorResponse, serverException.getResponse());
        assertTrue("Should contain 501 status code", serverException.getMessage().contains("501"));
    }

    @Test
    public void testGetResponse() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_UNSUPPORTED_HEADER);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertSame("Should return the same response instance", response, exception.getResponse());
    }

    @Test
    public void testInheritanceFromMrcpException() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertTrue(exception instanceof MrcpException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    public void testMessageFormat() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_MANDATORY_HEADER_MISSING);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        String message = exception.getMessage();
        
        assertNotNull("Message should not be null", message);
        assertTrue("Message should contain 'MRCPv2 Status Code:'", message.contains("MRCPv2 Status Code:"));
        assertTrue("Message should contain status code", message.contains("406"));
        assertTrue("Message should contain status description", message.contains("STATUS_MANDATORY_HEADER_MISSING"));
        assertTrue("Message should contain brackets", message.contains("[") && message.contains("]"));
    }

    @Test
    public void testWithUnknownStatusCode() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode((short) 999); // Unknown status code
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertEquals(response, exception.getResponse());
        assertTrue("Message should contain status code", exception.getMessage().contains("999"));
        assertTrue("Message should contain null for unknown status", exception.getMessage().contains("null"));
    }

    @Test
    public void testStackTrace() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    public void testThrowAndCatch() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_RESOURCE_NOT_ALLOCATED);
        
        try {
            throw new MrcpInvocationException(response);
        } catch (MrcpInvocationException e) {
            assertEquals(response, e.getResponse());
            assertTrue(e instanceof MrcpException);
            assertTrue("Message should contain status code", e.getMessage().contains("405"));
        }
    }

    @Test
    public void testSerialization() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        assertNotNull(exception.toString());
        assertTrue(exception.toString().contains("MrcpInvocationException"));
    }

    @Test
    public void testResponseImmutability() {
        MrcpResponse response = new MrcpResponse();
        response.setStatusCode(MrcpResponse.STATUS_SUCCESS);
        
        MrcpInvocationException exception = new MrcpInvocationException(response);
        
        // Modify the original response
        response.setStatusCode(MrcpResponse.STATUS_OPERATION_FAILED);
        
        // The exception should still reference the same object (it doesn't make a copy)
        assertSame("Should reference the same response object", response, exception.getResponse());
        assertEquals("Should reflect the modification", MrcpResponse.STATUS_OPERATION_FAILED, exception.getResponse().getStatusCode());
    }

    @Test
    public void testAllStatusCodes() {
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
        
        for (short statusCode : statusCodes) {
            MrcpResponse response = new MrcpResponse();
            response.setStatusCode(statusCode);
            
            MrcpInvocationException exception = new MrcpInvocationException(response);
            assertEquals("Response should be preserved for status code " + statusCode, 
                        response, exception.getResponse());
            assertTrue("Message should contain status code " + statusCode, 
                      exception.getMessage().contains(String.valueOf(statusCode)));
        }
    }
}