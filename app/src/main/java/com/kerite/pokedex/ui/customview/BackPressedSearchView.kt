package com.kerite.pokedex.ui.customview

import android.content.Context
import android.view.KeyEvent
import androidx.appcompat.widget.SearchView

class BackPressedSearchView(context: Context) : SearchView(context) {
    private var onCloseListener: OnCloseListener? = null

    init {
        setOnCloseListener {
            onCloseListener?.onClose()
            false
        }
    }

    override fun dispatchKeyEventPreIme(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP
        ) {
            this.onActionViewCollapsed();
        }
        return super.dispatchKeyEventPreIme(event);
    }

    override fun onActionViewCollapsed() {
        super.onActionViewCollapsed()
        onCloseListener?.onClose()
    }

    fun setOnCloseListener(listener: OnCloseListener) {
        this.onCloseListener = listener
    }

    interface OnCloseListener {
        fun onClose()
    }
}
