plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt) // Dependency Injection için Hilt eklendi
    alias(libs.plugins.kotlin.kapt) // Kapt (Kotlin Annotation Processing) eklendi
}

android {
    namespace = "com.yargisoft.tuner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yargisoft.tuner"
        minSdk = 24
        targetSdk = 34
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
        // Java dil seviyesini ayarla
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        // JVM hedefini belirle (uyumlu bir JVM sürümü kullanılmalı)
        jvmTarget = "11"
    }

    buildFeatures {
        // ViewBinding etkinleştirildi
        viewBinding = true
    }
}

dependencies {
    // AndroidX kütüphaneleri
    implementation(libs.androidx.core.ktx) // Core KTX
    implementation(libs.androidx.appcompat) // AppCompat
    implementation(libs.material) // Material Design
    implementation(libs.androidx.activity) // Activity KTX
    implementation(libs.androidx.constraintlayout) // ConstraintLayout

    // Navigation Component (Navigasyon işlemleri için)
    implementation(libs.navigation.fragment.ktx) // Navigation Fragment
    implementation(libs.navigation.ui.ktx) // Navigation UI

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android) // Hilt Android
    kapt(libs.hilt.compiler) // Hilt Compiler

    // Jetpack ViewModel ve LiveData
    implementation(libs.lifecycle.viewmodel.ktx) // ViewModel
    implementation(libs.lifecycle.livedata.ktx) // LiveData

    // Room Database (İleri kullanım için)
    implementation(libs.room.runtime) // Room Runtime
    kapt(libs.room.compiler) // Room Compiler
    implementation(libs.room.ktx) // Room KTX

    // Glide (Görseller için, gerekirse)
    implementation(libs.glide) // Glide kütüphanesi
    kapt(libs.glide.compiler) // Glide Compiler

    // Test kütüphaneleri
    testImplementation(libs.junit) // JUnit
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit
    androidTestImplementation(libs.androidx.espresso.core) // Espresso Core
}
