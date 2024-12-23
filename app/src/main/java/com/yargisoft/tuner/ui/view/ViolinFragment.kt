package com.yargisoft.tuner.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yargisoft.tuner.databinding.FragmentViolinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViolinFragment : Fragment() {
    private var _binding: FragmentViolinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViolinBinding.inflate(layoutInflater)
        return binding.root
    }

}