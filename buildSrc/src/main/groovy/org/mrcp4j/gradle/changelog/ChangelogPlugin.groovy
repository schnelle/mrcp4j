package org.mrcp4j.gradle.changelog

import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangelogPlugin implements Plugin<Project> {
    
    @Override
    void apply(Project project) {
        // Create the extension for configuration
        def extension = project.extensions.create('changelog', ChangelogExtension, project)
        
        // Register the changelog generation task
        project.tasks.register('generateChangelog', ChangelogTask) { task ->
            task.extension = extension
            task.description = 'Generates an HTML changelog from GitHub issues in a milestone'
            task.group = 'documentation'
        }
    }
}