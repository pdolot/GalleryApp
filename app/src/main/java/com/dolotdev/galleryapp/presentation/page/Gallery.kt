package com.dolotdev.galleryapp.presentation.page

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolotdev.galleryapp.R
import com.dolotdev.galleryapp.constant.RequestCode
import com.dolotdev.galleryapp.data.PhotoRepository
import com.dolotdev.galleryapp.functional.PhotoService
import kotlinx.android.synthetic.main.fragment_gallery.*

class Gallery : Fragment() {

    private val viewModel by lazy { GalleryViewModel() }
    private val adapter by lazy { PhotoAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoService = PhotoService(this)
        viewModel.photoRepository = PhotoRepository(context)

        takePicture.setOnClickListener {
            if (isCameraPermissionGranted()) {
                viewModel.photoService?.startCameraIntent()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    RequestCode.CAMERA_REQUEST_CODE
                )
            }
        }

        setAdapter()

        viewModel.photos.observe(viewLifecycleOwner, Observer {
            emptyData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(it)
        })
    }

    private fun setAdapter() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@Gallery.adapter
        }
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