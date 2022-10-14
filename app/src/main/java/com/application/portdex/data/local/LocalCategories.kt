package com.application.portdex.data.local

import android.content.Context
import com.application.portdex.R
import com.application.portdex.domain.models.SimpleItem
import com.application.portdex.domain.models.WalletItem
import com.application.portdex.domain.models.category.CategoryData

object LocalCategories {

    fun Context.getThingsNearBy(): List<CategoryData> {
        val list = mutableListOf<CategoryData>()
        list.add(CategoryData(id = 2, name = getString(R.string.label_service_provider), title = "Services Providers"))
        list.add(CategoryData(id = 5, name = getString(R.string.label_freelancers), title = "Freelancer"))
        list.add(CategoryData(id = 3, name = getString(R.string.label_food_stores), title = "Food"))
        list.add(CategoryData(id = 1, name = getString(R.string.label_retailers), title = "Retailer"))
        list.add(CategoryData(id = 4, name = getString(R.string.label_doctors), title = "Doctor"))
        list.add(CategoryData(id = 7, name = getString(R.string.label_property), title = "PropertyAgent"))


        list.add(CategoryData(id = 0, name = getString(R.string.label_digital)))
        list.add(CategoryData(id = 6, name = getString(R.string.label_search_local)))
        list.add(CategoryData(id = 8, name = getString(R.string.label_nft)))
        list.add(CategoryData(id = 9, name = getString(R.string.label_online_stores)))
        return list.map {
            it.apply {
                image = "things_nearby/${name}.png"
            }
        }
    }

    fun Context.getWalletSection(first: Boolean = true): MutableList<WalletItem> {
        return if (first) mutableListOf<WalletItem>().apply {
            add(WalletItem(id = 0, label = getString(R.string.label_manage_bank_payments)))
            add(WalletItem(id = 1, label = getString(R.string.label_multi_currency)))
            add(WalletItem(id = 2, label = getString(R.string.label_stable_currency_payments)))
            add(WalletItem(id = 3, label = getString(R.string.label_swap_crypto)))
            add(WalletItem(id = 4, label = getString(R.string.label_swap_lend_nft)))
            add(WalletItem(id = 5, label = getString(R.string.label_multi_chain_defi)))
            add(WalletItem(id = 6, label = getString(R.string.label_staking_crypto_tax)))
            add(WalletItem(id = 7, label = getString(R.string.label_subscription)))
            add(WalletItem(id = 8, label = getString(R.string.label_purchases)))
            add(WalletItem(id = 9, label = getString(R.string.label_buy_now_pay_later)))
        } else mutableListOf<WalletItem>().apply {
            add(WalletItem(id = 0, label = getString(R.string.label_crypto_for_payment)))
            add(WalletItem(id = 1, label = getString(R.string.label_mint_lend_swap_nft)))
            add(WalletItem(id = 2, label = getString(R.string.label_stable_currency_payments)))
            add(WalletItem(id = 3, label = getString(R.string.label_access_to_finance)))
        }
    }

    fun Context.getProfilePaymentList(): MutableList<SimpleItem> {
        return mutableListOf<SimpleItem>().apply {
            add(SimpleItem(label = getString(R.string.label_my_data), icon = R.drawable.logo_data))
            add(SimpleItem(label = getString(R.string.label_shopping_data), icon = R.drawable.logo_shopping))
            add(SimpleItem(label = getString(R.string.label_payment_transactions), icon = R.drawable.logo_payment_transaction))
            add(SimpleItem(label = getString(R.string.label_crypto_tax), icon = R.drawable.logo_crypto_tax))
            add(SimpleItem(label = getString(R.string.label_nft_transactions), icon = R.drawable.logo_nft_transactions))
            add(SimpleItem(label = getString(R.string.label_defi_transactions), icon = R.drawable.logo_defi_transactions))
            add(SimpleItem(label = getString(R.string.label_crypto_currency_transactions), icon = R.drawable.logo_crypto_currency_transactions))
        }
    }

    fun Context.getProfileRewards():MutableList<SimpleItem>{
        return mutableListOf<SimpleItem>().apply {
            add(SimpleItem(label = getString(R.string.label_cash_rewards), icon =R.drawable.logo_cash_rewards))
            add(SimpleItem(label = getString(R.string.label_data_rewards), icon =R.drawable.logo_data_rewards))
            add(SimpleItem(label = getString(R.string.label_staking_rewards), icon =R.drawable.logo_staking_rewards))
            add(SimpleItem(label = getString(R.string.label_stable_currency_rewards), icon =R.drawable.logo_stable_currency_rewards))
            add(SimpleItem(label = getString(R.string.label_cryptocurrency_rewards), icon =R.drawable.logo_cryptocurrency_rewards))
            add(SimpleItem(label = getString(R.string.label_shopping_rewards), icon =R.drawable.logo_shopping_rewards))
        }
    }

    fun Context.getChatTabs():MutableList<SimpleItem>{
        return mutableListOf<SimpleItem>().apply {
            add(SimpleItem(label = getString(R.string.label_retailers), icon = R.drawable.ic_chat))
            add(SimpleItem(label = getString(R.string.label_freelancers), icon = R.drawable.ic_chat))
            add(SimpleItem(label = getString(R.string.label_doctors), icon = R.drawable.ic_chat))
            add(SimpleItem(label = getString(R.string.label_property), icon = R.drawable.ic_chat))
            add(SimpleItem(label = getString(R.string.label_school), icon = R.drawable.ic_chat))
        }
    }

    fun getRetailerProducts(): MutableList<CategoryData> {
        val list = listOf(
            "All Products",
            "General",
            "Sweets",
            "Snacks",
            "Drinks",
            "Fruit&Veg",
            "Meat",
            "Diet",
            "Grocery",
            "Diary",
            "Pharmacy",
            "Personal Care",
            "Bakery",
            "Frozen Food",
            "Home Care"
        )
        return list.mapIndexed { index, s ->
            CategoryData(id = index, name = s)
        }.toMutableList()
    }
}