package com.application.portdex.CryptoScreens.ui.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.application.portdex.Uitls.Keys
import com.application.portdex.ViewModels.CryptoViewModel
import com.application.portdex.adapters.Crypto.NFTAdapter
import com.application.portdex.databinding.FragmentNFTBinding
import java.util.*

class NFTFragment : Fragment() , Observer{
    var cryptoViewModel : CryptoViewModel? = null
    var binding : FragmentNFTBinding? =null

    fun getNFTData(){
        cryptoViewModel!!.getNFTData(requireActivity(), Keys.MSG_NFT, binding!!.rlProgress)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoViewModel = CryptoViewModel()
        cryptoViewModel!!.addObserver(this)
        if(isNetworkConnected(requireActivity())){
            getNFTData()
        }else{
            showInternetToast(requireActivity())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNFTBinding.inflate(inflater, container, false)
        val root = binding!!.root
        return  root
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
            Keys.MSG_NFT -> {
                if (cryptoViewModel!!.collectionData != null ){
                    val nftAdapter = NFTAdapter(requireActivity(),cryptoViewModel!!.collectionData?.collections!!)
                    binding?.recyclerView!!.adapter = nftAdapter
                }
            }
        }
    }

}