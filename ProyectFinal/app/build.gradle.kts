@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

android {
    namespace = "com.example.proyectfinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectfinal"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("com.google.ar.sceneform:filament-android:1.17.1")
    implementation("androidx.compose.ui:ui:1.0.3")
    implementation("androidx.compose.material:material:1.0.3")
    implementation("androidx.compose.material3:material3:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // VIEW MODEL
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // ICONOS
    implementation("androidx.compose.material:material-icons-extended:1.4.3")

    // NAVEGACIÓN
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    // DISEÑO ADAPTABLE
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")

    //PERMISOS
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")

    // ExoPlayer
    implementation("com.google.android.exoplayer:exoplayer:2.15.1")

    // Coil. Para cargar imagenes de manera asyncrona
    implementation("io.coil-kt:coil-compose:2.5.0")

    // BASE DE DATOS DE ROOM
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
}