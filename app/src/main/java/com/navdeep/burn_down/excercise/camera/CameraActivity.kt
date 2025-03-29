package com.navdeep.burn_down.excercise.camera

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.ViewPager
import com.navdeep.burn_down.R
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var captureButton: ImageView
    private lateinit var shareButton: CardView
    private lateinit var viewFinder: PreviewView
    private lateinit var overlayView: View
    private lateinit var image_preview: ImageView
    private lateinit var viewPager : ViewPager
    private lateinit var frameLayout: FrameLayout
    private lateinit var flip_camera_iv: ImageView
    private lateinit var back_btn: ImageView
    private var camera: Camera? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // Start with rear camera
    private lateinit var cameraProvider: ProcessCameraProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MYTAG", "onCreate: "+"YESS")
        setContentView(R.layout.activity_camera_layout)

        initializeView()
        setOnClickListeners()
        startCamera()

        captureButton.setOnClickListener {
            captureImage()
        }

        shareButton.setOnClickListener {
            // Optionally add a button or trigger for taking a screenshot
            takeScreenshotAndShare(this@CameraActivity, frameLayout)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setOnClickListeners() {
        back_btn?.setOnClickListener {
            finish()
        }


        flip_camera_iv.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }
    }

    private fun initializeView() {
        captureButton = findViewById(R.id.captureButton)
        shareButton = findViewById(R.id.shareButton)
        viewFinder = findViewById(R.id.viewFinder)
        overlayView = findViewById(R.id.overlayView)
        image_preview = findViewById(R.id.image_preview)
        viewPager = findViewById(R.id.overlappingView)
        frameLayout = findViewById(R.id.frameLayout)
        flip_camera_iv = findViewById(R.id.flip_camera_iv)
        back_btn = findViewById(R.id.back_btn)

        initializeAdapter()

    }

    private fun takeScreenshotAndShare(cameraActivity: CameraActivity, frameLayout: FrameLayout) {
        val bitmap = Bitmap.createBitmap(frameLayout.width, frameLayout.height, Bitmap.Config.ARGB_8888)

        // Draw the entire FrameLayout onto the canvas
        val canvas = android.graphics.Canvas(bitmap)
        frameLayout.draw(canvas)

        // Save the bitmap and share it
        val savedUri = saveBitmap(cameraActivity, bitmap)
        if (savedUri != null) {
            shareToInstagram(cameraActivity, savedUri)
        }
        finishAffinity()
    }

    private fun captureView(view: View): Bitmap {
        // Create a bitmap from the view
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmap(activity: Activity, bitmap: Bitmap): Uri? {
        return try {
            // Save bitmap to external storage
            val directory = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(directory, "screenshot_framelayout.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Generate content:// URI with FileProvider
            FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun shareToInstagram(activity: Activity, imageUri: Uri) {
        val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(android.content.Intent.EXTRA_STREAM, imageUri)
            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant read permissions
            setPackage("com.instagram.android") // Target Instagram
        }
        activity.startActivity(android.content.Intent.createChooser(intent, "Share Image"))
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            // Unbind all use cases before rebinding
            cameraProvider.unbindAll()

            val preview = androidx.camera.core.Preview.Builder()
                .build()
            imageCapture = ImageCapture.Builder().build()
            preview.setSurfaceProvider(
                viewFinder.surfaceProvider
            )

            try {
                // Bind the camera selector (front/rear) and the use case
                camera = cameraProvider.bindToLifecycle(
                    this as LifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun initializeAdapter() {
        val imageUrls = arrayOf(R.drawable.im_1,R.drawable.im_2,
            R.drawable.im_3)

        val adapter = ImageAdapter(imageUrls)
        viewPager.adapter = adapter

    }

    private fun captureImage() {
        val photoFile = File(externalMediaDirs.firstOrNull(), "${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri

                    if (savedUri != null) {
                        try {
                            // Convert file URI to content URI
                            val file = File(savedUri.path ?: "")
                            val contentUri = FileProvider.getUriForFile(
                                this@CameraActivity,
                                "${applicationContext.packageName}.fileprovider",
                                file
                            )

                            image_preview.setImageURI(contentUri)
                            image_preview.visibility = View.VISIBLE
                            viewPager.visibility = View.VISIBLE
                            cameraExecutor.shutdown()
                            viewFinder.visibility = View.GONE
                            flip_camera_iv.visibility = View.GONE
                            shareButton.visibility = View.VISIBLE
                            captureButton.visibility = View.GONE

                            // Optionally show a toast
                            Log.d("TAG", "onImageSaved: "+"Image saved and previewed!")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@CameraActivity, "Failed to preview image", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("CameraActivity", "Failed to save photo.")
                        Toast.makeText(this@CameraActivity, "Photo save error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
