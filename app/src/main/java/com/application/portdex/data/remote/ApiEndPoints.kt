package com.application.portdex.data.remote

object ApiEndPoints {

    const val BASE_URL = "https://www.google.com"
    const val CHAT_DOMAIN = "chat.portdex.com"
    const val CHAT_HOST = "ec2-18-134-161-39.eu-west-2.compute.amazonaws.com"
    const val CHAT_PORT = 5222

    private const val testEnv = false

    fun getServicesProviders(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/get_service_providers"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/get_service_providers"
    }

    fun getProvidePackages(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/get_package_by_service_provider/"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/get_package_by_service_provider/"
    }

    fun getProfileByCategory(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/get_profile_by_cagegory/"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/get_profile_by_cagegory/"
    }

    fun getCategoriesData(): String {
        return if (testEnv) "https://5zoqubvc44.execute-api.eu-west-2.amazonaws.com/test/categories"
        else "https://ts1227dht3.execute-api.eu-west-2.amazonaws.com/prod/categories"
    }

    fun getUserProfile(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/get_profile_by_phone/"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/get_profile_by_phone/"
    }

    fun getSaveProfile(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/save_profile"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/save_profile"
    }

    fun getNewsFeedPost(): String {
        return if (testEnv) "https://31ytm5nhwc.execute-api.eu-west-2.amazonaws.com/test/post/getall"
        else "https://lodad3dujc.execute-api.eu-west-2.amazonaws.com/prod/post/getall"
    }

    fun getNearByUsers(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/users_near_me/"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/users_near_me/"
    }

    fun getSaveStore(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/save_store"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/save_store"
    }

    fun getCreateUserServicePost(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/post_save"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/post_save"
    }

    // only for retailer and food
    fun getRetailerStoreProducts(): String {
        return if (testEnv) "https://efxbmt8kwc.execute-api.eu-west-2.amazonaws.com/get_products_by_store/"
        else "https://c8cw3ycoi3.execute-api.eu-west-2.amazonaws.com/get_products_by_store/"
    }
}