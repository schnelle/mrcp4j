# Changelog Plugin

This directory contains a custom Gradle plugin for automated changelog generation from GitHub issues.

## Plugin Structure

- `build.gradle` - Plugin build configuration
- `src/main/groovy/org/mrcp4j/gradle/changelog/`
  - `ChangelogPlugin.groovy` - Main plugin class that registers the task and extension
  - `ChangelogExtension.groovy` - Configuration extension for the plugin
  - `ChangelogTask.groovy` - Task implementation that handles GitHub API interaction and HTML generation

## Plugin ID

- `org.mrcp4j.changelog`

## Extension Configuration

The plugin provides a `changelog` extension block for configuration:

```gradle
changelog {
    githubRepo = 'owner/repo'
    githubMilestone = 'milestone-name'
    githubToken = 'token'
    outputDir = 'path/to/output'
}
```

## Task Registration

The plugin registers the `generateChangelog` task in the `documentation` group.

## Building the Plugin

The plugin is automatically built as part of the main project build process since it's located in the `buildSrc` directory.