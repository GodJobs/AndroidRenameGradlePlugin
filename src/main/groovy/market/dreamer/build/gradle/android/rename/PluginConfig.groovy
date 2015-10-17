package market.dreamer.build.gradle.android.rename

final class PluginConfig {
    private PluginConfig() {}

    static final Integer GRADLE_MAJOR_VERSION = 2;
    static final Integer GRADLE_MINOR_VERSION = 3;

    static final String ANDROID_APPLICATION_PLUGIN_ID = "com.android.application";
    static final String ANDROID_LIBRARY_PLUGIN_ID = "com.android.library";

    static final String EXTENSION_NAME = "renameConfig";
    static final String DEFAULT_DIVIDER = "-";
    static final String ZIP_UNALIGNED = "unaligned";
    static final String ZIP_ALIGNED = "";
}
