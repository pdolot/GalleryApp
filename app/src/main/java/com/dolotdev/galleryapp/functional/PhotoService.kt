package com.dolotdev.galleryapp.functional

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.dolotdev.galleryapp.constant.Constant
import com.dolotdev.galleryapp.constant.RequestCode
import com.dolotdev.galleryapp.util.FileUtil.createImageFile

class PhotoService(private val owner: Fragment) {

    private var context: Context? = owner.context
    private var photoUri: Uri? = null
    var onPictureTaken: (Uri?) -> Unit = {}

    fun startCameraIntent() {
        context?.let { context ->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(context.packageManager)?.also {
                    createImageFile(context)?.also { file ->

                        photoUri = FileProvider.getUriForFile(
                            context,
                            Constant.FILE_PROVIDER_AUTH,
                            file
                        )

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                        owner.startActivityForResult(
                            takePictureIntent,
                            RequestCode.CAMERA_REQUEST_CODE
                        )
                    }
                }
            }
        }

    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCode.CAMERA_REQUEST_CODE -> onPictureTaken(photoUri)
            }
        }
    }
}