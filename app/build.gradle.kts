plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "app.demo.Vcalls"
    compileSdk = 36

    defaultConfig {
        applicationId = "app.demo.Vcalls"
        minSdk = 27
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    //Zegocloud
    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")
    implementation("com.guolindev.permissionx:permissionx:1.8.1")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // AppCompat (needed for Zego UIKit)
    implementation("androidx.appcompat:appcompat:1.7.1")

    // Core Supabase client
    implementation("io.github.jan-tennert.supabase:supabase-kt:3.2.2")

// Auth
    implementation("io.github.jan-tennert.supabase:auth-kt:3.2.2")


// Postgres REST API
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.2.2")

// Realtime (live updates, presence, subscriptions)
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.4.0")

// Storage (file uploads / downloads)
    implementation("io.github.jan-tennert.supabase:storage-kt:3.2.2")


    // Koin Core
    implementation("io.insert-koin:koin-core:4.1.1")

// Koin Android
    implementation("io.insert-koin:koin-android:4.1.1")

// Koin for Jetpack Compose (use this instead of viewmodel)
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")


    // Ktor
    implementation("io.ktor:ktor-client-core:3.3.0")

    // Ktor client for Android
    implementation("io.ktor:ktor-client-okhttp:3.3.0")

    // Ktor client serialization (Kotlinx)
    implementation("io.ktor:ktor-client-content-negotiation:3.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")

    // Logging (optional)
    implementation("io.ktor:ktor-client-logging:3.3.0")



    //Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}