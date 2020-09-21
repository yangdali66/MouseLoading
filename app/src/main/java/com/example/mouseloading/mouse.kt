package com.example.mouseloading

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class mouse: View {
    //圆弧的画笔
    private val mPaint: Paint by lazy {
        Paint().apply {
            color = Color.MAGENTA
            style = Paint.Style.FILL
        }
    }


    //小球的变化移动因子
    private var ballTranslateX = 0f
    private var ballMoveAnim:ValueAnimator?=null

    //定义 动画集
    private val animators =AnimatorSet()
    //嘴巴张开闭合的动画因子
    private var mouseAngle = 0f

    //小球的半径
    private var ballRadius = 0f

    //两个球之间的间距
    private var space = 0f

    //嘴巴的半径
    private var mouseRadius = 0f

    //圆弧的中心点坐标
    private var cx = 0f
    private var cy = 0f

    //代码创建
    constructor(context: Context) : super(context) {}

    //xml创建
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //确定小球半径
        if (width >= height) {
            //高度相同
            ballRadius = height / 6f

            //确定宽度是否能包容
            if (ballRadius * 8.5f > width) {
                ballRadius = width / 8.5f
            }
        }
        //宽度相同
        else {
            ballRadius = width / 8.5f
            //确定高度是否能包容
            if (ballRadius * 6f > height) {
                ballRadius = height / 6f
            }
        }
        mouseRadius = ballRadius * 3
        space = ballRadius / 2

        //确定中心点坐标
        cx = ((width - 8.5 * ballRadius) / 2 + 3 * ballRadius).toFloat()
        cy = height / 2f
    }
    /*
        跨平台 Android iOS
        Flutter
        JS
        后台

        创建对象
        复杂的计算
        不要放在onDraw
     */

    override fun onDraw(canvas: Canvas?) {
        //绘制圆弧
        canvas?.drawArc(
            cx - mouseRadius,
            cy - mouseRadius,
            cx + mouseRadius,
            cy + mouseRadius,
            mouseAngle, 360f - 2 * mouseAngle,
            true,
            mPaint
        )

        //开始绘制圆形
        canvas?.drawCircle(cx + 4.5f * ballRadius-ballTranslateX, cy, ballRadius, mPaint)
    }

    //定义一个变量保存动画对象
    private var mouseMoveAnim: ValueAnimator? = null

    //创建动画

    private fun creatAction() {
        if (mouseMoveAnim == null) {
            mouseMoveAnim = ValueAnimator.ofFloat(0f, 45f,0f).apply {
                duration = 500
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    mouseAngle = it.animatedValue as Float

                    invalidate()
                }
            }
        }
        if (ballMoveAnim == null){
            ballMoveAnim = ValueAnimator.ofFloat(0f,4.5f*ballRadius).apply {
                duration = 500
                repeatCount=ValueAnimator.INFINITE
                addUpdateListener {
                    ballTranslateX = it.animatedValue as Float
                    invalidate()
                }
            }
        }

        //同时执行
        animators.playTogether(mouseMoveAnim,ballMoveAnim)

    }

    //开始启动动画（kotlin所写）
    fun startAnim() {
        creatAction()
        if (animators.isPaused) {
            animators.resume()
        }else{
            animators.start()
        }
    }


    //关闭动画
    fun stopAnim() {

        animators.pause()
    }
    /*
    拆分为多个部分
    寻找关系
    每个部分的绘制
    寻找动画因子
    创建动画
 */
}