import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

val appPropertiesFile = rootProject.file("app.properties")
val appProperties = Properties()

appPropertiesFile.inputStream().use { stream ->
    appProperties.load(stream)
}

android {
    namespace = "com.jogasoft.moviefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jogasoft.moviefinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 4
        versionName = "0.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "TMDB_API_KEY", "\"${appProperties.getProperty("TMDB_API_KEY", "")}\"")
        buildConfigField("String", "BASE_TMDB_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "BASE_TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p/\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // Retrofit & Moshi
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutine.test)
    testImplementation(libs.kotlin.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// Gradle unit test task config
tasks.withType<Test> {
    // prevents gradle task caching while running tests
    outputs.upToDateWhen{ false }

    if (name.contains("UnitTest")) {
        testLogging {
            events("passed", "skipped", "failed", "standardOut", "standardError")
            showStandardStreams = true
        }
    }
}