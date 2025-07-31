package org.mrcp4j.gradle.changelog

import org.gradle.api.GradleException
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import static org.junit.Assert.*

class ChangelogPluginTest {
    
    @Test
    void testPluginApplied() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.mrcp4j.changelog'
        
        // Verify the plugin is applied and task is registered
        assertTrue(project.tasks.findByName('generateChangelog') != null)
        assertTrue(project.extensions.findByName('changelog') != null)
        
        def task = project.tasks.findByName('generateChangelog')
        assertTrue(task instanceof ChangelogTask)
        
        // Verify task properties
        assertEquals('documentation', task.group)
        assertEquals('Generates an HTML changelog from GitHub issues in a milestone', task.description)
    }
    
    @Test
    void testExtensionConfiguration() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.mrcp4j.changelog'
        
        def extension = project.extensions.findByName('changelog')
        
        // Test default values
        assertEquals('schnelle/mrcp4j', extension.githubRepo)
        assertTrue("Output dir should end with /changelog", extension.outputDir.toString().endsWith("/changelog"))
        assertNull(extension.githubMilestone)
        assertNull(extension.githubToken)
        
        // Test configuration
        extension.githubRepo = 'test/repo'
        extension.githubMilestone = '1.0.0'
        extension.githubToken = 'test-token'
        extension.outputDir = '/tmp/changelog'
        
        assertEquals('test/repo', extension.githubRepo)
        assertEquals('1.0.0', extension.githubMilestone)
        assertEquals('test-token', extension.githubToken)
        assertEquals('/tmp/changelog', extension.outputDir)
    }
    
    @Test
    void testTaskFailsWithoutMilestone() {
        def project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.mrcp4j.changelog'
        
        def task = project.tasks.findByName('generateChangelog')
        
        try {
            task.generateChangelog()
            fail("Expected GradleException")
        } catch (GradleException e) {
            assertTrue(e.message.contains("Please specify a milestone"))
        }
    }
}