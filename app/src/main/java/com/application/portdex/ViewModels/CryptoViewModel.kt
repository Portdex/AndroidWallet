package com.application.portdex.ViewModels

import android.content.Context
import android.view.View
import android.widget.Toast
import com.application.portdex.Models.*
import com.application.portdex.Uitls.Constants
import com.application.portdex.WebServices.RetrofitService
import com.application.portdex.WebServices.URLHelper
import com.application.portdex.databinding.ProgressDialogBinding
import retrofit2.Call
import retrofit2.Response
import java.util.*

class CryptoViewModel : Observable() {
    var resultCode = ""
    var marketData: MarketData? = null
    var collectionData: NFTCollectionData? = null
    var currencyData: CurrencyData? = null
    var exchangeRates: ExRates? = null
    var swapCurrData = ArrayList<SwapCurrencyItem>()
    var currencyConvert: CurrencyConvert? = null
    var nftDataList : List<OwnedNFTs>? = null


    /** Get the crypto market data */
    fun getMarketData(
        context: Context,
        msgMarketData: String,
        progress: View,
        requestCode: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.COIN_MARKET_BASE)
        var response = apiServices.getMarketData(Constants.COIN_KEY)
        response.enqueue(object : retrofit2.Callback<MarketData> {
            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    marketData = response.body()!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<MarketData>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** get Currency data */
    fun getCurrency(
        context: Context,
        progress: View,
        requestCode: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.BASE_URL_COINBASE)
        var response = apiServices.getCurrency()
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<CurrencyData> {
            override fun onResponse(call: Call<CurrencyData>, response: Response<CurrencyData>) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    currencyData = response.body()!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<CurrencyData>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }


    /** get swap Currency data */
    fun getSwapCurrency(
        context: Context,
        progress: View,
        requestCode: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.SWAP_BASE_URL)
        var response = apiServices.getSwapCurrency(Constants.SWAPZONE_API_KEY)
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<SwapCurrencyModel> {
            override fun onResponse(
                call: Call<SwapCurrencyModel>,
                response: Response<SwapCurrencyModel>
            ) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    swapCurrData = response.body()!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<SwapCurrencyModel>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** get currency conversion using swapzone */
    fun getCurrencyConversion(
        context: Context,
        progress: View,
        requestCode: String,
        from: String,
        to: String,
        amount: String,
        rateType: String,
        chooseRate: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.SWAP_BASE_URL)
        var response = apiServices.getCurrencyConversion(Constants.SWAPZONE_API_KEY,from , to, amount, rateType, chooseRate)
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<CurrencyConvert> {
            override fun onResponse(
                call: Call<CurrencyConvert>,
                response: Response<CurrencyConvert>
            ) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    currencyConvert = response.body()!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<CurrencyConvert>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })


    }

    /** Currency conversion through swap */
    fun getCurrencyConvert(
        context: Context,
        progress: View,
        requestCode: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.BASE_URL_COINBASE)
        var response = apiServices.getExchangeRates()
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    exchangeRates = response.body()!!.data
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** get Currency exchange data */
    fun getCurrencyExchange(
        context: Context,
        progress: View,
        requestCode: String
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.BASE_URL_COINBASE)
        var response = apiServices.getExchangeRates()
        progress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                progress.visibility = View.GONE

                if (response.body() != null) {
                    exchangeRates = response.body()!!.data
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                progress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** Get the NFT data collection */
    fun getNFTData(
        context: Context,
        requestCode: String,
        progress: ProgressDialogBinding,
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.OPEN_SEA_BASE_URL)
        var response = apiServices.getNFTCollections()

        progress.rlProgress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<NFTCollectionData> {
            override fun onResponse(
                call: Call<NFTCollectionData>,
                response: Response<NFTCollectionData>
            ) {
                progress.rlProgress.visibility = View.GONE

                if (response.body() != null) {
                    collectionData = response.body()!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<NFTCollectionData>, t: Throwable) {
                progress.rlProgress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** Get NFT Market data from Alchemy */
    fun getNFTMarketData (  context: Context,
    requestCode: String,
    progress: ProgressDialogBinding,
    ) {
        var apiServices = RetrofitService.create(context, false, URLHelper.OPEN_SEA_BASE_URL)
        var response = apiServices.getNFTMarketData(Constants.ALCHEMY_API_KEY, Constants.ALCHEMY_PUBLIC_OWNER)

        progress.rlProgress.visibility = View.VISIBLE
        response.enqueue(object : retrofit2.Callback<NFTMarketData> {
            override fun onResponse(
                call: Call<NFTMarketData>,
                response: Response<NFTMarketData>
            ) {
                progress.rlProgress.visibility = View.GONE

                if (response.body() != null) {
                    nftDataList = response.body()!!.ownedNfts!!
                    resultCode = requestCode
                    setChanged()
                    notifyObservers()
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<NFTMarketData>, t: Throwable) {
                progress.rlProgress.visibility = View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

            }
        })
    }

    /** Creating a charge for accepting the crypto payments */
//    fun createCharge(context: Context,
//                     requestCode: String,
//                     progress: ProgressDialogBinding,
//                     data : HashMap<String,String>){
//        var apiServices = RetrofitService.create(context, false, URLHelper.CREATE_CHARGE)
//        var response = apiServices.createCharge("application/json", Constants.COINBASE_COMMERCE_API_KEY,
//        "2018-03-22", data )
//
//        progress.rlProgress.visibility = View.VISIBLE
//        response.enqueue(object : retrofit2.Callback<ChargesModel> {
//            override fun onResponse(
//                call: Call<CreateChargeData>,
//                response: Response<CreateChargeData>
//            ) {
//                progress.rlProgress.visibility = View.GONE
//
//                if (response.body() != null) {
////                    nftDataList = response.body()!!.ownedNfts!!
////                    resultCode = requestCode
////                    setChanged()
////                    notifyObservers()
//                } else {
//                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
//
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<Char>, t: Throwable) {
//                progress.rlProgress.visibility = View.GONE
//                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
//
//            }
//        })
//    }
}