package com.bangkit.pastiinaja.ui.add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bangkit.pastiinaja.databinding.FragmentAddImageBinding
import com.bangkit.pastiinaja.getImageUri
import com.bangkit.pastiinaja.ui.ViewModelFactory
import com.bangkit.pastiinaja.ui.main.MainActivity
import com.bangkit.pastiinaja.ui.result.ResultActivity
import java.io.ByteArrayOutputStream

class AddImageFragment : Fragment() {

    private val viewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var binding: FragmentAddImageBinding

    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireActivity(), "Izin diberikan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireActivity(), "Izin tidak diberikan", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d(TAG, "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(requireActivity(), REQUIRED_PERMISSION) == PackageManager.PERMISSION_GRANTED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.buttonInputCamera.setOnClickListener {
            startCamera()
        }

        binding.buttonInputGallery.setOnClickListener {
            startGallery()
        }

        binding.buttonCheck.setOnClickListener {
            currentImageUri?.let {
                // convert image to base64 string
                val byteArrayOutputStream = ByteArrayOutputStream()
                val bitmap = (binding.ivPreview.drawable as BitmapDrawable).bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
                val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)

                viewModel.getSession().observe(viewLifecycleOwner) { userId ->
                    viewModel.postFraudByImage(userId, imageString) { isSuccess ->
                        if (isSuccess) {
                            val fraudId = viewModel.fraudId.value
                            val intentToResult = Intent(requireContext(), ResultActivity::class.java)
                            intentToResult.putExtra("DATA", fraudId)
                            startActivity(intentToResult)
                        } else {
                            Log.e(TAG, "Failed to post fraud by image")
                        }
                    }
                }

            } ?: Toast.makeText(requireContext(), "Gagal mengunggah", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d(TAG, "showImage: $it")
            binding.ivPreview.setImageURI(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val TAG = "AddImageFragment"

        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}