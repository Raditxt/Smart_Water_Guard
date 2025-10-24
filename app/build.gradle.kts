plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.raditya.smartwaterguard"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.raditya.smartwaterguard"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.ktx)
// 2. Lottie Animation
    implementation(libs.lottie)
// 3. MPAndroidChart (Grafik)
    implementation(libs.mpandroidchart)
// 5. Shimmer (Loading Feedback)
    implementation(libs.shimmer)
// 6. Glide (Images & Cards)
    implementation(libs.glide)
// 7. Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
// 9. ViewPager2 (Onboarding)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.gridlayout)
    implementation(libs.mpandroidchart)
    implementation(libs.androidx.lifecycle.runtime.ktx.v270)
    implementation(libs.androidx.core.ktx.v190)
}