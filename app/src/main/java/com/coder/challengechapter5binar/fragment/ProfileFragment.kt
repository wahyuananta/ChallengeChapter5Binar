package com.coder.challengechapter5binar.fragment

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.coder.challengechapter5binar.R
import com.coder.challengechapter5binar.databinding.FragmentProfileBinding
import com.coder.challengechapter5binar.fragment.LoginFragment.Companion.LOGINUSERNAME
import com.coder.challengechapter5binar.room.UserEntity
import com.coder.challengechapter5binar.room.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: UserRepository
    private val args : ProfileFragmentArgs by navArgs()
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireContext().getSharedPreferences(LOGINUSERNAME, Context.MODE_PRIVATE)
        repository = UserRepository(requireContext())

        binding.etUsername.setText(args.dataUser.username)
        binding.etUsername.isFocusable = false
        binding.etEmail.setText(args.dataUser.email)
        binding.etPassword.setText(args.dataUser.password)

        binding.btnUpdate.setOnClickListener {
            val user = UserEntity(
                args.dataUser.id,
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                val result = repository.updateUser(user)
                runBlocking(Dispatchers.Main) {
                    if (result != 0){
                        Toast.makeText(requireContext(), "Profile berhasil diupdate", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Profile gagal diupdate", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    preferences.edit().clear().apply()
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                .setNegativeButton("Batal"){ dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }

}