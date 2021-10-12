package dev.diegodc.moviesapp.core.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnScrollLoadMore(
    private val layoutManager: GridLayoutManager,
    private val callback: () -> Unit
) : RecyclerView.OnScrollListener() {

    var load: Boolean = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        load = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
            if (load) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    load = false
                    callback.invoke()
                }
            }
        }
    }
}