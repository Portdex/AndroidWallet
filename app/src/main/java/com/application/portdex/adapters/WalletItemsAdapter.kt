package com.application.portdex.adapters

import com.application.portdex.R
import com.application.portdex.core.utils.GenericUtils
import com.application.portdex.databinding.WalletItemListBinding
import com.application.portdex.domain.models.WalletItem

class WalletItemsAdapter : BaseAdapter<WalletItem>(R.layout.wallet_item_list) {

    override fun onBind(holder: BaseViewHolder, item: WalletItem) {
        val view = WalletItemListBinding.bind(holder.itemView)

        view.mainContainer.background = GenericUtils.getGradientBg(true)
        view.labelView.text = item.label
    }
}