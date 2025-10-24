// File: LoginActivity.kt (PERBAIKAN FINAL)
package com.raditya.smartwaterguard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raditya.smartwaterguard.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val PREFS_NAME = "user_session"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        // Dapatkan SharedPreferences
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Ambil kredensial yang tersimpan dari RegisterActivity
        // Default nilainya null jika belum pernah ada registrasi
        val savedEmail = prefs.getString("REGISTERED_EMAIL", null)
        val savedPassword = prefs.getString("REGISTERED_PASSWORD", null)

        // 1. Cek kekosongan input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan Password harus diisi.", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Cek apakah ada akun terdaftar (savedEmail != null)
        if (savedEmail == null) {
            // Ini terjadi jika user belum pernah menjalankan RegisterActivity dengan sukses.
            Toast.makeText(this, "Akun tidak ditemukan. Silakan buat akun baru.", Toast.LENGTH_LONG).show()
            return
        }

        // 3. Verifikasi Kredensial (Verifikasi Login yang sebenarnya)
        if (email == savedEmail && password == savedPassword) {

            // 🟢 LOGIN BERHASIL: Simpan status sesi dan pindah ke Dashboard
            prefs.edit().putBoolean("IS_LOGGED_IN", true).apply()

            Toast.makeText(this, "Login Berhasil! Selamat datang.", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, DashboardActivity::class.java))
            finish() // Tutup Login Activity

        } else {
            // 🔴 GAGAL: Email atau Password salah (tidak cocok dengan yang tersimpan)
            Toast.makeText(this, "Email atau password salah. Coba lagi.", Toast.LENGTH_LONG).show()
        }
    }
}