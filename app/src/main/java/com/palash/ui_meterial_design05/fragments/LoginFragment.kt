package com.palash.ui_meterial_design05.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.palash.ui_meterial_design05.R
import com.palash.ui_meterial_design05.databinding.FragmentLoginBinding
import com.palash.ui_meterial_design05.model.login.request.LoginRequest
import com.palash.ui_meterial_design05.utils.NetworkResult
import com.palash.ui_meterial_design05.utils.TokenManager
import com.palash.ui_meterial_design05.view_model.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val userName = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            loginViewModel.loginRequest(LoginRequest(password,userName))
            binding.progressBar.isVisible = true
            binding.btnLogin.isVisible = false
        }

        loginViewModel.loginResLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    //save token
                    tokenManager.saveToken(it.data!!.token)
                    //Log.d("Token", )
                    findNavController().navigate(R.id.action_loginFragment_to_dashFragment)
                }
                is NetworkResult.Error -> {
                    binding.btnLogin.isVisible = true
                    binding.txtError.text = it.message
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}