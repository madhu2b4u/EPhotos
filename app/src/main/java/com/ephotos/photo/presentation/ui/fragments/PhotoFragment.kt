package com.ephotos.photo.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ephotos.R
import com.ephotos.common.BaseFragment
import com.ephotos.common.noCrash
import com.ephotos.photo.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoFragment : BaseFragment() {

    private val viewModel: PhotoViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_photo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            noCrash {

            }
        }
    }
}
