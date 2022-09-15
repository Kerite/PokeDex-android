package com.kerite.pokedex.ui.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatTextView
import com.kerite.pokedex.R

class SelectableTextView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle), OnClickListener {
    companion object {
        private const val STATE_SELECTED = "selected"
    }

    private var mChecked: Boolean
    private var mMaskColor: Int
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    var checked
        get() = mChecked
        set(value) {
            mChecked = value
            invalidate()
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.SelectableTextView, defStyle, 0
        ).apply {
            try {
                mChecked = getBoolean(R.styleable.SelectableTextView_checked, false)
                mMaskColor = getColor(
                    R.styleable.SelectableTextView_maskColor,
                    Color.parseColor("#8a000000")
                )
            } finally {
                recycle()
            }
        }
        setOnClickListener(this)
    }

    override fun onClick(v: View) {
        checked = !checked
        Log.d("SelectableTextView", mChecked.toString())
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        val state = Bundle()
        state.putBoolean(STATE_SELECTED, mChecked)
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
        if (state is Bundle) {
            mChecked = state.getBoolean(STATE_SELECTED)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mChecked) {
            canvas.drawColor(mMaskColor)
        }
    }
}