package org.mrcp4j.test;

import org.junit.Test;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Assert;

/**
 * Test to verify that Apache Mina 2.x has been successfully integrated
 */
public class MinaUpgradeValidationTest {

    @Test
    public void testMinaVersion() {
        // Test that we can create a NioSocketAcceptor (Mina 2.x class)
        IoAcceptor acceptor = new NioSocketAcceptor();
        Assert.assertNotNull("Should be able to create NioSocketAcceptor", acceptor);
        
        // Clean up
        acceptor.dispose();
    }
    
    @Test 
    public void testMinaPackageStructure() {
        // Verify that we're using the new package structure
        String packageName = IoAcceptor.class.getPackage().getName();
        Assert.assertTrue("Should use new Mina 2.x package structure", 
                         packageName.startsWith("org.apache.mina.core"));
    }
}