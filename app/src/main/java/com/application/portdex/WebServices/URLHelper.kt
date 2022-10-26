package com.application.portdex.WebServices

object URLHelper {

    const val COIN_URL ="https://rest-sandbox.coinapi.io/"
    const val COIN_ASSETS ="v1/assets"

    const val COIN_MARKET_BASE ="https://pro-api.coinmarketcap.com/v1/"
    const val COIN_MARKET_DATA ="cryptocurrency/listings/latest"

    const val BASE_URL_COINBASE ="https://api.coinbase.com/"
    const val COINBASE_CURRENCY ="v2/currencies"
    const val COINBASE_EXCHANGE_RATES ="v2/exchange-rates"

    const val OPEN_SEA_BASE_URL ="https://api.opensea.io/api/v1/"
    const val NFT_COLLECTION ="collections"

    //For swapzone apis
    const val SWAP_BASE_URL ="https://api.swapzone.io/v1/"
    const val SWAP_CURRENCY ="exchange/currencies"
    const val SWAP_CURRENCY_CONVERSION ="exchange/get-rate"

    //For NFT Market data (Alchemy)
    const val GET_NFT_MARKET = "https://eth-mainnet.g.alchemy.com/nft/v2/{apikey}/getNFTs"

    //for creating a charge
    const val CREATE_CHARGE ="https://api.commerce.coinbase.com/"
    const val CHARGE="charges/"

    //For circle apis
    const val CIRCLE_BASE_URL ="https://api-sandbox.circle.com/"
    const val CIRCLE_MASTER_WALLET_CONFIG ="v1/configuration"
    
    //for accepting crypto payment used circle Payment apis 
    const val CREATE_PAYMENT_INTENT ="v1/paymentIntents"
    const val GET_CRYPTO_PAYMENT_INTENT ="v1/paymentIntents" //with param id


}