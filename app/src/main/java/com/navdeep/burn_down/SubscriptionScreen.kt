package com.navdeep.burn_down

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.billingclient.api.*
import com.navdeep.burn_down.dashboard.Dashboard
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class SubscriptionScreen : Activity() , PurchasesUpdatedListener {

    private val TAG = javaClass.name

    private lateinit var viewPager: ViewPager2
    private lateinit var monthly: ImageView
    private lateinit var all_time: ImageView
    private lateinit var skip_tv: TextView
    private lateinit var desc_tv: TextView


    private val allTimePurchase = "com.burnout.buy"; //inApp
    private val monthlyPurchase = "com.burnout.monthly"; //subscription
    lateinit var sku_monhtly : SkuDetails
    lateinit var sku_allTime : SkuDetails
    var screenName : String = ""

    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription_screen)

        if (intent.extras != null) {
            screenName = intent.getStringExtra("screen")!!
        }

        if (!Utility.isInternetAvailable(this)) {
            // Handle no internet connection
            return
        }

        setupBillingClient()

        viewPager = findViewById(R.id.viewPager)
        monthly = findViewById(R.id.monthly_iv)
        all_time = findViewById(R.id.all_time_iv)
        skip_tv = findViewById(R.id.skip_tv)
        desc_tv = findViewById(R.id.desc_tv)

        if (!TextUtils.isEmpty((screenName))) {
            desc_tv.visibility = View.GONE
            skip_tv.visibility = View.GONE
        }

        val images = listOf(
            R.drawable.workout_plan,
            R.drawable.yoga_plan,
            R.drawable.nut_plan,
            R.drawable.bmi
        )
        val label = listOf(
            R.string.workout_label,
            R.string.yoga_label,
            R.string.nut_plan,
            R.string.bmi
        )

        val adapter = ImageAdapter(images,label)
        viewPager.adapter = adapter
        // Center the images
        viewPager.offscreenPageLimit = 1
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val dotsIndicator: DotsIndicator = findViewById(R.id.dotsIndicator)
        dotsIndicator.setViewPager2(viewPager)


        all_time?.setOnClickListener {
            initiateAllTimePurchase()
        }

        monthly?.setOnClickListener {
            initiateMontlySubscriptionPurchase()
        }

        skip_tv?.setOnClickListener {
            startActivity(Intent(this , Dashboard :: class.java))
            finish()
        }

    }

    private fun initiateMontlySubscriptionPurchase() {
        val skuDetails = SkuDetails(sku_monhtly.originalJson)
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()

        billingClient.launchBillingFlow(this, billingFlowParams)
    }

    private fun initiateAllTimePurchase() {
        val skuDetails = SkuDetails(sku_allTime.originalJson)
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setSkuDetails(skuDetails)
            .build()

        billingClient.launchBillingFlow(this, billingFlowParams)
    }

    override fun onResume() {
        super.onResume()
        if (!Utility.isInternetAvailable(this)) {
            // Handle no internet connection
            return
        }
    }

    private class ImageAdapter(private val images: List<Int>, private val label: List<Int>) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_layout, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.imageView.setImageResource(images[position])
            holder.label_tv.setText(label[position])
        }

        override fun getItemCount(): Int {
            return images.size
        }

        class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val label_tv: TextView = itemView.findViewById(R.id.label_tv)
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult,
                                    purchases: MutableList<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            // Process the purchased item(s)
            purchases.forEach { purchase ->
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle user cancellation
        } else {
            // Handle other errors
        }
    }


    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        //connectToBillingService
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                    querySkuDetailsForMonthlySubscription()

                    querySkuDetailsForInApp()
                    // Start your purchase flow here
                } else {
                    // Handle billing setup failure
                    Log.d(TAG, "onBillingSetupFinished: "+"failed")
                }
            }

            override fun onBillingServiceDisconnected() {
                // Handle billing service disconnection
                Log.d(TAG, "onBillingServiceDisconnected: "+"No Google services availaable")
            }
        })
    }


    private fun querySkuDetailsForInApp() {
        val skuList = listOf(allTimePurchase)
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.INAPP)
            .build()

        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Process the result
                skuDetailsList?.forEach { skuDetails ->
                    sku_allTime = skuDetails
                    val price = skuDetails.price
                    // Handle the SKU details as needed
                }
            } else {
                // Handle query SKU details failure
            }
        }
    }

    private fun querySkuDetailsForMonthlySubscription() {
        val skuList = listOf(monthlyPurchase)
        val params = SkuDetailsParams.newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.SUBS)
            .build()

        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                // Process the result
                skuDetailsList?.forEach { skuDetails ->
                    sku_monhtly = skuDetails
                    val price = skuDetails.price
                    // Handle the SKU details as needed
                }
            } else {
                // Handle query SKU details failure
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        val sku = purchase
        val purchaseToken = purchase.purchaseToken
        val purchaseTime = purchase.purchaseTime
        val orderId = purchase.orderId
        val isAutoRenewing = purchase.isAutoRenewing
        val developerPayload = purchase.developerPayload
        val signature = purchase.signature

        // Example: Logging purchase details
        Log.d(TAG, "Purchase Details:")
        Log.d(TAG, "SKU: $sku")
        Log.d(TAG, "Purchase Token: $purchaseToken")
        Log.d(TAG, "Purchase Time: $purchaseTime")
        Log.d(TAG, "Order ID: $orderId")
        Log.d(TAG, "Auto-Renewing: $isAutoRenewing")
        Log.d(TAG, "Developer Payload: $developerPayload")
        Log.d(TAG, "Signature: $signature")

        // Example: Grant the purchased item to the user
        grantItemToUser(sku)
    }

    private fun grantItemToUser(sku: Purchase) {
        // Logic to grant the purchased item to the user
        // This could involve unlocking premium features, removing ads, etc.
        Log.d(TAG, "Granting item to user: $sku")
        val intent = Intent(Utility.ACTION_SUBSCRIPTION_PURCHASED)
// Add any additional data to the intent if needed
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        billingClient.endConnection()
    }

}