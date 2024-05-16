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
import com.palash.ui_meterial_design05.databinding.FragmentIntroBinding
import com.palash.ui_meterial_design05.databinding.FragmentRegistrationBinding
import com.palash.ui_meterial_design05.model.signup.request.SignupRequest
import com.palash.ui_meterial_design05.utils.NetworkResult
import com.palash.ui_meterial_design05.view_model.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegistration.setOnClickListener {

            binding.btnRegistration.isVisible = false
            binding.progressBar.isVisible = true
            loginViewModel.signupRequest(SignupRequest(30, "Dilip", "Pal"))
        }

        loginViewModel.signupResLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            binding.txtError.isVisible = false
            when(it){
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    Log.d("UserId", it.data!!.id.toString())
                    findNavController().navigate(R.id.action_registrationFragment_to_dashFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.isVisible = true
                    binding.txtError.text=it.message
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}