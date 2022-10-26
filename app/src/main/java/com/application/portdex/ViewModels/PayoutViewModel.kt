package com.application.portdex.ViewModels

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.application.portdex.Models.MasterWalletConfigModel
import com.application.portdex.Models.PaymentBlockchainResponse
import com.application.portdex.Models.PaymentIntentRequest
import com.application.portdex.Models.PaymentIntentResponse
import com.application.portdex.Uitls.Constants
import com.application.portdex.WebServices.RetrofitService
import com.application.portdex.WebServices.URLHelper
import retrofit2.Call
import retrofit2.Response
import java.util.*


class PayoutViewModel : Observable() {
    var resultcode =""
    var masterWalletConfigModel : MasterWalletConfigModel? = null
    var paymentIntentResponse : PaymentIntentResponse? = null
    var paymentBlockchainResponse : PaymentBlockchainResponse? = null


    /** Get the payment intent to access the blockchain address */
    fun getPaymentBlockchain(context : Context, requestCode : String, progress: View, payment_id: String, uuid : String){
        var apiServices = RetrofitService.create(context, false, URLHelper.CIRCLE_BASE_URL)
        var response = apiServices.getPaymentBlockchainAddress("Bearer "+ Constants.CIRCLE_API_KEY,
            uuid,payment_id )
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<PaymentBlockchainResponse> {
            override fun onResponse(
                call: Call<PaymentBlockchainResponse>,
                response: Response<PaymentBlockchainResponse>
            ) {
                progress.visibility = View.GONE
                if (response.body() != null) {
                    if (response.code() == 200 && response.body()!!.data != null){
                        Log.d("getData", "onResponse: ${response.body().toString()}")
                        resultcode = requestCode
                        paymentBlockchainResponse = response.body()
                        setChanged()
                        notifyObservers()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<PaymentBlockchainResponse>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }


    /** Create the payment request for accepting the amount*/
    fun createPaymentRequest(context : Context, requestCode : String, progress: View, paymentIntentRequest: PaymentIntentRequest){
        var apiServices = RetrofitService.create(context, false, URLHelper.CIRCLE_BASE_URL)
        var response = apiServices.createPaymentRequest("Bearer "+Constants.CIRCLE_API_KEY,
        "b3d9d2d5-4c12-4946-a09d-953e82fae2b0","application/json", paymentIntentRequest)
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<PaymentIntentResponse> {
            override fun onResponse(
                call: Call<PaymentIntentResponse>,
                response: Response<PaymentIntentResponse>
            ) {
                progress.visibility = View.GONE
                if (response.body() != null) {
                    if (response.code() == 201 && response.body()!!.data != null){
                        Log.d("getData", "onResponse: ${response.body().toString()}")
                        resultcode = requestCode
                        paymentIntentResponse = response.body()
                        setChanged()
                        notifyObservers()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<PaymentIntentResponse>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** Get master wallet configuration and id from circle api*/
    fun getMasterWallet(context : Context, requestCode : String, progress: View){
        var apiServices = RetrofitService.create(context, false, URLHelper.CIRCLE_BASE_URL)
        var response = apiServices.masterWalletConfiguration("Bearer "+Constants.CIRCLE_API_KEY)

        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<MasterWalletConfigModel> {
            override fun onResponse(
                call: Call<MasterWalletConfigModel>,
                response: Response<MasterWalletConfigModel>
            ) {
                if (response.body() != null) {
                    if (response.code() == 201 && response.body()!!.data != null){
                        Log.d("getData", "onResponse: ${response.body().toString()}")
                        resultcode = requestCode
                        masterWalletConfigModel = response.body()
                        setChanged()
                        notifyObservers()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<MasterWalletConfigModel>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }
}