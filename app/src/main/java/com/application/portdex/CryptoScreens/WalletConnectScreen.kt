package com.application.portdex.CryptoScreens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.application.portdex.R
import com.google.gson.GsonBuilder
//    import kotlinx.android.synthetic.main.activity_wallet_connect_screen.*
import okhttp3.OkHttpClient
class WalletConnectScreen : AppCompatActivity() {
    private val wcClient by lazy {
//        WCClient(GsonBuilder(), OkHttpClient())
    }

//    val privateKey = PrivateKey("ba005cd605d8a02e3d5dfd04234cef3a3ee4f76bfbad2722d1fb5af8e12e6764".decodeHex())
//    val address = CoinType.ETHEREUM.deriveAddress(privateKey)

//    private val peerMeta = WCPeerMeta(name = "Example", url = "https://example.com")
//
//    private lateinit var wcSession: WCSession
//
//    private var remotePeerMeta: WCPeerMeta? = null
//
//    companion object {
//        init {
//            System.loadLibrary("TrustWalletCore")
//        }
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_connect_screen)
    }

    private fun setupConnectButton() {
//        runOnUiThread {
//            binding.connectButton.text = "Connect"
//            binding.connectButton.setOnClickListener {
//                connect(binding.uriInput.editText?.text?.toString() ?: return@setOnClickListener)
//            }
//        }
    }

//    fun connect(uri: String) {
//        disconnect()
//        wcSession = WCSession.from(uri) ?: throw InvalidSessionException()
//        wcClient.connect(wcSession, peerMeta)
//    }
//
//    fun disconnect() {
//        if (wcClient.session != null) {
//            wcClient.killSession()
//        } else {
//            wcClient.disconnect()
//        }
//    }

    fun approveSession() {
//        val address = binding.addressInput.editText?.text?.toString() ?: address
//        val chainId = binding.chainInput.editText?.text?.toString()?.toIntOrNull() ?: 1
//        wcClient.approveSession(listOf(address), chainId)
//        binding.connectButton.text = "Kill Session"
//        binding.connectButton.setOnClickListener {
//            disconnect()
//        }
    }

//    fun rejectSession() {
//        wcClient.rejectSession()
//        wcClient.disconnect()
//    }
//
//    fun rejectRequest(id: Long) {
//        wcClient.rejectRequest(id, "User canceled")
//    }
//
//    private fun onDisconnect() {
//        setupConnectButton()
//    }
//
//    private fun onFailure(throwable: Throwable) {
//        throwable.printStackTrace()
//    }
//
//    private fun onSessionRequest(peer: WCPeerMeta) {
//        runOnUiThread {
//            remotePeerMeta = peer
//            wcClient.remotePeerId ?: run {
//                println("remotePeerId can't be null")
//                return@runOnUiThread
//            }
//            val meta = remotePeerMeta ?: return@runOnUiThread
//            AlertDialog.Builder(this)
//                .setTitle(meta.name)
//                .setMessage("${meta.description}\n${meta.url}")
//                .setPositiveButton("Approve") { _, _ ->
//                    approveSession()
//                }
//                .setNegativeButton("Reject") { _, _ ->
//                    rejectSession()
//                }
//                .show()
//        }
//    }

//    private fun onEthSign(id: Long, message: WCEthereumSignMessage) {
//        runOnUiThread {
//            AlertDialog.Builder(this)
//                .setTitle(message.type.name)
//                .setMessage(message.data)
//                .setPositiveButton("Sign") { _, _ ->
//                    wcClient.approveRequest(id, privateKey.sign(message.data.decodeHex(), CoinType.ETHEREUM.curve()))
//                }
//                .setNegativeButton("Cancel") { _, _ ->
//                    rejectRequest(id)
//                }
//                .show()
//        }
//    }

//    private fun onEthTransaction(id: Long, payload: WCEthereumTransaction, send: Boolean = false) { }
//
//    private fun onBnbTrade(id: Long, order: WCBinanceTradeOrder) { }
//
//    private fun onBnbCancel(id: Long, order: WCBinanceCancelOrder) { }
//
//    private fun onBnbTransfer(id: Long, order: WCBinanceTransferOrder) { }
//
//    private fun onBnbTxConfirm(param: WCBinanceTxConfirmParam) { }

//    private fun onGetAccounts(id: Long) {
//        val account = WCAccount(
//            chainInput.editText?.text?.toString()?.toIntOrNull() ?: 1,
//            addressInput.editText?.text?.toString() ?: address,
//        )
//        wcClient.approveRequest(id, account)
//    }
//
//    private fun onSignTransaction(id: Long, payload: WCSignTransaction) { }
//
//    fun String.decodeHex(): ByteArray {
//        check(length % 2 == 0) { "Must have an even length" }
//
//        return removePrefix("0x")
//            .chunked(2)
//            .map { it.toInt(16).toByte() }
//            .toByteArray()
//    }

}