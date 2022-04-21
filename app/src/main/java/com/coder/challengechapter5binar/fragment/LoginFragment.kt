package com.coder.challengechapter5binar.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter5binar.R
import com.coder.challengechapter5binar.databinding.FragmentLoginBinding
import com.coder.challengechapter5binar.room.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository

    companion object {
        const val LOGINUSERNAME = "login_username"
        const val USER = "user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(requireContext())

        val preferences = this.activity?.getSharedPreferences(LOGINUSERNAME, Context.MODE_PRIVATE)
        if (preferences!!.getString(USER, null) != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val etUsername = binding.etUsername.text
            val etPassword = binding.etPassword.text

            when {
                etUsername.isNullOrEmpty() -> {
                    binding.ilUsername.error = getString(R.string.email_belum_diisi)
                }
                etPassword.isNullOrEmpty() -> {
                    binding.ilPassword.error = getString(R.string.password_belum_diisi)
                }
                else -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = repository.checkUser(etUsername.toString(), etPassword.toString())

                        activity?.runOnUiThread {
                            if (result == false) {
                                val snackbar = Snackbar.make(it,"Login gagal, coba periksa email atau password anda", Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
                            } else {
                                val editor : SharedPreferences.Editor = preferences.edit()
                                editor.putString(USER, etUsername.toString())
                                editor.apply()
                                Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}