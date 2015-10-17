package market.dreamer.build.gradle.android.rename

import org.gradle.api.GradleException
import org.gradle.api.Project

final class PluginPreconditions {
    private PluginPreconditions() {}

    static void checkConditions(Project project) {
        checkGradleVersions(project);
        checkAndroidPlugins(project);
    }

    static void checkGradleVersions(Project project, Integer gradleMajorVersion = PluginConfig.GRADLE_MAJOR_VERSION, Integer gradleMinorVersion = PluginConfig.GRADLE_MINOR_VERSION) {
        def gradleVersions = project.gradle.gradleVersion =~ "(\\d*)\\.(\\d*).*";
        if (!gradleVersions || !gradleVersions.matches() || gradleVersions.group(1).toInteger() < gradleMajorVersion || gradleVersions.group(2).toInteger() < gradleMinorVersion) {
            println("You are using Gradle ${project.gradle.gradleVersion}: This version of the rename plugin requires minimum Gradle version ${gradleMajorVersion}.${gradleMinorVersion}");
        }
    }

    static void checkAndroidPlugins(Project project) {
        if (!project.plugins.hasPlugin(PluginConfig.ANDROID_APPLICATION_PLUGIN_ID) && !project.plugins.hasPlugin(PluginConfig.ANDROID_LIBRARY_PLUGIN_ID)) {
            throw new GradleException('Please apply the Android Application plugin or the Android Library plugin first')
        }
    }
}
