package com.application.portdex.data.repository

import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.rx.RxAmplify
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

    override fun loginWithNumber(number: String, password: String): Single<Resource<Boolean>> {
        return RxAmplify.Auth.signIn(number, password)
            .map { result ->
                when (result.nextStep.signInStep) {
                    AuthSignInStep.CONFIRM_SIGN_IN_WITH_CUSTOM_CHALLENGE -> Resource.Success(true)
                    else -> error.getError(ErrorEnum.loginFailed)
                }
            }
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