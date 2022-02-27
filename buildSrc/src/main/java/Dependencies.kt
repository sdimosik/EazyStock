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
    private const val swipe_to_refresh_layout_version = "1.1.0"

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
    const val swipe_to_refresh_layout =
        "androidx.swiperefreshlayout:swiperefreshlayout:$swipe_to_refresh_layout_version"

}

object Build {
    private const val android_build_tools_version = "7.1.0"

    const val android_build_tools = "com.android.tools.build:gradle:$android_build_tools_version"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
}


object AdapterDelegate {
    private const val version = "4.3.1"

    const val dsl = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$version"
    const val viewbinding = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$version"
}

object DI {
    const val version = "2.40.5"

    const val dagger = "com.google.dagger:dagger:$version"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:$version"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:$version"
    const val dagger_android_processor = "com.google.dagger:dagger-android-processor:$version"
}

object Extensions {

    // To use only without reflection variants of viewBinding
    private const val view_binding_property_delegate_version = "1.5.3"
    const val view_binding_property_delegate =
        "com.github.kirich1409:viewbindingpropertydelegate-noreflection:$view_binding_property_delegate_version"
}

object Network {
    private const val version_retrofit = "2.8.0"
    private const val version_okhttp = "5.0.0-alpha.4"

    const val retrofit = "com.squareup.retrofit2:retrofit:$version_retrofit"
    const val retrofit_converter = "com.squareup.retrofit2:converter-gson:$version_retrofit"
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:$version_okhttp"
}

object DB {
    private const val version = "2.4.1"

    const val room = "androidx.room:room-runtime:$version"
    const val room_ktx = "androidx.room:room-ktx:$version"
    const val room_compiler = "androidx.room:room-compiler:$version"
}

object Multithreading {
    private const val coroutines_version = "1.6.0"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
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

object Images {
    private const val glide_version = "4.13.0"
    private const val lottie_anim_version = "4.2.2"

    const val glide = "com.github.bumptech.glide:glide:$glide_version"
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glide_version"

    const val lottie_anim = "com.airbnb.android:lottie:$lottie_anim_version"
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

object Other {
    private const val alert_version = "7.2.4"
    private const val toast_version = "1.5.2"
    private const val timber_version = "5.0.1"
    private const val threetenabp_version = "1.3.1"

    const val alert = "com.github.tapadoo:alerter:$alert_version"
    const val toast = "com.github.GrenderG:Toasty:$toast_version"
    const val timber = "com.jakewharton.timber:timber:$timber_version"
    const val threetenabp = "com.jakewharton.threetenabp:threetenabp:$threetenabp_version"
}