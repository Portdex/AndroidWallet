package com.application.portdex.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.portdex.R
import com.application.portdex.core.utils.EqualSpacingItemDecoration
import com.application.portdex.core.utils.GenericUtils.inVisible
import com.application.portdex.core.utils.GenericUtils.show
import com.application.portdex.core.utils.GridSpacingItemDecoration
import com.application.portdex.core.utils.ImageUtils.toPx
import com.application.portdex.databinding.RecyclerFreelancerViewBinding
import com.application.portdex.databinding.RecyclerLabeledViewBinding

object CustomUi {


    fun Fragment.getGridLabeledView(
        label: String,
        count: Int = 3,
        space: Int = 8,
        seeAll: (() -> Unit)? = null
    ): RecyclerLabeledViewBinding {
        return requireContext().getRecyclerLabeledView(label, seeAll).apply {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), count)
            recyclerView.addItemDecoration(GridSpacingItemDecoration(3, space.toPx()))
        }
    }

    fun FragmentActivity.getGridLabeledView(
        label: String,
        count: Int = 3,
        space: Int = 8,
        seeAll: (() -> Unit)? = null
    ): RecyclerLabeledViewBinding {
        return getRecyclerLabeledView(label, seeAll).apply {
            recyclerView.layoutManager = GridLayoutManager(this@getGridLabeledView, count)
            recyclerView.addItemDecoration(GridSpacingItemDecoration(3, space.toPx()))
        }
    }

    fun Fragment.getHorizontalLabeledView(
        label: String,
        seeAll: (() -> Unit)? = null
    ): RecyclerLabeledViewBinding {
        return requireContext().getRecyclerLabeledView(label, seeAll).apply {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
            recyclerView.addItemDecoration(EqualSpacingItemDecoration(8.toPx(), 0))
        }
    }

    fun Fragment.getFreelancerLabeledView(
        label: String,
        count: Int = 3,
        connect: (() -> Unit)? = null
    ): RecyclerFreelancerViewBinding {
        return RecyclerFreelancerViewBinding.inflate(LayoutInflater.from(requireContext())).apply {
            labelView.text = label
            btnConnect.show(connect != null)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), count)
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.addItemDecoration(GridSpacingItemDecoration(count, 8.toPx()))
            root.tag = label
        }
    }

    private fun Context.getRecyclerLabeledView(
        label: String, seeAll: (() -> Unit)? = null
    ): RecyclerLabeledViewBinding {
        return RecyclerLabeledViewBinding.inflate(LayoutInflater.from(this)).apply {
            labelView.text = label
            recyclerView.itemAnimator = DefaultItemAnimator()
            btnSeeAll.inVisible(seeAll == null)
            btnSeeAll.setOnClickListener { seeAll?.invoke() }
            root.tag = label
        }
    }


    fun View.getViewAdapter(label: String?): RecyclerView.Adapter<*>? {
        val view = findViewWithTag<ViewGroup>(label)
        return view?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter
    }
}