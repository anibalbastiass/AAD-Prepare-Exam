package com.anibalbastias.android.marvelapp.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.FrameLayout
import com.anibalbastias.android.marvelapp.R

/**
 * Created by anibalbastias on 14/05/18.
 */
class DotLoadingIndicatorView : View {

    /**
     * Dots amount
     */
    private var dotAmount: Int = 0

    /**
     * Drawing tools
     */
    private var primaryPaint: Paint? = null
    private var startPaint: Paint? = null
    private var endPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var backgroundBorderPaint: Paint? = null

    /**
     * Animation tools
     */
    private var animationTime: Long = 0
    private var animatedRadius: Float = 0.toFloat()
    private var isFirstLaunch = true
    private var startValueAnimator: ValueAnimator? = null
    private var endValueAnimator: ValueAnimator? = null

    /**
     * Circle size
     */
    private var dotRadius: Float = 0.toFloat()
    private var bounceDotRadius: Float = 0.toFloat()
    private var spacing: Float = 0.toFloat()
    /**
     * Circle coordinates
     */
    private var xCoordinate: Float = 0.toFloat()
    private var dotPosition: Int = 0

    /**
     * Colors
     */
    private var startColor: Int = 0
    private var endColor: Int = 0
    private var parentView: FrameLayout? = null
    private var inverseColor = Color.WHITE

    /**
     * background
     */
    private var hasBackground: Boolean = false
    private var cornerRadius: Float = dpToPx(BACKGROUND_SQUARE_CORNER_RADIUS).toFloat()
    private var squareWidth: Int = dpToPx(BACKGROUND_SQUARE_WIDTH)

    private var circlesWidth: Int = 0

    companion object {

        const val RIGHT_DIRECTION = 1
        const val LEFT_DIRECTION = -1

        fun darker(color: Int, factor: Float): Int {
            val a = Color.alpha(color)
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)

            return Color.argb(
                a,
                Math.max((r * factor).toInt(), 0),
                Math.max((g * factor).toInt(), 0),
                Math.max((b * factor).toInt(), 0)
            )
        }

