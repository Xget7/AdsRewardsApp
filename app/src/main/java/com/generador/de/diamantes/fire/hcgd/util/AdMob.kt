package com.generador.de.diamantes.fire.hcgd.util


import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.generador.de.diamantes.fire.hcgd.R
import com.generador.de.diamantes.fire.hcgd.util.Common.Companion.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.lang.Exception

var mInterstitialAd: InterstitialAd? = null
var mRewardedAd: RewardedAd? = null
var TAG = "AdmobRewardVideo"


     fun loadRewardedAd(context: Context) {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, context.getString(R.string.viedeoReward_ad_unit_id), adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad wasn't loaded")
                mRewardedAd = null
            }
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd

        }
        })
     }

    fun showRewardAd(context: Context)  {
        try {

            val rewardAmount = mutableStateOf(20)
            if (mRewardedAd != null) {
                val activity = context.findActivity()
                mRewardedAd!!.show(activity) { rewardItem -> // Handle the reward.
                    Log.d(TAG, "The user earned the reward.")
                    rewardAmount.value = rewardItem.amount

                }
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
                loadRewardedAd(context)
            }
        }catch (e : Exception){

        }
    }



//load the interstitial ad
fun loadInterstitial(context: Context) {
    InterstitialAd.load(
        context,
        context.getString(R.string.inertialAd),
        AdRequest.Builder().build(),
        object :InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("Admob", "onAdLoaded: Ad was loaded.")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                mInterstitialAd = null
                Log.d("Admob", "onAdFailedToLoad: ${loadAdError.message}")
            }
        }
    )
}

fun addInterstitialCallbacks(context: Context) {
    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            Log.d("Admob", "onAdDismissedFullScreenContent: Ad was dismissed")
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            Log.d("Admob", "onAdFailedToShowFullScreenContent: Ad failed to show")
        }

        override fun onAdShowedFullScreenContent() {
            mInterstitialAd = null
            loadInterstitial(context)
            Log.d("Admob", "onAdShowedFullScreenContent: Ad showed fullscreen content.")
        }
    }
}

fun showInterstitial(context: Context) {
    val activity = context.findActivity()

    if (mInterstitialAd != null) {
        mInterstitialAd?.show(activity!!)
    } else {
        Log.d("Admob", "showInterstitial: The interstitial ad wasn't ready yet.")
    }
}

