package com.application.portdex.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener


class ScrollAwareFABBehavior(context: Context?, attrs: AttributeSet?) :
    FloatingActionButton.Behavior() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(
                    coordinatorLayout, child, directTargetChild, target, axes, type
                )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        if (dy > 0 && child.visibility == View.VISIBLE) {
            child.hide(object : OnVisibilityChangedListener() {
                override fun onHidden(floatingActionButon: FloatingActionButton) {
                    super.onHidden(floatingActionButon)
                    floatingActionButon.visibility = View.INVISIBLE
                }
            })
        } else if (dy < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }
}