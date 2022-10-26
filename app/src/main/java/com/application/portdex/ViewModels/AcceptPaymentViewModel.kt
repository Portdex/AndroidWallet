package com.application.portdex.ViewModel

import android.content.Context
import android.view.View
import android.widget.Toast
import com.application.portdex.Models.ChargesModel
import com.application.portdex.Models.ChargesRequestModel
import com.application.portdex.Uitls.Constants
import com.application.portdex.WebServices.URLHelper
import com.application.portdex.WebServices.RetrofitService
import retrofit2.Call
import retrofit2.Response
import java.util.*

class AcceptPaymentViewModel : Observable(){

    var code = ""
    var chargesModel : ChargesModel? = null
    var responseCode = ""

    fun createCharge(
        context: Context,
        requestCode: String,
        progress: View,
        chargesRequestModel: ChargesRequestModel
    ){
        var apiServices = RetrofitService.create(context, false, URLHelper.CREATE_CHARGE)
        var response = apiServices.createCharge("application/json", Constants.COINBASE_COMMERCE_API_KEY,
            "2018-03-22", chargesRequestModel )

        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<ChargesModel> {
            override fun onResponse(
                call: Call<ChargesModel>,
                response: Response<ChargesModel>
            ) {
                if (response.body() != null) {
                    if (response.code() == 201 && response.body()!!.data != null){
//                        Log.d("getData", "onResponse: ${response.body().toString()}")
                        code = response.body()!!.data.code
                        acceptPayment(context, requestCode, progress)
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<ChargesModel>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }


    fun acceptPayment(
        context: Context,
        requestCode: String,
        progress: View){
        var apiServices = RetrofitService.create(context, false, URLHelper.CREATE_CHARGE)
        var response = apiServices.acceptPayments("2018-03-22", URLHelper.CHARGE + code)

        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<ChargesModel> {
            override fun onResponse(
                call: Call<ChargesModel>,
                response: Response<ChargesModel>
            ) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    if (response.code() == 200 && response.body()!!.data != null){
                        chargesModel = response.body()
                        responseCode = requestCode
                        setChanged()
                        notifyObservers()
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<ChargesModel>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}