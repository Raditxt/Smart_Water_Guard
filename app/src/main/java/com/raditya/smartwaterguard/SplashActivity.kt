// File: SplashActivity.kt (Hanya Bridge ke Lottie Loading)
package com.raditya.smartwaterguard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class SplashActivity : AppCompatActivity() {

    // HAPUS: Variabel isContentReady dan MIN_BRANDING_DELAY_MS

    override fun onCreate(savedInstanceState: Bundle?) {
        // Tampilkan Splash Screen API
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // **TIDAK ADA DELAY ATAU LOGIKA SESI DI SINI.** // Langsung pindah ke Activity Lottie yang akan menangani loading sesungguhnya.

        startActivity(Intent(this, LottieLoadingActivity::class.java))
        finish()
    }
}