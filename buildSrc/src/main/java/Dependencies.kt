object Android {
    const val appId = "com.sdimosikvip.eazystock"
    const val compileSdk = 31
    const val buildTools = "30.0.3"
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"
}

object Androidx {
    private const val core_ktx_version = "1.7.0"
    private const val app_compat_version = "1.4.1"
    private const val lifecycle_viewmodel_ktx_version = "2.4.0"
    private const val lifecycle_livedata_ktx_version = "2.4.0"
    private const val constraintlayout_version = "2.1.3"
    private const val navigation_component = "2.4.0"

    const val core_ktx = "androidx.core:core-ktx:$core_ktx_version"
    const val app_compat = "androidx.appcompat:appcompat:$app_compat_version"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_viewmodel_ktx_version"
    const val lifecycle_livedata_ktx =
        "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_livedata_ktx_version"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:$navigation_component"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:$navigation_component"

}

object Build {
    private const val android_build_tools_version = "7.1.0"

    const val android_build_tools = "com.android.tools.build:gradle:$android_build_tools_version"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val hilt_build_plugin = "com.google.dagger:hilt-android-gradle-plugin:${DI.version}"
}


object AdapterDelegate {
    private const val version = "4.3.1"

    const val dsl = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$version"
    const val viewbinding = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$version"
}

object DI {
    const val version = "2.40.5"

    const val hilt = "com.google.dagger:hilt-android:$version"
    const val hitl_android_compiler = "com.google.dagger:hilt-android-compiler:$version"
    const val hilt_lifecycle_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
}

object Extensions {

    // To use only without reflection variants of viewBinding
    private const val view_binding_property_delegate_version = "1.5.3"
    const val view_binding_property_delegate =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$view_binding_property_delegate_version"
}

object Google {
    private const val materialVersion = "1.5.0"
    const val material = "com.google.android.material:material:$materialVersion"
}

object Kotlin {
    const val version = "1.6.10"
}

object Modules {
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
}

object Shimmer {
    private const val facebook_version = "0.5.0"
    private const val shimmer_recyclerview_version = "0.4.1"

    const val facebook_shimmer = "com.facebook.shimmer:shimmer:$facebook_version"
    const val shimmer_recyclerview =
        "com.todkars:shimmer-recyclerview:$shimmer_recyclerview_version"
}

object UITest {
    private const val espresso_version = "3.4.0"
    const val espresso = "androidx.test.espresso:espresso-core:$espresso_version"
}

object UnitTest {
    private const val junit_version = "4.12"
    private const val junit_ext_version = "1.1.3"

    const val junit4 = "junit:junit:$junit_version"
    const val junit_ext = "androidx.test.ext:junit:$junit_ext_version"
}