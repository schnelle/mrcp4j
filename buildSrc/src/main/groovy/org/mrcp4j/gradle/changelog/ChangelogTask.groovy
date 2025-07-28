package org.mrcp4j.gradle.changelog

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import groovy.json.JsonSlurper

class ChangelogTask extends DefaultTask {
    
    @Internal
    ChangelogExtension extension
    
    @TaskAction
    void generateChangelog() {
        // Get configuration from project properties (command line) or extension (build script)
        def githubRepo = project.findProperty('changelog.github.repo') ?: extension.githubRepo
        def milestone = project.findProperty('changelog.github.milestone') ?: extension.githubMilestone
        def githubToken = project.findProperty('changelog.github.token') ?: extension.githubToken
        def outputDirPath = extension.outputDir ?: "${project.buildDir}/changelog"
        
        if (!milestone) {
            throw new GradleException("Please specify a milestone using -Pchangelog.github.milestone=MILESTONE_NAME or configure it in the changelog extension")
        }
        
        // Create output directory
        def outputDir = project.file(outputDirPath)
        outputDir.mkdirs()
        
        // Generate changelog
        generateChangelogFromGitHub(githubRepo, milestone, githubToken, outputDir)
    }
    
    private void generateChangelogFromGitHub(String repo, String milestone, String token, File outputDir) {
        def baseUrl = "https://api.github.com"
        
        // First, get the milestone ID
        def milestonesUrl = "${baseUrl}/repos/${repo}/milestones"
        def milestonesResponse = makeGitHubRequest(milestonesUrl, token)
        
        def milestones = parseJsonResponse(milestonesResponse)
        def targetMilestone = milestones.find { it.title == milestone }
        
        if (!targetMilestone) {
            def availableMilestones = milestones.collect { it.title }.join(', ')
            throw new GradleException("Milestone '${milestone}' not found. Available milestones: ${availableMilestones}")
        }
        
        def milestoneNumber = targetMilestone.number
        
        // Get issues for the milestone
        def issuesUrl = "${baseUrl}/repos/${repo}/issues?milestone=${milestoneNumber}&state=all&per_page=100"
        def issuesResponse = makeGitHubRequest(issuesUrl, token)
        def issues = parseJsonResponse(issuesResponse)
        
        // Sort issues by type (based on labels)
        def issuesByType = [:]
        issues.each { issue ->
            def type = categorizeIssue(issue)
            if (!issuesByType.containsKey(type)) {
                issuesByType[type] = []
            }
            issuesByType[type].add(issue)
        }
        
        // Generate HTML
        def html = generateHtml(repo, milestone, targetMilestone, issuesByType)
        
        // Write to file
        def outputFile = new File(outputDir, "changelog-${milestone.replaceAll('[^a-zA-Z0-9]', '-')}.html")
        outputFile.text = html
        
        println "Changelog generated: ${outputFile.absolutePath}"
        println "Found ${issues.size()} issues in milestone '${milestone}'"
    }
    
    private String makeGitHubRequest(String url, String token) {
        def connection = new URL(url).openConnection()
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
        connection.setRequestProperty("User-Agent", "mrcp4j-changelog-generator")
        
        if (token) {
            connection.setRequestProperty("Authorization", "Bearer ${token}")
        }
        
        if (connection.responseCode != 200) {
            throw new GradleException("Failed to fetch from GitHub API: ${connection.responseCode} ${connection.responseMessage}")
        }
        
        return connection.inputStream.text
    }
    
    private def parseJsonResponse(String jsonText) {
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(jsonText)
    }
    
    private String categorizeIssue(issue) {
        def labels = issue.labels?.collect { it.name } ?: []
        
        // Categorize based on labels
        if (labels.any { it.toLowerCase().contains('bug') || it.toLowerCase().contains('fix') }) {
            return 'Bug Fixes'
        } else if (labels.any { it.toLowerCase().contains('enhancement') || it.toLowerCase().contains('feature') }) {
            return 'Enhancements'
        } else if (labels.any { it.toLowerCase().contains('documentation') || it.toLowerCase().contains('doc') }) {
            return 'Documentation'
        } else if (labels.any { it.toLowerCase().contains('test') }) {
            return 'Testing'
        } else if (labels.any { it.toLowerCase().contains('chore') || it.toLowerCase().contains('maintenance') }) {
            return 'Maintenance'
        } else {
            return 'Other'
        }
    }
    
