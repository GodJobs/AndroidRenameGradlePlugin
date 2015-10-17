package market.dreamer.build.gradle.android.rename

import market.dreamer.build.gradle.android.rename.callable.*

import static market.dreamer.build.gradle.android.rename.PluginConfig.*

class PluginExtension {
    private def
            applicationName = {} as ApplicationNameCallback,
            versionName = { versionName -> versionName } as VersionNameCallback,
            versionCode = { versionCode -> versionCode } as VersionCodeCallback,
            buildType = { buildType -> buildType.name } as BuildTypeCallback,
            flavors = { flavors -> flavors*.name.join(DEFAULT_DIVIDER) } as FlavorCallback,
            divider = { DEFAULT_DIVIDER } as DividerCallback,
            zipAligned = { zipAligned -> zipAligned ? ZIP_ALIGNED : ZIP_UNALIGNED } as ZipAlignCallback,
            includes = [applicationName, versionName, versionCode, buildType, flavors, zipAligned];

    def getApplicationName() {
        return applicationName
    }

    void setApplicationName(applicationName) {
        this.applicationName = (PluginUtils.toCallable(applicationName) as ApplicationNameCallback);
    }

    def getVersionName() {
        return versionName
    }

    void setVersionName(versionName) {
        this.versionName = (PluginUtils.toCallable(versionName) as VersionNameCallback);
    }

    def getVersionCode() {
        return versionCode
    }

    void setVersionCode(versionCode) {
        this.versionCode = (PluginUtils.toCallable(versionCode) as VersionCodeCallback);
    }

    def getBuildType() {
        return buildType
    }

    void setBuildType(buildType) {
        this.buildType = (PluginUtils.toCallable(buildType) as BuildTypeCallback);
    }

    def getFlavors() {
        return flavors
    }

    void setFlavors(flavors) {
        this.flavors = (PluginUtils.toCallable(flavors) as FlavorCallback);
    }

    def getDivider() {
        return divider
    }

    void setDivider(divider) {
        this.divider = (PluginUtils.toCallable(divider) as DividerCallback);
    }

    def getZipAligned() {
        return zipAligned
    }

    void setZipAligned(zipAlign) {
        this.zipAligned = zipAlign
    }

    def getIncludes() {
        return includes
    }

    void setIncludes(includes) {
        this.includes = includes
    }
}
