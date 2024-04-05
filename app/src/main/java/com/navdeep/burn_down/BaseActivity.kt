import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.*
import com.navdeep.burn_down.R
import com.navdeep.burn_down.Utility
import com.navdeep.burn_down.Utility.isInternetConnected
import com.navdeep.burn_down.Utility.saveToSharedPreferences
import com.navdeep.burn_down.Utility.showSubscriptionDialog
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private lateinit var billingClient: BillingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isInternetConnected(this)) {
            saveToSharedPreferences(this, "isInAppProductPurchased", false)
            saveToSharedPreferences(this, "isActiveSubscription", false)
            showSubscriptionDialog(this, getString(R.string.no_internet), "HOME")
            return
        }

        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                }
            }.build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryPurchases()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun queryPurchases() {
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handlePurchase(purchase)
                }
            }
        }

        billingClient.queryPurchasesAsync(BillingClient.SkuType.INAPP) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    handleInAppPurchase(purchase)
                }
            }
        }
    }

    private fun handleInAppPurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            saveToSharedPreferences(this, "isInAppProductPurchased", true)
        } else{
            saveToSharedPreferences(this, "isInAppProductPurchased", false)
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val purchaseTime = purchase.purchaseTime
            val currentTimeMillis = System.currentTimeMillis()
            val purchaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(purchaseTime))
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(currentTimeMillis))
            var isActivesubscription = Utility.isTrialPeriodAvailable(currentDate , purchaseDate)

            saveToSharedPreferences(this, "isActiveSubscription", isActivesubscription)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billingClient != null) {
            billingClient.endConnection()
        }
    }
}
