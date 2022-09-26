package com.application.portdex.data.mappers

import android.content.Context
import com.application.portdex.R
import com.application.portdex.data.remote.dto.CategoryDto
import com.application.portdex.domain.models.category.CategoryData
import com.application.portdex.domain.models.category.CategoryItem

object CategoriesMapper {

    fun CategoryDto.toCategoryItems(context: Context): List<CategoryItem> {
        return results.items.filter { !it.title.isNullOrEmpty() }.map { category ->
            CategoryItem(
                id = category.id,
                title = category.title.mapCategoryName(context),
                data = category.data.map { item ->
                    CategoryData(
                        id = item.subCatID,
                        name = item.name?.replace("_", " "),
                        image = item.name?.mapImage(category.title)
                    )
                }
            )
        }
    }

    private fun String?.mapCategoryName(context: Context): String {
        return when (this) {
            "Retailer" -> context.getString(R.string.label_retailers_real_time)
            "Freelancer" -> context.getString(R.string.label_freelance_service)
            "Food" -> context.getString(R.string.label_top_food_recommended)
            "Services Providers" -> context.getString(R.string.label_service_provider_near_by)
            "Doctor" -> context.getString(R.string.label_recommended_doctors)
            "Property Agent" -> context.getString(R.string.label_properties_near_by)
            else -> this!!
        }
    }

    private fun String?.mapImage(category: String?): String {
        return when (category) {
            "Retailer" -> "retailer/${this}.png"
            "Food" -> "food/${this}.png"
            else -> ""
        }
    }
}