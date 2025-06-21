// app/build.gradle.kts
plugins {
    // подключаем плагины, версии берутся из classpath и settings.gradle.kts
    /*id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Kotlin KAPT для аннотаций джава-кодогенераторов
    //id("kotlin-kapt")
    // Hilt Gradle Plugin (версия берётся из buildscript classpath)
    //id("dagger.hilt.android.plugin")
    // Serialization плагин
    kotlin("plugin.serialization") version "1.9.0"

    // Compose (через version catalog)*/
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
  //  id("com.google.devtools.ksp")
   // id("dagger.hilt.android.plugin")

}

android {
    namespace = "com.lsimanenka.financetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lsimanenka.financetracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // ===== Hilt =====
    // implementation("com.google.dagger:hilt-android:2.48")
    // kapt        ("com.google.dagger:hilt-compiler:2.48")

    // Hilt + Navigation для Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // ViewModel + Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ===== Networking =====
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // ===== UI / Compose =====
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("com.airbnb.android:lottie-compose:6.0.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Core / Activity
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.7.0")

    // Room (если понадобится)
    // implementation("androidx.room:room-runtime:2.5.1")
    //  kapt        ("androidx.room:room-compiler:2.5.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
