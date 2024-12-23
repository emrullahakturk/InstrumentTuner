package com.yargisoft.tuner.ui.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yargisoft.tuner.core.utils.PermissionUtils
import com.yargisoft.tuner.databinding.FragmentMainBinding
import com.yargisoft.tuner.ui.viewmodel.TunerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val tunerViewModel: TunerViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // İzin verilmiş, işlemleri başlatabilirsiniz
                tunerViewModel.startTuning()
            } else {
                // İzin reddedilmiş, kullanıcıya mesaj gösterin
                Toast.makeText(requireContext(), "Microphone permission denied!", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel'deki LiveData'ları gözlemle
        tunerViewModel.currentFrequency.observe(viewLifecycleOwner, Observer { frequency ->
            binding.tvMessage.text = "Frequency: ${"%.2f".format(frequency)} Hz"
        })

        tunerViewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            binding.tvNote.text = "Note: $note"
        })

        try {
            if (PermissionUtils.isPermissionGranted(this, Manifest.permission.RECORD_AUDIO)) {
                tunerViewModel.startTuning()
            } else {
                PermissionUtils.requestPermission(requestPermissionLauncher, Manifest.permission.RECORD_AUDIO)
            }
        } catch (e: IllegalStateException) {
            Toast.makeText(requireContext(), "AudioRecord initialization failed: ${e.message}", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tunerViewModel.stopTuning()
    }
}