        //Unit dp
        var DOT_RADIUS = 6
        var SPACING = 7
        private const val BACKGROUND_SQUARE_WIDTH = 70
        private const val BACKGROUND_SQUARE_CORNER_RADIUS = 4


    }

    /**
     * This value detect direction of circle animation direction
     * [DotProgressBar.RIGHT_DIRECTION] and [DotProgressBar.LEFT_DIRECTION]
     */
    var animationDirection: Int = 0
        internal set

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initializeAttributes(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeAttributes(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeAttributes(attrs)
        init()
    }

    constructor(context: Context) : super(context) {
        initializeAttributes(null)
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        dotRadius = dpToPx(DOT_RADIUS).toFloat()
        spacing = dpToPx(SPACING).toFloat()
        bounceDotRadius = dotRadius + dotRadius / 3
        circlesWidth = (dotAmount * (dotRadius * 2) + spacing * (dotAmount - 1) + (bounceDotRadius - dotRadius) *
                2).toInt()

        if (hasBackground) {
            //square background
            setMeasuredDimension(squareWidth, squareWidth)
        } else {
            setMeasuredDimension(circlesWidth, (bounceDotRadius * 2).toInt())
        }
        xCoordinate = ((measuredWidth - circlesWidth) / 2).toFloat() + bounceDotRadius
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        //get primary color
        val typedValue = TypedValue()
        val sa = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
        val primaryColor = sa.getColor(0, 0)
        sa.recycle()

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.DotProgressBar,
                0, 0
            )

            try {
                setDotAmount(a.getInteger(R.styleable.DotProgressBar_amount, 3))
                setAnimationTime(
                    animationTime = a.getInteger(
                        R.styleable.DotProgressBar_duration,
                        300
                    ).toLong()
                )
                setStartColor(
                    a.getInteger(
                        R.styleable.DotProgressBar_startColor,
                        primaryColor

                    )
                )
                setEndColor(
                    a.getInteger(
                        R.styleable.DotProgressBar_endColor,
                        primaryColor
                    )
                )
                animationDirection = a.getInt(R.styleable.DotProgressBar_animationDirection, 1)
            } finally {
                a.recycle()
            }

        } else {
            setDotAmount(3)
            setAnimationTime(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
            setStartColor(primaryColor)
            setEndColor(primaryColor)
            animationDirection = 1
        }
    }

    private fun init() {
        primaryPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        primaryPaint!!.color = startColor
        primaryPaint!!.strokeJoin = Paint.Join.ROUND
        primaryPaint!!.strokeCap = Paint.Cap.ROUND
        primaryPaint!!.strokeWidth = 5f

        startPaint = Paint(primaryPaint)
        endPaint = Paint(primaryPaint)

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        //Fill
        backgroundPaint!!.style = Paint.Style.FILL
        backgroundPaint!!.color = Color.WHITE
        //border
        backgroundBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        backgroundBorderPaint!!.style = Paint.Style.STROKE
        backgroundBorderPaint!!.color = 0xEEEEEE
        backgroundBorderPaint!!.strokeWidth = dpToPx(1).toFloat(

        )
        startValueAnimator = ValueAnimator.ofInt(startColor, endColor)
        startValueAnimator!!.duration = animationTime
        startValueAnimator!!.setEvaluator(ArgbEvaluator())
        startValueAnimator!!.addUpdateListener { animation -> startPaint!!.color = animation.animatedValue as Int }

        endValueAnimator = ValueAnimator.ofInt(endColor, startColor)
        endValueAnimator!!.duration = animationTime
        endValueAnimator!!.setEvaluator(ArgbEvaluator())
        endValueAnimator!!.addUpdateListener { animation -> endPaint!!.color = animation.animatedValue as Int }

        dotRadius = dpToPx(DOT_RADIUS).toFloat()
        spacing = dpToPx(SPACING).toFloat()
        bounceDotRadius = dotRadius + dotRadius / 3
        circlesWidth = (dotAmount * (bounceDotRadius * 2) + spacing * (dotAmount - 1)).toInt()
    }

    /**
     * setters
     */
    private fun setDotAmount(amount: Int) {
        this.dotAmount = amount
    }

    private fun setStartColor(@ColorInt color: Int) {
        this.startColor = color
    }

    private fun setEndColor(@ColorInt color: Int) {
        this.endColor = color
    }

    private fun setAnimationTime(animationTime: Long) {
        this.animationTime = animationTime
    }

    private fun setDotPosition(dotPosition: Int) {
        this.dotPosition = dotPosition
    }

    /**
     * Set amount of dots, it will be restarted your view
     *
     * @param amount number of dots, dot size automatically adjust
     */
    fun changeDotAmount(amount: Int) {
        stopAnimation()
        setDotAmount(amount)
        setDotPosition(0)
        reinitialize()
    }

    /**
     * It will be restarted your view
     */
    fun changeStartColor(@ColorInt color: Int) {
        stopAnimation()
        setStartColor(color)
        reinitialize()
    }

    /**
     * It will be restarted your view
     */
    fun changeEndColor(@ColorInt color: Int) {
        stopAnimation()
        setEndColor(color)
        reinitialize()
    }

    /**
     * It will be restarted your view
     */
    fun changeAnimationTime(animationTime: Long) {
        stopAnimation()
        setAnimationTime(animationTime)
        reinitialize()
    }

    /**
     * Change animation direction, doesn't restart view.
     *
     * @param animationDirection left or right animation direction
     */
    fun changeAnimationDirection(@AnimationDirection animationDirection: Int) {
        this@DotLoadingIndicatorView.animationDirection = animationDirection
    }

    fun showAsFullScreen(hasBackground: Boolean = false) {
        show(null, hasBackground, false)
    }

    fun showOnView(anchorView: View, colorInverse: Boolean = false) {
        show(anchorView, false, colorInverse)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun show(
        anchorView: View?,
        background: Boolean = false,
        inverse: Boolean = false
    ) {
        if (isShown) return
        this.hasBackground = background
        if (inverse) {
            setStartColor(inverseColor)
            changeStartColor(inverseColor)
            changeEndColor(inverseColor)
            primaryPaint!!.color = inverseColor

        }
        parentView = FrameLayout(context)
        var lp: FrameLayout.LayoutParams
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var windowLayoutParams = WindowManager.LayoutParams()
        windowLayoutParams.format = PixelFormat.TRANSLUCENT
        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

        if (anchorView == null) {
            windowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            windowLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            if (hasBackground) {
                parentView!!.setBackgroundColor(Color.argb(127, 0, 0, 0))
            }
            lp =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        } else {
            if (inverse) {
                windowLayoutParams.width = anchorView.width
                windowLayoutParams.height = anchorView.height
                parentView!!.background = anchorView.background
            } else {
                windowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
                windowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            windowLayoutParams.gravity = Gravity.START or Gravity.TOP
            computePosition(anchorView, windowLayoutParams, inverse)

            lp =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        }


        lp.gravity = Gravity.CENTER

        parentView!!.addView(this, lp)
        parentView!!.setOnTouchListener { _, _ -> true }

        if (!(context as? Activity)?.isFinishing!!) {
            try {
                wm.addView(parentView, windowLayoutParams)
                anchorView?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(v: View?) {
                    }

                    override fun onViewDetachedFromWindow(v: View?) {
                        remove()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun remove() {
        if (!isShown) return
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        parentView!!.removeAllViews()
        wm.removeView(parentView)
    }

    private fun drawBackground(canvas: Canvas) {
        //Fill
        val r = RectF(
            0.toFloat(), 0.toFloat(), squareWidth.toFloat()
            , squareWidth.toFloat()
        )
        canvas.drawRoundRect(r, cornerRadius, cornerRadius, backgroundPaint)
        //border
        canvas.drawRoundRect(r, cornerRadius, cornerRadius, backgroundBorderPaint)
    }

    private fun computePosition(anchorView: View, outParams: WindowManager.LayoutParams, inverse: Boolean) {
        val offsetX = anchorView.width / 2  // Center on the view horizontally.
        val offsetY = anchorView.height / 2
        val tmpAnchorPos = IntArray(2)
        anchorView.getLocationInWindow(tmpAnchorPos)

        if (inverse) {
            outParams.x = tmpAnchorPos[0]
            outParams.y = tmpAnchorPos[1]

        } else {
            outParams.x = tmpAnchorPos[0] + offsetX - circlesWidth / 2
            outParams.y = tmpAnchorPos[1] - offsetY + bounceDotRadius.toInt()
        }
    }

    /**
     * Reinitialize animators instances
     */
    private fun reinitialize() {
        init()
        requestLayout()
        startAnimation()
    }

    private fun drawCirclesLeftToRight(canvas: Canvas, radius: Float) {
        var step = 0f
        for (i in 0 until dotAmount) {
            drawCircles(canvas, i, step, radius)
            step += dotRadius * 2 + spacing
        }
    }

    private fun drawCirclesRightToLeft(canvas: Canvas, radius: Float) {
        var step = 0f
        for (i in dotAmount - 1 downTo 0) {
            drawCircles(canvas, i, step, radius)
            step += dotRadius * 2 + spacing
        }
    }

    private fun drawCircles(canvas: Canvas, i: Int, step: Float, radius: Float) {
        if (dotPosition == i) {
            drawCircleUp(canvas, step, radius)
        } else {
            if (i == dotAmount - 1 && dotPosition == 0 && !isFirstLaunch || dotPosition - 1 == i) {
                drawCircleDown(canvas, step, radius)
            } else {
                drawCircle(canvas, step)
            }
        }
    }

    /**
     * Circle radius is grow
     */
    private fun drawCircleUp(canvas: Canvas, step: Float, radius: Float) {
        canvas.drawCircle(
            xCoordinate + step,
            (measuredHeight / 2).toFloat(),
            dotRadius + radius,
            startPaint!!
        )
    }

    private fun drawCircle(canvas: Canvas, step: Float) {
        canvas.drawCircle(
            xCoordinate + step,
            (measuredHeight / 2).toFloat(),
            dotRadius,
            primaryPaint!!
        )
    }

    /**
     * Circle radius is decrease
     */
    private fun drawCircleDown(canvas: Canvas, step: Float, radius: Float) {
        canvas.drawCircle(
            xCoordinate + step,
            (measuredHeight / 2).toFloat(),
            bounceDotRadius - radius,
            endPaint!!
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (hasBackground) {
            drawBackground(canvas)
        }
        if (animationDirection < 0) {
            drawCirclesRightToLeft(canvas, animatedRadius)
        } else {
            drawCirclesLeftToRight(canvas, animatedRadius)
        }
    }

    private fun stopAnimation() {
        this.clearAnimation()
        postInvalidate()
    }

    private fun startAnimation() {
        val bounceAnimation = BounceAnimation()
        bounceAnimation.duration = animationTime
        bounceAnimation.repeatCount = Animation.INFINITE
        bounceAnimation.interpolator = LinearInterpolator()
        bounceAnimation.setAnimationListener(object : AnimationListener() {
            override fun onAnimationRepeat(animation: Animation) {
                dotPosition++
                if (dotPosition == dotAmount) {
                    dotPosition = 0
                }

                startValueAnimator!!.start()

                if (!isFirstLaunch) {
                    endValueAnimator!!.start()
                }

                isFirstLaunch = false
            }
        })
        startAnimation(bounceAnimation)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            stopAnimation()
        } else {
            startAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        stopAnimation()
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    @kotlin.annotation.Retention
    @IntDef(RIGHT_DIRECTION, LEFT_DIRECTION)
    internal annotation class AnimationDirection

    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            animatedRadius = (bounceDotRadius - dotRadius) * interpolatedTime
            invalidate()
        }
    }

    internal abstract inner class AnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            // stub
        }

        override fun onAnimationEnd(animation: Animation) {
            // stub
        }
    }
}