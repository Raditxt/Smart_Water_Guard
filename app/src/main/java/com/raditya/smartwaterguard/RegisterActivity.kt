// File: RegisterActivity.kt (PERBAIKAN)
package com.raditya.smartwaterguard

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raditya.smartwaterguard.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val PREFS_NAME = "user_session"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            performRegister()
        }

        binding.tvGoToLogin.setOnClickListener {
            finish()
        }
    }

    private fun performRegister() {
        // Ambil SEMUA INPUT dan terapkan .trim() untuk menghilangkan spasi
        val name = binding.etName.text.toString().trim() // <-- Perbaiki: Ambil dari etName
        val email = binding.etEmail.text.toString().trim() // <-- Perbaiki: Variabel yang benar
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        // 1. Cek semua field harus diisi
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi.", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Cek kesamaan password
        if (password != confirmPassword) {
            Toast.makeText(this, "Password dan Konfirmasi Password harus sama.", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Cek panjang password
        if (password.length < 6) {
            Toast.makeText(this, "Password minimal 6 karakter.", Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Cek format email (Dummy)
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Format email tidak valid.", Toast.LENGTH_SHORT).show()
            return
        }

        // 🟢 Registrasi Berhasil (Dummy)
        Toast.makeText(this, "Pendaftaran berhasil! Silakan Login.", Toast.LENGTH_LONG).show()

        // **[LOGIKA: SIMPAN DATA PROFIL]**
        // Simpan Nama, Email, dan PASSWORD ke SharedPreferences
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("REGISTERED_NAME", name)
            putString("REGISTERED_EMAIL", email)
            // PENTING: Simpan PASSWORD agar bisa diverifikasi di LoginActivity
            putString("REGISTERED_PASSWORD", password)
            apply()
        }

        // Pindah kembali ke Login Activity
        finish()
    }
}