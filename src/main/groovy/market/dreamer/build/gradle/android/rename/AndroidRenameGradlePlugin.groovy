package market.dreamer.build.gradle.android.rename

import com.android.build.gradle.BaseExtension
import market.dreamer.build.gradle.android.rename.callable.*
import org.gradle.api.Plugin
import org.gradle.api.Project

import static market.dreamer.build.gradle.android.rename.PluginConfig.EXTENSION_NAME
import static market.dreamer.build.gradle.android.rename.PluginUtils.callOrReturn

final class AndroidRenameGradlePlugin implements Plugin<Project> {
    private Project project;
    private BaseExtension androidExtension;

    @Override
    void apply(Project target) {
        PluginPreconditions.checkConditions(target);

        project = target;
        project.extensions.create(EXTENSION_NAME, PluginExtension);
        androidExtension = project.android;
        project.afterEvaluate {
            //TODO check validation
            applyChanges();
        }
    }

    private def getNonTestVariants() {
        return PluginUtils.isAndroidLibraryPluginApplied(project) ? androidExtension.libraryVariants : project.android.applicationVariants
    }

    private void applyChanges() {
        PluginExtension renameConfig = project[EXTENSION_NAME];
        if (!renameConfig || !renameConfig.includes) return;
        final List includes = renameConfig.includes;

        def apkVariants = getNonTestVariants();
        apkVariants.all { variant ->
            List fileNamePrefixFragments = [];
            includes.each { item ->
                String appendFragment;
                switch (item) {
                    case ApplicationNameCallback:
                    case Closure:
                    case String:
                        appendFragment = callOrReturn(item)
                        break;
                    case VersionNameCallback:
                        appendFragment = callOrReturn(item, variant.hasProperty('versionName') ? variant.versionName : androidExtension.defaultConfig.versionName)
                        break;
                    case VersionCodeCallback:
                        appendFragment = callOrReturn(item, variant.hasProperty('versionCode') ? variant.versionCode : androidExtension.defaultConfig.versionCode)
                        break;
                    case BuildTypeCallback:
                        appendFragment = callOrReturn(item, variant.buildType)
                        break;
                    case FlavorCallback:
                        appendFragment = callOrReturn(item, variant.productFlavors)
                        break;
                }
                if (appendFragment) {
                    fileNamePrefixFragments << appendFragment
                }
            }
            def fileNamePrefix = fileNamePrefixFragments.join(callOrReturn(renameConfig.divider))

            variant.outputs.each { output ->
                final String fileNameSuffix = getFileNameSuffix(output.outputFile);
                if (PluginUtils.isAndroidLibraryPluginApplied(project)) {
                    def outputFile = output.outputFile
                    output.outputFile = new File(outputFile.parent, "${fileNamePrefix}${fileNameSuffix}")
                } else {
                    if (output.zipAlign) {
                        def outputFile = output.outputFile
                        output.outputFile = new File(outputFile.parent, "${fileNamePrefix}${appendDividerIfNeeded(renameConfig, true)}${fileNameSuffix}")
                    }

                    def file = output.packageApplication.outputFile
                    output.packageApplication.outputFile = new File(file.parent, "${fileNamePrefix}${appendDividerIfNeeded(renameConfig, false)}${fileNameSuffix}")
                }
            }
        }
    }

    private String getFileNameSuffix(file) {
        final String fileName = file.name

        def lastIndexOf = fileName.lastIndexOf(".")
        if (lastIndexOf >= 0) {
            return fileName.substring(lastIndexOf);
        }

        return "";
    }

    private String getZipAlignedFragment(renameConfig, zipAligned) {
        if (renameConfig.zipAligned) {
            return callOrReturn(renameConfig.zipAligned, zipAligned);
        }
        return "";
    }

    private String appendDividerIfNeeded(renameConfig, zipAligned) {
        final String zipAlignedFragment = getZipAlignedFragment(renameConfig, zipAligned)
        zipAlignedFragment ? callOrReturn(renameConfig.divider) + zipAlignedFragment : ""
    }
}
