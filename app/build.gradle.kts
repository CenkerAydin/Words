plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.cenkeraydin.words"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cenkeraydin.words"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(libs.androidx.runtime.livedata)

    //Firebase
    implementation(libs.firebase.bom)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Hilt
    implementation (libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt (libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android.v242)
    kapt (libs.hilt.android.compiler.v242)

    //Room
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //Navigation
    implementation (libs.androidx.navigation.compose)

    implementation (libs.gson) // Gson bağımlılığı

    implementation (libs.androidx.material)

    //Lottie
    implementation (libs.lottie.compose)



}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
