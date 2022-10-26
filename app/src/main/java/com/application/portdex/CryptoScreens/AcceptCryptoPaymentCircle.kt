package com.application.portdex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.application.portdex.Models.Amount
import com.application.portdex.Models.PaymentIntentRequest
import com.application.portdex.Models.PaymentMethods
import com.application.portdex.Uitls.GeneralFunctions
import com.application.portdex.Uitls.Keys
import com.application.portdex.ViewModels.PayoutViewModel
import com.application.portdex.databinding.ActivityAcceptCryptoPaymentBinding
import com.application.portdex.databinding.ActivityAcceptCryptoPaymentCircleBinding
//import kotlinx.android.synthetic.main.activity_accept_crypto_payment_circle.*
import java.util.*

class AcceptCryptoPaymentCircle : AppCompatActivity(), Observer {
    var UUID =""
    var payoutViewModel : PayoutViewModel? = null
    lateinit var _binding : ActivityAcceptCryptoPaymentCircleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAcceptCryptoPaymentCircleBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        payoutViewModel = PayoutViewModel()
        payoutViewModel!!.addObserver(this)

        UUID = GeneralFunctions.getUUID(this)

        clicks()

    }

    fun clicks(){
        _binding.btnCreateRequest.setOnClickListener{
            if (_binding.etAmount.text.toString().length != 0){
                var amount = _binding.etAmount.text.toString()
                var paymentIntentRequest = PaymentIntentRequest(UUID, Amount(amount,"USD"),
                    "USD", mutableListOf(PaymentMethods("blockchain","ETH")) )
                payoutViewModel!!.createPaymentRequest(this@AcceptCryptoPaymentCircle,
                    Keys.CREATE_CRYPTO_PAYMENT_REQUEST,_binding.rlProgress.rlProgress,paymentIntentRequest )
            }

        }
    }

    override fun update(o: Observable?, arg: Any?) {
        when(payoutViewModel!!.resultcode){
            Keys.CREATE_CRYPTO_PAYMENT_REQUEST -> {
                if(payoutViewModel!!.paymentIntentResponse != null){
                    var paymentResponse = payoutViewModel!!.paymentIntentResponse
                    var payment_id = paymentResponse?.data!!.id
                    if(paymentResponse?.data!!.paymentMethods.size > 0){
                        var blockchainAddress = paymentResponse?.data!!.paymentMethods.get(0).address

                        getPaymentIntent(UUID, payment_id)
                        Toast.makeText(this@AcceptCryptoPaymentCircle,"request created", Toast.LENGTH_LONG).show()
                    }

                }
            }

            Keys.GET_PAYMENT_BLOCKCHAIN ->{
                if(payoutViewModel!!.paymentIntentResponse != null){
                    var paymentResponse = payoutViewModel!!.paymentIntentResponse
                    var payment_id = paymentResponse?.data!!.id
                    if(paymentResponse?.data!!.paymentMethods.size > 0){
                        var blockchainAddress = paymentResponse?.data!!.paymentMethods.get(0).address

                        getPaymentIntent(UUID, blockchainAddress)
                        Toast.makeText(this@AcceptCryptoPaymentCircle,"request created", Toast.LENGTH_LONG).show()
                    }

                }
            }

        }

    }

    //get payment intent for the blockchain access
    fun getPaymentIntent(uuid : String , paymentId : String){
        payoutViewModel!!.getPaymentBlockchain(this@AcceptCryptoPaymentCircle,
            Keys.GET_PAYMENT_BLOCKCHAIN,_binding.rlProgress.rlProgress,paymentId , uuid)
    }


}