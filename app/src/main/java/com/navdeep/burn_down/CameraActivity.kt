package com.navdeep.burn_down


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.AspectRatio.Ratio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.viewpager.widget.ViewPager
import com.navdeep.burn_down.R
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraExecutor = Executors.newSingleThreadExecutor()

        val previewView: PreviewView = findViewById(R.id.previewView)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val aspectRatio = Rational(previewView.width, previewView.height)
            val preview =  Preview.Builder()
                .setTargetAspectRatio(aspectRatio.toInt())
//                .setTargetRotation(previewView.display.)
                .build()
                .apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this))

        val buttonShare: CardView = findViewById(R.id.buttonShare)
        buttonShare.setOnClickListener {
            takePhotoAndShare()
        }

        setAdapter()
    }

    private fun setAdapter() {
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val images = listOf(R.drawable.overlay_1, R.drawable.overlay_1, R.drawable.overlay_1)
        val adapter = ImagePagerAdapter(this, images)
        viewPager.adapter = adapter
    }

    private fun takePhotoAndShare() {
        val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputFileOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = FileProvider.getUriForFile(
                        this@CameraActivity,
                        "com.navdeep.burn_down.fileprovider",
                        file
                    )

                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        setPackage("com.instagram.android") // Package name for Instagram
                    }

                    try {
                        startActivity(shareIntent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            this@CameraActivity,
                            "Instagram not installed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                    Toast.makeText(
                        this@CameraActivity,
                        "Photo capture failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun overlayBitmaps(bitmap: Bitmap, overlayBitmap: Bitmap): Bitmap {
        val result = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(result)
        val paint = Paint(Paint.FILTER_BITMAP_FLAG)

        // Draw the original bitmap
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        // Draw the overlay bitmap
        canvas.drawBitmap(overlayBitmap, 0f, 0f, paint)

        return result
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}
