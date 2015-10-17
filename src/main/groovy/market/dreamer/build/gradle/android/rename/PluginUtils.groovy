package market.dreamer.build.gradle.android.rename

import market.dreamer.build.gradle.android.rename.callable.CallableCallback
import org.gradle.api.Project

final class PluginUtils {
    private PluginUtils() {}

    static boolean isAndroidLibraryPluginApplied(Project project) {
        return project.plugins.hasPlugin(PluginConfig.ANDROID_LIBRARY_PLUGIN_ID);
    }

    static boolean callable(instance) {
        (instance instanceof Closure) || (instance instanceof CallableCallback)
    }

    static def toCallable(returnValue) {
        (!returnValue || callable(returnValue)) ? returnValue : { returnValue }
    }

    static def callOrReturn(returnValue, inValue = null) {
        returnEmptyNotNull(callable(returnValue) ? returnValue(inValue) : returnValue)
    }

    private static def returnEmptyNotNull(value) {
        value == null ? "" : value
    }
}
