package com.application.portdex.data.repository

import android.content.Context
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.anggrayudi.storage.extension.openInputStream
import com.application.portdex.domain.repository.StorageRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val context: Context
) : StorageRepository {

    companion object {
        private const val TAG = "RegistrationRepositoryI"
        private val REGION = Regions.EU_WEST_2
        private const val IDENTITY_POOL_ID = "eu-west-2:fe9eb4b0-a617-4ce3-9151-309a56450d80"
        private const val BUCKET_NAME = "my-portdex-data-demo"
    }

    override fun uploadImage(file: DocumentFile): Single<String> {
        val credentials = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, REGION)
        val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.EU_WEST_2))

        val utility = TransferUtility.builder()
            .context(context.applicationContext)
            .s3Client(s3Client)
            .defaultBucket(BUCKET_NAME)
            .build()

        val fileKey = "user_${System.currentTimeMillis()}.png"

        return Single.create { emitter ->
            val transferListener = object : TransferListener {
                override fun onStateChanged(id: Int, state: TransferState) {
                    Log.d(TAG, "onStateChanged: ${state.name}")
                    if (state == TransferState.COMPLETED) {
                        val imageUrl = "https://${BUCKET_NAME}.s3.amazonaws.com/${fileKey}"
                        Log.d(TAG, "onStateChanged: $imageUrl")
                        emitter.onSuccess(imageUrl)
                    }
                }

                override fun onProgressChanged(
                    id: Int,
                    bytesCurrent: Long,
                    bytesTotal: Long
                ) {
                    Log.d(TAG, "onProgressChanged: ")
                }

                override fun onError(id: Int, ex: java.lang.Exception) {
                    Log.e(TAG, "onError: ", ex)
                    emitter.onError(ex)
                }
            }
            try {
                file.uri.openInputStream(context).use { input ->
                    val observer = utility.upload(fileKey, input)
                    observer.setTransferListener(transferListener)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}