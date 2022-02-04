plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "$project.rootDir/tools/proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))

    implementation(Androidx.app_compat)
    implementation(Androidx.constraintlayout)
    implementation(Androidx.core_ktx)
    implementation(Androidx.lifecycle_livedata_ktx)
    implementation(Androidx.lifecycle_viewmodel_ktx)
    implementation(Androidx.navigation_fragment_ktx)
    implementation(Androidx.navigation_ui_ktx)

    implementation(Google.material)

    implementation(DI.hilt)
    kapt(DI.hitl_android_compiler)

    implementation(Extensions.view_binding_property_delegate)

    implementation(Shimmer.facebook_shimmer)
    implementation(Shimmer.shimmer_recyclerview)

    implementation(AdapterDelegate.dsl)
    implementation(AdapterDelegate.viewbinding)

    androidTestImplementation(UnitTest.junit4)
    androidTestImplementation(UnitTest.junit_ext)

    androidTestImplementation(UITest.espresso)
}