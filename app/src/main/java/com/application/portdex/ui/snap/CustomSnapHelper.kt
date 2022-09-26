package com.application.portdex.ui.snap

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class CustomSnapHelper(
    private val snapListener: SnapItemListener? = null
) : PagerSnapHelper() {

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager is LinearLayoutManager) {
            return if (layoutManager.canScrollHorizontally()) {
                getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else {
                getStartView(layoutManager, getVerticalHelper(layoutManager))
            }
        }
        return super.findSnapView(layoutManager)
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int, velocityY: Int
    ): Int {
        val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val position = layoutManager.getPosition(centerView)
        var targetPosition = -1
        if (layoutManager.canScrollHorizontally()) {
            targetPosition = if (velocityX < 0) position - 1 else position + 1
        }

        if (layoutManager.canScrollVertically()) {
            targetPosition = if (velocityY < 0) position - 1 else position + 1
        }

        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        targetPosition = min(lastItem, max(targetPosition, firstItem))
        if (targetPosition >= 0) snapListener?.onItemSnap(targetPosition)
        return targetPosition
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager, helper: OrientationHelper
    ): View? {
        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()
            val isLastItem = (layoutManager.findLastCompletelyVisibleItemPosition()
                    == layoutManager.itemCount - 1)
            if (firstChild == RecyclerView.NO_POSITION || isLastItem) return null

            val child = layoutManager.findViewByPosition(firstChild)
            return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                && helper.getDecoratedEnd(child) > 0
            ) child
            else {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) null
                else layoutManager.findViewByPosition(firstChild + 1)
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        return mVerticalHelper ?: OrientationHelper.createVerticalHelper(layoutManager)
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        return mHorizontalHelper ?: OrientationHelper.createHorizontalHelper(layoutManager)
    }
}