package com.dolotdev.galleryapp.presentation.page

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dolotdev.galleryapp.R
import com.dolotdev.galleryapp.constant.RequestCode
import com.dolotdev.galleryapp.functional.PhotoService
import kotlinx.android.synthetic.main.fragment_gallery.*

class Gallery : Fragment() {

    private val viewModel by lazy { GalleryViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoService = PhotoService(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.photoService?.onActivityResult(requestCode, resultCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RequestCode.CAMERA_REQUEST_CODE) {
            if (isCameraPermissionGranted()) {
                viewModel.photoService?.startCameraIntent()
            }
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context ?: return false,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}