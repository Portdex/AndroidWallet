package com.application.portdex.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.portdex.Uitls.Keys
import com.application.portdex.ViewModels.CryptoViewModel
import com.application.portdex.adapters.Crypto.MarketAdapter
import com.application.portdex.databinding.FragmentCryptoBinding
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CryptoFragment : Fragment() , Observer{
    private var param1: String? = null
    private var param2: String? = null
    var cryptoViewModel : CryptoViewModel? = null
    var binding : FragmentCryptoBinding? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCryptoBinding.inflate(inflater, container, false)
        val root = binding!!.root
        return  root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoViewModel = CryptoViewModel()
        cryptoViewModel!!.addObserver(this)
        if(isNetworkConnected(requireActivity())){
            getMarketData()
        }else{
            showInternetToast(requireActivity())
        }

    }

    fun getMarketData(){
        cryptoViewModel!!.getMarketData(requireActivity(), Keys.MSG_MARKET_DATA, binding!!.rlProgress.rlProgress,
        Keys.MSG_MARKET_DATA)
    }


    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun showInternetToast(context: Context?) {
        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
    }

    override fun update(o: Observable?, arg: Any?) {
        when(cryptoViewModel?.resultCode){
            Keys.CRYPTO ->{

            }

            Keys.MSG_MARKET_DATA->{
                if (cryptoViewModel!!.marketData != null && cryptoViewModel!!.marketData?.status!!.error_code == 0){

                    if(cryptoViewModel!!.marketData?.data != null && cryptoViewModel!!.marketData?.data!!.size > 0 ){
                        if(isAdded){
                            val marketAdapter = MarketAdapter(requireActivity(),cryptoViewModel!!.marketData?.data!!)
                            binding?.recyclerView!!.adapter = marketAdapter
                        }
                    }


                }
            }

            Keys.NFT ->{

            }
        }
    }


}