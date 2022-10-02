package com.application.portdex.data.remote

object ApiEndPoints {

    const val BASE_URL = "https://www.google.com"
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

}