package com.application.portdex.adapters

import com.application.portdex.R
import com.application.portdex.databinding.ServiceProviderItemViewBinding
import com.application.portdex.domain.models.ProviderInfo

class ServiceProviderAdapter(val listener: (ProviderInfo) -> Unit) : BaseAdapter<ProviderInfo>(
    resource = R.layout.service_provider_item_view
) {

    override fun onItemClick(position: Int) {
        listener(getItemAtPosition(position))
    }

    override fun onBind(holder: BaseViewHolder, item: ProviderInfo) {
        val view = ServiceProviderItemViewBinding.bind(holder.view)
        view.provider = item
        view.executePendingBindings()
    }
}