// Top of build.gradle (app)
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt) // Use the kapt plugin alias
}

android {
    namespace = "com.travelexperts.travelexpertsadmin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.travelexperts.travelexpertsadmin"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.navigation:navigation-compose:2.7.2")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:2.11.2")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")


    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ViewModel integration
    implementation(libs.hilt.navigation.compose) // Use the alias

    implementation("androidx.activity:activity-compose:1.7.2") // Required for Image Picker
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1") // Required for ViewModel
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.coroutines.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}

//configurations.all {
//    resolutionStrategy {
//        force(libs.javapoet) // Use the alias
//    }
//}