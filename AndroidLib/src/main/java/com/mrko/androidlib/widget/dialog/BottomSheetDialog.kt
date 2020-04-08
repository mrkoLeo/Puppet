package com.mrko.androidlib.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.mrko.baselibrary.log.logd


/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author:Created by Mrko on 2019-09-24.
 * @email:mrko0630@163.com
 * @description: BottomSheetDialog 处理
 * @since: 1.0
 */


////////////////////////////////////////////////////////////////////////////////////////////////
////  description: 类 BottomSheetDialogListView
////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * 参考 https://github.com/af913337456/WangyiyunBottomSheetDialog/blob/c6cd85d384eff1acef998b3a2b8c713accba8a72/app/src/main/java/com/accurme/wangyiyunbottomsheetdialog/SpringBackBottomSheetDialog.java
 * 1，能够在 BottomSheetDialog 分展开的情况下，滑动显示完整数据
 * 2，能够像网易云音乐一样，上滑的时候，避免触发 expend 状态
 *
 */

class BottomSheetDialogListView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ListView(context, attrs, defStyleAttr) {


    private var downY: Float = 0.toFloat()
    private var moveY: Float = 0.toFloat()
    var isOverScroll = false
    private var bottomCoordinator: CoordinatorLayout? = null

    fun setCoordinatorDisallow() {
        if (bottomCoordinator == null)
            return
        bottomCoordinator!!.requestDisallowInterceptTouchEvent(true)
    }

    /**
     * 绑定需要被拦截 intercept 的 CoordinatorLayout
     * @param contentView View
     */
    fun bindBottomSheetDialog(contentView: View) =// try throw illegal
        try {
            val parentOne = contentView.parent as FrameLayout
            this.bottomCoordinator = parentOne.parent as CoordinatorLayout
            setOnTouchListener(
                object : OnTouchListener {
                    @SuppressLint("ClickableViewAccessibility")
                    override fun onTouch(v: View, event: MotionEvent): Boolean {
                        if (bottomCoordinator == null)
                            return false
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                "child onTouch ACTION_DOWN".logd()
                                downY = event.rawY
                                bottomCoordinator!!.requestDisallowInterceptTouchEvent(true)
                            }

                            MotionEvent.ACTION_MOVE -> {
                                moveY = event.rawY
                                "child onTouch ACTION_MOVE firstVisiblePos $firstVisiblePosition -- isOverScroll $isOverScroll".logd()

                                if (moveY - downY > 10) {
                                    "child onTouch 不阻断".logd()
                                    // coordinator.requestDisallowInterceptTouchEvent(true);
                                    if (firstVisiblePosition == 0 && isOverScroll) {
                                        bottomCoordinator!!.requestDisallowInterceptTouchEvent(false)
                                        return false
                                    }
                                }
                                "child onTouch 阻断".logd()
                                bottomCoordinator!!.requestDisallowInterceptTouchEvent(true)
                            }

                            MotionEvent.ACTION_UP -> "child onTouch ACTION_UP".logd()
                        }
                        return false
                    }
                }
            )
        } catch (e: Exception) {
            // maybe 可能是强转异常
        }

    @SuppressLint("WrongConstant")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (bottomCoordinator == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        val size = ((resources.displayMetrics.heightPixels * 0.618) as Float).toInt()
        val newHeightSpec = MeasureSpec.makeMeasureSpec(
            size,
            Integer.MIN_VALUE
        )
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        isOverScroll = clampedY
    }

}


