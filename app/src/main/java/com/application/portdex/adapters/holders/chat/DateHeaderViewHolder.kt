package com.application.portdex.adapters.holders.chat

import com.application.portdex.core.utils.DateFormatter
import com.application.portdex.databinding.ItemDateHeaderBinding
import java.util.*

class DateHeaderViewHolder(val view: ItemDateHeaderBinding) : ViewHolder<Date>(view.root) {

    private val dateFormat: String = DateFormatter.Template.STRING_DAY_MONTH_YEAR.get()
    var dateHeadersFormatter: DateFormatter.Formatter? = null

    override fun onBind(data: Date) {
        view.textView.text = dateHeadersFormatter?.format(data)
            ?: DateFormatter.format(data, dateFormat)
    }

}