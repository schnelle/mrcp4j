#!/usr/bin/env groovy

// Sample script to demonstrate changelog generation without GitHub API

def outputDir = new File("build/changelog")
outputDir.mkdirs()

// Sample data that would typically come from GitHub API
def sampleMilestoneData = [
    title: "0.3.1",
    number: 1,
    description: "Bug fixes and enhancements for version 0.3.1",
    due_on: "2025-08-15T00:00:00Z"
]

def sampleIssues = [
    [
        number: 1,
        title: "Mina Version too old",
        state: "closed",
        html_url: "https://github.com/schnelle/mrcp4j/issues/1",
        user: [login: "schnelle"],
        created_at: "2025-07-27T18:45:38Z",
        closed_at: "2025-07-27T19:00:37Z",
        labels: [
            [name: "bug"],
            [name: "dependencies"]
        ]
    ],
    [
        number: 6,
        title: "Lack of unit tests",
        state: "closed",
        html_url: "https://github.com/schnelle/mrcp4j/issues/6",
        user: [login: "schnelle"],
        created_at: "2025-07-28T09:15:35Z",
        closed_at: "2025-07-28T10:23:13Z",
        labels: [
            [name: "enhancement"],
            [name: "testing"]
        ]
    ],
    [
        number: 9,
        title: "Automated changelog from issues",
        state: "open",
        html_url: "https://github.com/schnelle/mrcp4j/issues/9",
        user: [login: "schnelle"],
        created_at: "2025-07-28T13:32:10Z",
        closed_at: null,
        labels: [
            [name: "enhancement"],
            [name: "documentation"]
        ]
    ]
]

// Categorize issues by type
def categorizeIssue(issue) {
    def labels = issue.labels?.collect { it.name } ?: []
    
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

// Sort issues by type
def issuesByType = [:]
sampleIssues.each { issue ->
    def type = categorizeIssue(issue)
    if (!issuesByType.containsKey(type)) {
        issuesByType[type] = []
    }
    issuesByType[type].add(issue)
}

// Generate HTML
def generateHtml(String repo, String milestone, Map milestoneData, Map issuesByType) {
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
        html << "\n        <p><strong>Due Date:</strong> ${milestoneData.due_on.substring(0, 10)}</p>"
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

def html = generateHtml("schnelle/mrcp4j", "0.3.1", sampleMilestoneData, issuesByType)

def outputFile = new File(outputDir, "changelog-0-3-1.html")
outputFile.text = html

println "Sample changelog generated: ${outputFile.absolutePath}"
println "Found ${sampleIssues.size()} sample issues in milestone '0.3.1'"