    private String generateHtml(String repo, String milestone, Map milestoneData, Map issuesByType) {
        def html = new StringBuilder()
        def repoUrl = "https://github.com/${repo}"
        def milestoneUrl = "${repoUrl}/milestone/${milestoneData.number}"
        
        html << """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Changelog - ${milestone}</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        h1, h2 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
        }
        .milestone-info {
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
        }
        .issue-category {
            margin: 30px 0;
        }
        .issue-list {
            list-style: none;
            padding: 0;
        }
        .issue-item {
            background: #fff;
            border: 1px solid #e1e4e8;
            border-radius: 5px;
            margin: 10px 0;
            padding: 15px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }
        .issue-title {
            font-weight: bold;
            margin-bottom: 5px;
        }
        .issue-title a {
            color: #0366d6;
            text-decoration: none;
        }
        .issue-title a:hover {
            text-decoration: underline;
        }
        .issue-meta {
            font-size: 0.9em;
            color: #6a737d;
        }
        .issue-labels {
            margin-top: 10px;
        }
        .label {
            display: inline-block;
            background: #f1f8ff;
            color: #0366d6;
            padding: 2px 8px;
            margin: 2px;
            border-radius: 12px;
            font-size: 0.8em;
        }
        .state-closed {
            color: #28a745;
        }
        .state-open {
            color: #d73a49;
        }
    </style>
</head>
<body>
    <h1>Changelog - ${milestone}</h1>
    
    <div class="milestone-info">
        <p><strong>Repository:</strong> <a href="${repoUrl}">${repo}</a></p>
        <p><strong>Milestone:</strong> <a href="${milestoneUrl}">${milestone}</a></p>"""
        
        if (milestoneData.description) {
            html << "\n        <p><strong>Description:</strong> ${milestoneData.description}</p>"
        }
        
        if (milestoneData.due_on) {
            html << "\n        <p><strong>Due Date:</strong> ${milestoneData.due_on}</p>"
        }
        
        html << """
        <p><strong>Generated:</strong> ${new Date().format('yyyy-MM-dd HH:mm:ss')} UTC</p>
    </div>
"""
        
        if (issuesByType.isEmpty()) {
            html << """
    <div class="issue-category">
        <p>No issues found in this milestone.</p>
    </div>
"""
        } else {
            // Sort categories for consistent output
            def sortedCategories = issuesByType.keySet().sort()
            
            sortedCategories.each { category ->
                def issues = issuesByType[category]
                html << """
    <div class="issue-category">
        <h2>${category} (${issues.size()})</h2>
        <ul class="issue-list">"""
                
                issues.each { issue ->
                    def stateClass = issue.state == 'closed' ? 'state-closed' : 'state-open'
                    html << """
            <li class="issue-item">
                <div class="issue-title">
                    <a href="${issue.html_url}" target="_blank">#${issue.number}: ${issue.title}</a>
                    <span class="${stateClass}">[${issue.state}]</span>
                </div>
                <div class="issue-meta">
                    Created by ${issue.user.login} on ${issue.created_at.substring(0, 10)}"""
                    
                    if (issue.closed_at) {
                        html << " • Closed on ${issue.closed_at.substring(0, 10)}"
                    }
                    
                    html << """
                </div>"""
                    
                    if (issue.labels && !issue.labels.isEmpty()) {
                        html << """
                <div class="issue-labels">"""
                        issue.labels.each { label ->
                            html << """
                    <span class="label">${label.name}</span>"""
                        }
                        html << """
                </div>"""
                    }
                    
                    html << """
            </li>"""
                }
                
                html << """
        </ul>
    </div>"""
            }
        }
        
        html << """
</body>
</html>"""
        
        return html.toString()
    }
}