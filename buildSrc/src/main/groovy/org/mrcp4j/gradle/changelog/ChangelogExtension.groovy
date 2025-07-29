package org.mrcp4j.gradle.changelog

import org.gradle.api.Project

class ChangelogExtension {
    String githubRepo = 'schnelle/mrcp4j'
    String githubMilestone
    String githubToken
    String outputDir
    
    ChangelogExtension(Project project) {
        this.outputDir = "${project.buildDir}/changelog"
    }
    
    String getGithubRepo() {
        return githubRepo
    }
    
    void setGithubRepo(String githubRepo) {
        this.githubRepo = githubRepo
    }
    
    String getGithubMilestone() {
        return githubMilestone
    }
    
    void setGithubMilestone(String githubMilestone) {
        this.githubMilestone = githubMilestone
    }
    
    String getGithubToken() {
        return githubToken
    }
    
    void setGithubToken(String githubToken) {
        this.githubToken = githubToken
    }
    
    String getOutputDir() {
        return outputDir
    }
    
    void setOutputDir(String outputDir) {
        this.outputDir = outputDir
    }
}