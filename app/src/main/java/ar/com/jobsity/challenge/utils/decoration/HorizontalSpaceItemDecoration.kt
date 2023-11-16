package ar.com.jobsity.challenge.utils.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HorizontalSpaceItemDecoration(private val horizontalSpaceHeight: Int = 0) :
    RecyclerView.ItemDecoration() {

//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        outRect.top = 0
//        outRect.bottom = 0
//        outRect.right = horizontalSpaceHeight
//
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.left = horizontalSpaceHeight * 2
//        }
//    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val itemCount = state.itemCount
        var left = 0
        var top = 0
        var right = horizontalSpaceHeight
        var bottom = 0


        if (itemPosition == 0) {
            left += horizontalSpaceHeight * 2
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            right += horizontalSpaceHeight
        }

        if (!isReverseLayout(parent)) {
            outRect.set(left, top, right, bottom)
        } else {
            outRect.set(right, bottom, left, top)
        }

    }

    private fun isReverseLayout(parent: RecyclerView): Boolean {
        if (parent.layoutManager is LinearLayoutManager) {
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            return layoutManager!!.reverseLayout
        } else {
            throw IllegalStateException("PaddingItemDecoration can only be used with a LinearLayoutManager.")
        }
    }
}
