package com.ephotos.photo.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ephotos.R
import com.ephotos.common.BaseFragment
import com.ephotos.common.noCrash
import com.ephotos.photo.data.remote.models.PhotoResponse
import com.ephotos.photo.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo.*

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
                setupListeners()
                observeOnViewModel()
            }
        }
    }

    private fun setupListeners() {
        btnGetPhoto.setOnClickListener {
            fetchRandomPhoto()
        }
    }

    private fun fetchRandomPhoto() {
        val randomId = (0..1000).random() // generated random from 0 to 1000 included
        viewModel.getPhotoInfo(randomId.toString())
    }

    private fun observeOnViewModel() = with(viewModel) {
        lifecycle.addObserver(this)

        photoResult.observe(viewLifecycleOwner, {
            showResults(it)
        })
    }

    private fun showResults(it: PhotoResponse) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(this).load(it.downloadUrl)
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(circularProgressDrawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(8)))
            .into(photoView)
    }

}
