plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android.plugin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.minthanhtike.minflix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.minthanhtike.minflix"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500/\"")
            buildConfigField(
                "String",
                "API_KEY",
                ""
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix =".debug"
            buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500/\"")
            buildConfigField(
                "String",
                "API_KEY",
                ""
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
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //jetpack navigation
    implementation(libs.androidx.navigation.compose)

    //constraint layout
    implementation(libs.constraintlayout)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.coil)
    implementation(libs.coil.svg)

    //ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.auth)

    //kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.hilt.compiler)

    // Compose Hilt Navigation
    implementation(libs.hilt.navigation.compose)

    //downloadable fonts
    implementation(libs.androidx.ui.text.google.fonts)

    //paging 3
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)

    //window size
    implementation(libs.androidx.windowsize)

    //adaptive navigation suite
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    //supporting pane scaffold
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.adaptive.navigation)


}