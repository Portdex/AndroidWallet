package com.application.portdex.CryptoScreens.Fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.application.portdex.AcceptCryptoPayment
import com.application.portdex.AcceptCryptoPaymentCircle
import com.application.portdex.CryptoScreens.SwapCurrency
import com.application.portdex.CryptoScreens.WalletConnectScreen
import com.application.portdex.R
import com.application.portdex.Uitls.Constants
import com.application.portdex.databinding.FragmentCreatePaymentBinding
import com.google.android.material.button.MaterialButton
import com.stripe.model.PaymentLink
import com.stripe.model.Payout
import com.stripe.model.Price
import com.stripe.model.Product
import com.stripe.param.PaymentLinkCreateParams
import com.stripe.param.PriceCreateParams
//import kotlinx.android.synthetic.main.fragment_create_payment.*


class CreatePayment : Fragment() {

    lateinit var btnCopy : MaterialButton
    lateinit var  tvPaymentLink : TextView
    lateinit var btnSwap : MaterialButton
    lateinit var _binding : FragmentCreatePaymentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePaymentBinding.inflate(layoutInflater, container,false)
        // Inflate the layout for this fragment
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()

        btnCopy = view.findViewById(R.id.btnCopy)
        tvPaymentLink = view.findViewById(R.id.tvPaymentLink)
        btnSwap = view.findViewById(R.id.btnSwap)

        clicks()

        StrictMode.setThreadPolicy(policy)
        val createPayment = view.findViewById<MaterialButton>(R.id.btnPayment)

        createPayment.setOnClickListener{
            com.stripe.Stripe.apiKey =
                Constants.STRIPE_SECRET_KEY
            val product =  createProduct()


            val params = PriceCreateParams
                .builder()
                .setCurrency("usd")
                .setUnitAmount(1000L)
                .setProduct(product.id)
                .build()

            val price = Price.create(params)


            val params1 = PaymentLinkCreateParams
                .builder()
                .addLineItem(
                    PaymentLinkCreateParams.LineItem
                        .builder()
                        .setPrice(price.id)
                        .setQuantity(1L)
                        .build()
                )
                .build()

            val paymentLink = PaymentLink.create(params1)

            tvPaymentLink.text = paymentLink.url

            val intent = Intent(".SecondActivity")
            intent.putExtra("da ta","true")
            activity?.sendBroadcast(intent)

        }



    }

    fun clicks(){

        _binding.btnPayout.setOnClickListener{
            startActivity(Intent(requireActivity(), Payout::class.java))
        }

        _binding.btnCharge.setOnClickListener{
            startActivity(Intent(requireActivity(), AcceptCryptoPayment::class.java))
        }

        _binding.btnAcceptCrypto.setOnClickListener{
            startActivity(Intent(requireActivity(), AcceptCryptoPaymentCircle::class.java))
        }

        _binding.btnConnectToWallet.setOnClickListener{

        }

        btnCopy.setOnClickListener{
            val manager: ClipboardManager? =requireActivity().
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clipData = ClipData.newPlainText("text", tvPaymentLink.getText())
            manager?.setPrimaryClip(clipData)
        }

        btnSwap.setOnClickListener{

         startActivity(Intent(requireActivity(), SwapCurrency::class.java))
           startActivity(Intent(requireActivity(), WalletConnectScreen::class.java))
        }
    }


    fun createProduct() : Product {
        val params: MutableMap<String, Any> = HashMap()
        params["name"] = "Gold Special"

        val product: Product = Product.create(params)

        return product
    }

}