package com.northious.batteryview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BatteryView : View {
    private val mBodySkeletonPaint by lazy {
        Paint().apply {
            color = mBodySkeletonColor
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
    }

    private val mBodyFillPaint by lazy {
        Paint().apply {
            color = mBodySkeletonColor
            style = Paint.Style.FILL
            strokeWidth = 1f
        }
    }

    private val mHeaderPaint by lazy {
        Paint().apply {
            color = mHeaderColor
            style = Paint.Style.FILL
        }
    }
    private var mBodySkeletonColor: Int = Color.parseColor("#E1F2FE")
    private var mBodyFillColor: Int = Color.parseColor("#D3D3D3")
    private var mBodySkeletonRadius: Float = 0f
    private var mBodyFillRadius: Float = 0f
    private var mGap: Float = 0f

    private var mDistance = 0f

    private var mHeaderColor: Int = Color.parseColor("#E1F2FE")
    private var mHeaderRadius: Float = 0f

    // 比例
    private var mWidthRadio = 8f
    private var mHeightRadio = 3f
    private var mRadiusRadio = 4f
    private var mDistanceRadio = 30f

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initParams(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initParams(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = -1
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initParams(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun initParams(attrs: AttributeSet?) {
        attrs ?: return
        context.obtainStyledAttributes(attrs, R.styleable.BatteryView).apply {
            mBodySkeletonColor =
                getColor(R.styleable.BatteryView_body_skeleton_color, Color.parseColor("#E1F2FE"))
            mBodyFillColor =
                getColor(R.styleable.BatteryView_body_fill_color, Color.parseColor("#D3D3D3"))
            val base = getFloat(R.styleable.BatteryView_base_value, 5f)
            mBodySkeletonRadius =
                getDimension(R.styleable.BatteryView_body_skeleton_radius, base * 8)
            mBodyFillRadius =
                getDimension(R.styleable.BatteryView_body_fill_radius, mBodySkeletonRadius)
            mGap = getDimension(R.styleable.BatteryView_gap, base)
            mDistance = getDimension(R.styleable.BatteryView_distance, base * 3)

            mHeaderColor =
                getColor(R.styleable.BatteryView_header_color, Color.parseColor("#E1F2FE"))
            mHeaderRadius = getDimension(R.styleable.BatteryView_header_radius, base * 10)
            mWidthRadio = getDimension(R.styleable.BatteryView_width_radio, 8f)
            mHeightRadio = getDimension(R.styleable.BatteryView_height_radio, 3f)
            mRadiusRadio = getDimension(R.styleable.BatteryView_radius_radio, 4f)
            mDistanceRadio = getDimension(R.styleable.BatteryView_distance_radio, 30f)
            recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        val height = measuredHeight.toFloat()
        val width = measuredWidth.toFloat()
        val headerWidth = width / mWidthRadio
        val bodyWidth = width - headerWidth
        val headerHeight = height / mHeightRadio
        mHeaderRadius = headerWidth / mRadiusRadio
        mDistance = bodyWidth / mDistanceRadio

        drawBodySkeleton(canvas, bodyWidth, height, mBodySkeletonRadius)
        drawBodyFill(canvas, bodyWidth, height)
        drawHeader(canvas, headerWidth, headerHeight, mHeaderRadius, bodyWidth)
    }

    private fun drawBodySkeleton(canvas: Canvas, width: Float, height: Float, radius: Float) {
        canvas.drawRoundRect(
            0f,
            0f,
            width,
            height,
            radius,
            radius,
            mBodySkeletonPaint
        )
    }

    private fun drawBodyFill(canvas: Canvas, width: Float, height: Float) {
        canvas.drawRoundRect(
            mGap,
            mGap,
            width - mGap,
            height - mGap,
            mBodySkeletonRadius,
            mBodySkeletonRadius,
            mBodyFillPaint
        )
    }

    private fun drawHeader(
        canvas: Canvas,
        headerWidth: Float,
        headerHeight: Float,
        radius: Float,
        offset: Float
    ) {
        canvas.save()
        canvas.translate(offset, 0f)
        val topY = (measuredHeight - headerHeight) / 2f
        val bottomY = topY + headerHeight
        canvas.drawRoundRect(
            mDistance,
            topY,
            headerWidth,
            bottomY,
            radius,
            radius,
            mHeaderPaint
        )
        canvas.restore()
    }

}