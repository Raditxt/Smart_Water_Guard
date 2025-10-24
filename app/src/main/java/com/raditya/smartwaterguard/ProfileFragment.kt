// File: ProfileFragment.kt (Final)
package com.raditya.smartwaterguard

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.raditya.smartwaterguard.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val PREFS_NAME = "user_session"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfileData()

        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun loadProfileData() {
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Ambil data yang disimpan saat register
        val userName = prefs.getString("REGISTERED_NAME", "Pengguna SmartGuard")
        val userEmail = prefs.getString("REGISTERED_EMAIL", "emailtidakditemukan@app.com")

        // Tampilkan data Profil (Card Akun)
        binding.tvGreeting.text = getString(R.string.user_greeting, userName)
        binding.tvUserEmail.text = userEmail

        // Tampilkan data Aplikasi (Card Info)
        binding.tvVersion.text = "Versi 1.0 (Build 1)" // Tetap manual

        // PENTING: Mengisi detail kreator dengan ID dan String yang baru
        // ID di layout baru adalah tv_creator_detail, dan stringnya adalah creator_name_detail
        binding.tvCreatorDetail.text = getString(R.string.creator_name_detail)
    }

    private fun performLogout() {
        // Hapus status login
        val prefs = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        prefs.edit().putBoolean("IS_LOGGED_IN", false).apply()

        Toast.makeText(requireContext(), "Anda telah keluar dari akun.", Toast.LENGTH_SHORT).show()

        // Navigasi ke LoginActivity dan bersihkan stack
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}