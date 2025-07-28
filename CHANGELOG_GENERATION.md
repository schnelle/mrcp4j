# Changelog Generation

This project includes an automated changelog generation feature that creates HTML changelog pages from GitHub issues associated with milestones using a custom Gradle plugin.

## Usage

The changelog generation functionality is provided through the `org.mrcp4j.changelog` Gradle plugin which is automatically applied in the project.

### Basic Usage

Generate a changelog for a specific milestone:

```bash
./gradlew generateChangelog -Pchangelog.github.milestone="1.0.0"
```

### Configuration

The plugin can be configured in the `build.gradle` file using the `changelog` extension block:

```gradle
changelog {
    githubRepo = 'schnelle/mrcp4j'           // Default repository
    githubMilestone = '1.0.0'               // Can be set here instead of command line
    githubToken = 'ghp_xxxxxxxxxxxxx'       // Optional authentication token
    outputDir = "${buildDir}/changelog"     // Output directory
}
```

### Configuration Properties

The following properties can be configured in the extension block or passed via command line:

- `changelog.github.repo` - GitHub repository in format `owner/repo` (default: `schnelle/mrcp4j`)
- `changelog.github.milestone` - Milestone name to generate changelog for (required)
- `changelog.github.token` - GitHub personal access token for API authentication (optional but recommended)

### Examples

**Generate changelog for milestone "0.3.1":**
```bash
./gradlew generateChangelog -Pchangelog.github.milestone="0.3.1"
```

**Generate changelog with custom repository:**
```bash
./gradlew generateChangelog \
  -Pchangelog.github.repo="myorg/myrepo" \
  -Pchangelog.github.milestone="2.0.0"
```

**Generate changelog with authentication (recommended for private repos or to avoid rate limits):**
```bash
./gradlew generateChangelog \
  -Pchangelog.github.milestone="0.3.1" \
  -Pchangelog.github.token="ghp_xxxxxxxxxxxx"
```

**Configure in build.gradle and run without parameters:**
```gradle
changelog {
    githubMilestone = '0.3.1'
    githubToken = System.getenv('GITHUB_TOKEN')
}
```
```bash
./gradlew generateChangelog
```

## Plugin Implementation

The changelog generation functionality is implemented as a custom Gradle plugin located in the `buildSrc` directory:

- `buildSrc/src/main/groovy/org/mrcp4j/gradle/changelog/ChangelogPlugin.groovy` - Main plugin class
- `buildSrc/src/main/groovy/org/mrcp4j/gradle/changelog/ChangelogTask.groovy` - Task implementation
- `buildSrc/src/main/groovy/org/mrcp4j/gradle/changelog/ChangelogExtension.groovy` - Configuration extension

This modular approach keeps the main `build.gradle` clean while providing full functionality.

## Output

The generated changelog will be saved as an HTML file in `build/changelog/` directory:

- File format: `changelog-{milestone-name}.html`
- Example: `build/changelog/changelog-0-3-1.html`

## Issue Categorization

Issues are automatically categorized based on their labels:

- **Bug Fixes** - Issues with labels containing "bug" or "fix"
- **Enhancements** - Issues with labels containing "enhancement" or "feature"  
- **Documentation** - Issues with labels containing "documentation" or "doc"
- **Testing** - Issues with labels containing "test"
- **Maintenance** - Issues with labels containing "chore" or "maintenance"
- **Other** - Issues that don't match any of the above categories

## HTML Features

The generated HTML changelog includes:

- Professional GitHub-style formatting
- Responsive design for mobile and desktop
- Links to original GitHub issues
- Issue status indicators (open/closed)
- Label display
- Milestone information with description and due date
- Creation and closure dates for issues
- Grouped categorization by issue type

## Authentication

While authentication is optional, it's recommended to:

- Avoid GitHub API rate limits (60 requests/hour for unauthenticated, 5000/hour for authenticated)
- Access private repositories
- Get more detailed information from the GitHub API

To use authentication:

1. Create a GitHub Personal Access Token at https://github.com/settings/tokens
2. Grant it `repo` scope for private repositories or `public_repo` for public repositories
3. Pass it via the `changelog.github.token` property

## Sample Output

A sample changelog has been generated at `build/changelog/changelog-sample.html` showing the expected output format.