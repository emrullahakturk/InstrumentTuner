package com.yargisoft.tuner.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yargisoft.tuner.databinding.FragmentViolinBinding
import com.yargisoft.tuner.ui.viewmodel.ViolinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViolinFragment : Fragment() {

    private var _binding: FragmentViolinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViolinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViolinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentFrequency.observe(viewLifecycleOwner, Observer { frequency ->
            binding.tvMessage.text = "Frequency: ${"%.2f".format(frequency)} Hz"
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            binding.tvNote.text = "Note: $note"
        })

        viewModel.startTuning()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.stopTuning()
    }
}
