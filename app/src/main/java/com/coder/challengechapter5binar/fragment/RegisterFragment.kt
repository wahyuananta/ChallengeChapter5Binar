package com.coder.challengechapter5binar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter5binar.R
import com.coder.challengechapter5binar.databinding.FragmentRegisterBinding
import com.coder.challengechapter5binar.room.UserEntity
import com.coder.challengechapter5binar.room.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(requireContext())

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            val konfirmasiPassword = binding.etKonfirmasiPassword.text

            when {
                username.isNullOrEmpty() -> {
                    binding.ilUsername.error = getString(R.string.name_belum_diisi)
                }
                email.isNullOrEmpty() -> {
                    binding.ilEmail.error = getString(R.string.email_belum_diisi)
                }
                password.isNullOrEmpty() -> {
                    binding.ilPassword.error = getString(R.string.password_belum_diisi)
                }
                konfirmasiPassword.isNullOrEmpty() -> {
                    binding.ilKonfirmasiPassword.error = getString(R.string.konfirmasi_password_belum_diisi)
                }
                password.toString().lowercase() != konfirmasiPassword.toString().lowercase() -> {
                    binding.ilKonfirmasiPassword.error = getString(R.string.password_tidak_sama)
                }
                else -> {
                    val user = UserEntity(null, username.toString(), email.toString(), password.toString())

                    lifecycleScope.launch(Dispatchers.IO) {
                        val result= repository.addUser(user)
//                        val result= mDb?.repository()?.insertDataUser(dataUser)
                        activity?.runOnUiThread {
                            if (result != 0.toLong()) {
//                                val user = UserEntity(
//                                    null,
//                                    name.toString(),
//                                    email.toString(),
//                                    password.toString()
//                                )
                                Toast.makeText(activity, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
//                                val registerToLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
//                                findNavController().navigate(registerToLogin)
                            } else {
                                val snackbar = Snackbar.make(it,"Registrasi gagal, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                                snackbar.setAction("Oke") {
                                    snackbar.dismiss()
                                }
                                snackbar.show()
                            }
                        }
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}