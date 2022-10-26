package com.application.portdex.WebServices

import com.application.portdex.Models.*
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {


    @GET(URLHelper.COIN_MARKET_DATA)
    fun getMarketData(@Header("X-CMC_PRO_API_KEY") apiKey: String): Call<MarketData>

    @GET(URLHelper.NFT_COLLECTION)
    fun getNFTCollections(): Call<NFTCollectionData>

    @GET(URLHelper.COINBASE_CURRENCY)
    fun getCurrency(): Call<CurrencyData>

    @GET(URLHelper.SWAP_CURRENCY)
    fun getSwapCurrency(@Header("x-api-key") header: String): Call<SwapCurrencyModel>

    @GET(URLHelper.COINBASE_EXCHANGE_RATES)
    fun getExchangeRates(): Call<ExchangeRates>

    @GET(URLHelper.SWAP_CURRENCY_CONVERSION)
    fun getCurrencyConversion(
        @Header("x-api-key") header: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String,
        @Query("rateType") rateType: String,
        @Query("chooseRate") chooseRate: String
    ): Call<CurrencyConvert>


    @GET(URLHelper.GET_NFT_MARKET)
    fun getNFTMarketData(
        @Path("path") path: String,
        @Query("owner") owner: String
    ): Call<NFTMarketData>


    @POST(URLHelper.CHARGE)
    fun createCharge(
        @Header("Content-Type") contentType: String,
        @Header("X-CC-Api-Key") apiKey: String,
        @Header("X-CC-Version") version: String,
        @Body chargesRequestModel: ChargesRequestModel
    ): Call<ChargesModel>

    //Accept crypto payments
    @GET
    fun acceptPayments(
        @Header("X-CC-Version") version: String,
        @Url url: String
    ): Call<ChargesModel>


    @GET(URLHelper.CIRCLE_MASTER_WALLET_CONFIG)
    fun masterWalletConfiguration(@Header("Authorization") token: String): Call<MasterWalletConfigModel>

    @POST(URLHelper.CREATE_PAYMENT_INTENT)
    fun createPaymentRequest(
        @Header("Authorization") token: String,
        @Header("X-Requested-Id") xrequestId: String,
        @Header("Content-Type") content_type: String,
        @Body paymentIntentRequest : PaymentIntentRequest
    ) : Call<PaymentIntentResponse>

    @GET(URLHelper.GET_CRYPTO_PAYMENT_INTENT)
    fun getPaymentBlockchainAddress(@Header("Authorization") token : String,
    @Header("X-Requested-Id") request_id : String,
    @Query("id") id : String) : Call<PaymentBlockchainResponse>
}