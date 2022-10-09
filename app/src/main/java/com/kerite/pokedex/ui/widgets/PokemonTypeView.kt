package com.kerite.pokedex.ui.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.kerite.pokedex.R
import com.kerite.pokedex.model.enums.PokemonType
import com.kerite.pokedex.util.dp
import kotlin.math.min

class PokemonTypeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private lateinit var mType: PokemonType

    private val mBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val mIconPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val mOverlayPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    private lateinit var mTypeString: String

    private val mBackgroundRect = RectF()
    private val mIconRect = Rect()
    private val mTextBounds = Rect()
    private val mOverlayPath: Path = Path()
    private val mTextPaint = Paint()

    private var mRadius: Float = 0f
    private lateinit var mIconDrawable: Drawable

    var type
        get() = mType
        set(value) {
            mTypeString = context.getString(value.nameRes)
            mBackgroundPaint.color = context.getColor(value.colorRes)
            mIconDrawable = ResourcesCompat.getDrawable(resources, value.iconRes, null)!!
            invalidate()
        }

    init {
        context.obtainStyledAttributes(
            attrs, R.styleable.PokemonTypeView, defStyleAttr, defStyleRes
        ).apply {
            type = PokemonType.values[getInt(R.styleable.PokemonTypeView_pokemonType, 0)]
            recycle()
        }
        mBackgroundPaint.style = Paint.Style.FILL
        mOverlayPaint.color = context.getColor(R.color.type_background_filter)
        mTextPaint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBackgroundRect.apply {
            left = 0f
            top = 0f
            right = w.toFloat()
            bottom = h.toFloat()
        }

        mTextPaint.textSize = h.toFloat() - 8.dp

//        val iconRight = w / 3f - w / 20f
//        val iconLeft = w / 20f
//        val iconTop = h / 4f
//        val iconBottom = h - iconTop

        val iconMiddleX = w / 6f
        val iconMiddleY = h / 2f

        val iconSize = min(h * 1f, w / 3f)

        mIconRect.apply {
            left = (iconMiddleX - iconSize / 2).toInt()
            top = (iconMiddleY - iconSize / 2).toInt()
            right = (iconMiddleX + iconSize / 2).toInt()
            bottom = (iconMiddleY + iconSize / 2).toInt()
        }

        mRadius = min(h, w) / 4f

        mOverlayPath.apply {
            reset()
            moveTo(w / 3f - 4.dp, h.toFloat())
            lineTo(w / 3f + 4.dp, 0f)
            lineTo(w - mRadius, 0f)
            arcTo(
                RectF(w - mRadius * 2, 0f, w.toFloat(), mRadius * 2),
                -90f, 90f
            )
            lineTo(w.toFloat(), h - mRadius)
            arcTo(
                RectF(w - mRadius * 2, h - mRadius * 2, w.toFloat(), h.toFloat()),
                0f, 90f
            )
            close()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val radius = min(height, width) / 4f
//        canvas.drawRoundRect(mRectBackground, (height / 4f).dp, (height / 4f).dp, mBackgroundPaint)
        canvas.drawRoundRect(mBackgroundRect, radius, radius, mBackgroundPaint)
//        canvas.drawRect(mRectIcon, mTextPaint)

        canvas.drawPath(mOverlayPath, mOverlayPaint)
        mIconDrawable.bounds = mIconRect
        mIconDrawable.draw(canvas)

        // TODO optimize performance
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.getTextBounds(mTypeString, 0, mTypeString.length, mTextBounds)
        val fontHeight = (mTextPaint.fontMetrics.top
                + mTextPaint.fontMetrics.bottom)

        canvas.drawText(
            mTypeString,
            width * 2f / 3,
            mBackgroundRect.centerY() - fontHeight / 2,
            mTextPaint
        )
    }
}