package com.application.portdex.data.repository

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.rx.RxAmplify
import com.application.portdex.core.utils.ValidationUtils
import com.application.portdex.data.errors.ErrorEnum
import com.application.portdex.data.errors.ErrorRepository
import com.application.portdex.data.utils.Resource
import com.application.portdex.domain.repository.LoginRepository
import com.jacopo.pagury.prefs.PrefUtils
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val error: ErrorRepository
) : LoginRepository {

    companion object {
        private const val TAG = "LoginRepositoryImpl"
        //USERNAME : dadb5341-6ac2-4f69-88a5-8bdf901a1057
    }

    private val password by lazy { ValidationUtils.generatePassword() }

    override fun loginWithNumber(number: String, password: String): Single<Resource<Boolean>> {
        return RxAmplify.Auth.signIn(number, password)
            .map { result ->
                when (result.nextStep.signInStep) {
                    AuthSignInStep.CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE -> {
                        result.nextStep.additionalInfo?.get("USERNAME")?.let { userName ->
                            PrefUtils.setUserName(userName)
                        }
                        Resource.Success(true)
                    }
                    else -> error.getError(ErrorEnum.loginFailed)
                }
            }
    }

    override fun signUpWithNumber(number: String): Single<Resource<Boolean>> {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.phoneNumber(), number)
            .build()
        return RxAmplify.Auth.signUp(number, password, options)
            .onErrorResumeNext { error.getException(it) }
            .map { Resource.Success(it.isSignUpComplete) }
    }

    override fun confirmLogin(code: String): Single<Resource<Boolean>> {
        return RxAmplify.Auth.confirmSignIn(code)
            .map { result -> Resource.Success(result.isSignInComplete) }
    }

    override fun fetchAuthSession(): Single<Resource<Boolean>> {
        return RxAmplify.Auth.fetchAuthSession()
            .map { it as AWSCognitoAuthSession }
            .map { session ->
                session.userPoolTokens.value?.let { token ->
                    PrefUtils.getLoginInfo()?.let { info ->
                        info.accessToken = token.accessToken
                        info.refreshToken = token.refreshToken
                        info.idToken = token.idToken
                        PrefUtils.setLoginInfo(info)
                    }
                    Resource.Success(true)
                } ?: kotlin.run {
                    Resource.Error(session.userPoolTokens.error?.message)
                }
            }
    }


    override fun logOut(): Completable {
        return RxAmplify.Auth.signOut()
    }
}