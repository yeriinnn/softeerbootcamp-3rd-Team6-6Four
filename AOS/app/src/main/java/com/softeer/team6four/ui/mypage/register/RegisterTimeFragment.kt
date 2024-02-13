package com.softeer.team6four.ui.mypage.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softeer.team6four.R
import com.softeer.team6four.databinding.FragmentRegisterTimeBinding

class RegisterTimeFragment : Fragment() {
    private var _binding: FragmentRegisterTimeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            UploadDialogFragment() { findNavController().navigate(R.id.action_registerTimeFragment_to_registerCompleteFragment) }.show(
                childFragmentManager,
                "TAG"
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}