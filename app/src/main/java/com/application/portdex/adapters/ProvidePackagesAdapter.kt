package com.application.portdex.adapters

import com.application.portdex.R
import com.application.portdex.databinding.PackageItemViewBinding
import com.application.portdex.domain.models.ProviderPackage

class ProvidePackagesAdapter : BaseAdapter<ProviderPackage>(resource = R.layout.package_item_view) {

    override fun onBind(holder: BaseViewHolder, item: ProviderPackage) {
        val view = PackageItemViewBinding.bind(holder.view)
        view.item = item
        view.executePendingBindings()
    }
}