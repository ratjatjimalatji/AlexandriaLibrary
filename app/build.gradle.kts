plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    //id("kotlin-kapt")
    //id("dagger.hilt.android.plugin")
    //alias(libs.plugins.android.application)
    //alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.example.libraryreader"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.libraryreader"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

//    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.10.0"))
    implementation(libs.firebase.auth)
    implementation("com.google.firebase:firebase-firestore:26.1.1")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.4")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")

//
//    // Dagger - Hilt
    //kapt(libs.hilt.android)
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.3.0")
    //ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

//
////    // Networking
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.gson)
//    implementation(libs.okhttp)
////
//    // Coroutines
//    implementation("org.jetbrains.kotlin:kotlinx-coroutines-core:1.10.1")
//    implementation("org.jetbrains.kotlin:kotlinx-coroutines-android:1.10.1")
//    implementation("org.jetbrains.kotlin:kotlinx-coroutines-play-services:1.10.1")
//
//    //Coil
    implementation("io.coil-kt:coil-compose:2.7.0")
//
//    // Image Loading (Coil)
//    implementation(libs.coil.compose)
//    implementation(libs.coil.network)
//
//    // UI & Icons
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.material)
////
////    // Core & Lifecycle
//    implementation(libs.androidx.core.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.4.0")
//    implementation(libs.androidx.appcompat)
//
//    // Testing
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}