[![Maven Central](https://maven-badges.herokuapp.com/maven-central/market.dreamer.build.android/android-rename/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/market.dreamer.build.android/android-rename)
================

AndroidRenameGradlePlugin
================

Description
================

`Android` 应用打包结果重命名插件，可以应用在 `Application` 和 `Library` 中。

Integration
================

在项目的 `build.gradle` 中引用插件：

```gradle
dependencies {
    classpath 'com.android.tools.build:gradle:1.2.3'
    classpath 'market.dreamer.build.android:android-rename:0.0.2'
}
```

在模块的 `build.gradle` 中应用插件：

```gradle
apply plugin: 'com.android.application'
apply plugin: 'market.dreamer.android.rename'
```

Usage
================

**Basic Configuration**

```gradle
renameConfig {
    applicationName = "Test",
}
```

上面的配置将会得到输出结果 `Test-0.0.1-001-release.[apk/aar]` 。

**NOTE**
`renameConfig` 中能够使用的配置如下（以下所有配置皆可以定义为 `Closure` 形式，`Closure` 形式的定义将会取返回值作为结果）：

* `applicationName` ：应用的名称。`Closure` 形式时，没有传入参数。
* `versionName` ：版本名称。在 `Android` 配置中，可能出现在两个地方，一是 `defaultConfig` 中，二是 `productFlavors` 中。定义为 `Closure` 时，会将 `versionName` 作为参数传入，如果定义了 `productFlavors` 将会传入 `flavors中的配置，否则使用 `defaultConfig` 中的值。
* `versionCode` ：版本号。配置说明和 `versionName` 类似。
* `buildType` ：编译类型。定义为 `Closure` 时，传入参数为 `buildType` 。
* `flavors` ： `ProductFlavors` 。定义为 `Closure` 时，会将 `productFlavors` 列表作为参数传入。
* `divider` ：分割符。默认分割符为 `-` ，可以通过这个字段做修改，这个字段也可以定义为 `Closure` 。
* `includes` ：此配置定义最后的输出结果包含哪些部分，比如 `includes = [applicationName]` ，这时只能得到结果 `Test.[apk/aar]` ，而忽略掉了其他部分。除了以上给定的配置，也可以在 `includes` 中扩展自定义的 `Closure` 。
比如 `includes = [applicationName, {return new Date()}]` ，这时可以得到结果 `Test-20151017.[apk/aar]` 之类的结果。如果想要灵活的扩展，可以使用这个方式实现。

**More**

所有配置的默认值如下：

```gradle
renameConfig {
    applicationName = {}
    versionName = { versionName -> versionName }
    versionCode = { versionCode -> versionCode }
    buildType = { buildType -> buildType.name }
    flavors = { flavors -> flavors*.name.join(divider) }
    divider = { "-" }
    includes = [applicationName, versionName, versionCode, buildType, flavors];
}
```

如果想实现更深入的定制，可以按照以上配置进行自定义。

ChangeLog
================
0.0.4:

- Fix error building when clean

0.0.3:

- Fix zip align error

License
=======

The MIT License (MIT)

Copyright (c) 2015 GodJobs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.