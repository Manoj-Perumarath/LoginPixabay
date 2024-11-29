package com.example.loginpixabay.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.loginpixabay.databinding.FragmentImageDetailBinding
import com.example.loginpixabay.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailBinding
    private val viewModel: HomeViewModel by viewModels()
    private val imageDataArgs: ImageDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        imageDataArgs.imageItem?.let { viewModel.setImageDetail(imageDataArgs.imageItem!!) }
        return binding.root
    }

}