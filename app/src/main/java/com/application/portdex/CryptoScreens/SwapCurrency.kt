package com.application.portdex.CryptoScreens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.portdex.Models.CurrData
import com.application.portdex.Models.SwapCurrencyItem
import com.application.portdex.R
import com.application.portdex.Uitls.GeneralFunctions
import com.application.portdex.Uitls.Keys
import com.application.portdex.ViewModels.CryptoViewModel
import com.application.portdex.databinding.ActivitySwapCurrencyBinding
//import kotlinx.android.synthetic.main.activity_swap_currency.*
//import kotlinx.android.synthetic.main.progress_dialog.*
import java.util.*
import kotlin.collections.ArrayList


class SwapCurrency : AppCompatActivity() , Observer{
    var cryptoViewModel : CryptoViewModel? = null
    var currencyList = mutableListOf<CurrData>()
    var swapCurrList = ArrayList<SwapCurrencyItem>()
    var from =""
    var to =""
    var amount =""
    var rateType =""
    var chooseRate =""
    var selectedFromData : SwapCurrencyItem? = null
    var selectedToData : SwapCurrencyItem? = null
    lateinit var _binding : ActivitySwapCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySwapCurrencyBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        cryptoViewModel = CryptoViewModel()
        cryptoViewModel!!.addObserver(this)

        if(GeneralFunctions.isNetworkAvailable(this)){
//            getCurrency()
            getSwapCurrency()
        }else{
            GeneralFunctions.showInternetToast(this)
        }

        _binding.etRate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0?.toString()?.length != 0){
                    getCurrencyConversion(selectedFromData?.ticker!!, selectedToData?.ticker!!,p0.toString(),
                    "all", "best")
                }
            }
        })
        clicks()
    }

    fun clicks(){
        _binding.ivBack.setOnClickListener{
            finish()
        }

        _binding.btnSwap.setOnClickListener{
            if(GeneralFunctions.isNetworkAvailable(this)){
                getCurrencyConversion(selectedFromData?.name!!, selectedToData?.name!!,_binding.etRate.text.toString(),
                    "all", "best")
            }else{
                GeneralFunctions.showInternetToast(this)
            }
        }
    }
    override fun update(o: Observable?, arg: Any?) {
        when(cryptoViewModel?.resultCode){
            Keys.CRYPTO ->{

            }

            Keys.MSG_SWAP_CURRENCY ->{
                if (cryptoViewModel!!.swapCurrData != null && cryptoViewModel!!.swapCurrData?.size!! > 0){
                    swapCurrList.clear()
                    swapCurrList.addAll(cryptoViewModel!!.swapCurrData!!)

                    var currencyNames = mutableListOf<String>()
                    for(i in swapCurrList.indices){
                        currencyNames.add(swapCurrList.get(i).name)
                    }

                    setFromSpinner(currencyNames)

                    setToSpinner(currencyNames)


                }
            }

            Keys.MSG_CURRENCY->{
                if (cryptoViewModel!!.currencyData != null && cryptoViewModel!!.currencyData?.data?.size!! > 0){


                    currencyList.clear()
                    currencyList.addAll(cryptoViewModel!!.currencyData!!.data)

                    var currencyNames = mutableListOf<String>()
                    for(i in currencyList.indices){
                        currencyNames.add(currencyList.get(i).name)
                    }
                    val arrayAdapter: ArrayAdapter<String> =
                        ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyNames)
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    _binding.spSpinner.setAdapter(arrayAdapter)
                    _binding.spSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            var data = parent.getItemAtPosition(position)

                            Toast.makeText(
                                parent.context,
                                "Selected: $data",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    })

//                        val currencyAdapter = CurrencyAdapter(this@SwapCurrency,cryptoViewModel!!.currencyData!!.data)
//                        recyclerView!!.adapter = currencyAdapter



                }
            }

            Keys.MSG_CONVERT_CURRENCY ->{
                if(cryptoViewModel!!.currencyConvert != null){
                     var currencyConvertData = cryptoViewModel!!.currencyConvert
                     var minAmount = currencyConvertData?.amountTo
                    _binding.etTo.setText( minAmount.toString())

                }
            }

            Keys.NFT ->{

            }
        }
    }

    fun setFromSpinner(currencyNames: MutableList<String>) {
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyNames)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        _binding.spSpinner.setAdapter(arrayAdapter)
        _binding.spSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                var data = parent.getItemAtPosition(position)
                selectedFromData = swapCurrList.get(position)
                Toast.makeText(
                    parent.context,
                    "Selected: $data",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun setToSpinner(currencyNames: MutableList<String>) {
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyNames)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        _binding.spSpinnerTo.setAdapter(arrayAdapter)
        _binding.spSpinnerTo.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                var data = parent.getItemAtPosition(position)
                selectedToData = swapCurrList.get(position)
                Toast.makeText(
                    parent.context,
                    "Selected: $data",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }


    fun getCurrency(){
        cryptoViewModel!!.getCurrency(this@SwapCurrency, _binding.rlProgress.rlProgress,Keys.MSG_CURRENCY)
    }

    fun getSwapCurrency(){
        cryptoViewModel!!.getSwapCurrency(this@SwapCurrency, _binding.rlProgress.rlProgress,Keys.MSG_SWAP_CURRENCY)
    }

    fun getCurrencyConversion(from: String,
                              to: String,
                              amount: String,
                              rateType: String,
                              chooseRate: String){
        cryptoViewModel!!.getCurrencyConversion(this@SwapCurrency, _binding.rlProgress.rlProgress,Keys.MSG_CONVERT_CURRENCY, from, to,
        amount, rateType, chooseRate)
    }
}