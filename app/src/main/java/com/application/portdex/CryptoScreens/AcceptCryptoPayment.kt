package com.application.portdex

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.application.portdex.Models.ChargesMetadata
import com.application.portdex.Models.ChargesModel
import com.application.portdex.Models.ChargesRequestModel
import com.application.portdex.Models.LocalPrice
import com.application.portdex.R
import com.application.portdex.Uitls.GeneralFunctions
import com.application.portdex.Uitls.Keys
import com.application.portdex.ViewModel.AcceptPaymentViewModel
import com.application.portdex.databinding.ActivityAcceptCryptoPaymentBinding
import com.application.portdex.databinding.ChatActivityBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
//import kotlinx.android.synthetic.main.activity_accept_crypto_payment.*
import java.util.*


class AcceptCryptoPayment : AppCompatActivity(), Observer , AdapterView.OnItemSelectedListener {

    var acceptPaymentViewModel : AcceptPaymentViewModel? = null
    var chargesModelLocal : ChargesModel? = null
    var bitmap: Bitmap? = null
    lateinit var _binding : ActivityAcceptCryptoPaymentBinding

    var addresses = arrayOf(
        "Select wallet",
        "apecoin", "bitcoin",
        "bitcoincash", "dai",
        "dogecoin", "ethereum", "litecoin", "shibainu", "tether", "usdc"
    )

    lateinit var spinner: Spinner
    lateinit var qrImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAcceptCryptoPaymentBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        acceptPaymentViewModel = AcceptPaymentViewModel()
        acceptPaymentViewModel!!.addObserver(this)

        if(GeneralFunctions.isNetworkAvailable(this)){
            generateChargesData()
        }else{
            GeneralFunctions.showInternetToast(this)
        }

        spinner = findViewById(R.id.walletSpinner)
        qrImage = findViewById(R.id.idIVQrcode)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            onBackPressed()
        }
        spinner.setOnItemSelectedListener(this);

    }

    fun setSpinner(){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, addresses)
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item )
        spinner.setAdapter(adapter)
    }

    private fun generateChargesData() {
//        var priceObj = JSONObject()
//        priceObj.put("amount", 12)
//        priceObj.put("currency", "USD")
//
//        var metaObj = JSONObject()
//        metaObj.put("customer_id", "1")
//        metaObj.put("customer_name", "name")
//
//        var mapObj = JSONObject()
//        mapObj.put("local_price", priceObj)
//        mapObj.put("metadata", metaObj)
//        mapObj.put("name","Inder")
//        mapObj.put("pricing_type", "fixed_price")
//        mapObj.put("description", "Need to pay for product")

        var chargesRequestModel = ChargesRequestModel("Need to pay for product", LocalPrice(12, "USD"),
        ChargesMetadata("1", "name"),"Inder","fixed_price")

        acceptPaymentViewModel!!.createCharge(this, Keys.CHARGES,  _binding.rlProgress.rlProgress,  chargesRequestModel)
    }

    override fun update(p0: Observable?, p1: Any?) {
       if (acceptPaymentViewModel!!.chargesModel != null){
           chargesModelLocal = acceptPaymentViewModel!!.chargesModel
           setSpinner()
       }
    }

    private fun generateQRCode(address : String) {
        val mWriter = MultiFormatWriter()
        try {
            //BitMatrix class to encode entered text and set Width & Height
            val mMatrix = mWriter.encode(address, BarcodeFormat.QR_CODE, 400, 400)
            val mEncoder = BarcodeEncoder()
            val mBitmap = mEncoder.createBitmap(mMatrix) //creating bitmap of code
            qrImage.setImageBitmap(mBitmap) //Setting generated QR code to imageView
            // to hide the keyboard
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var selectedWallet = addresses[position]
        if (position == 0){

        }else {
            Toast.makeText(this, selectedWallet, Toast.LENGTH_SHORT).show()
            when (selectedWallet) {
                "apecoin" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.apecoin)
                }
                "bitcoin" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.bitcoin)
                }
                "bitcoincash" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.bitcoincash)
                }
                "dai" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.dai)
                }
                "dogecoin" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.dogecoin)
                }
                "ethereum" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.ethereum)
                }
                "litecoin" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.litecoin)
                }
                "shibainu" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.shibainu)
                }
                "tether" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.tether)
                }
                "usdc" -> {
                    generateQRCode(chargesModelLocal!!.data.addresses.usdc)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
//        TODO("Not yet implemented")
    }

}