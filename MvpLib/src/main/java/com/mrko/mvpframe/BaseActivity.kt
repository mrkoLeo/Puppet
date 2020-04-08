package com.mrko.mvpframe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.mrko.baselibrary.log.logi
import com.mrko.pet.utils.ProgressDialogHelper
import me.jessyan.autosize.AutoSizeConfig

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
 * @author:Created by Mrko on 2019-08-05.
 * @email:mrko0630@163.com
 * @description: BaseActivity
 * @since: 1.0
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open var mTAG: String = this.javaClass.simpleName
    protected var progressDialogHelper: ProgressDialogHelper? = null

    protected open fun onCreateBefore(savedInstanceState: Bundle?) {
        "onCreateBefore $savedInstanceState".logi()
    }

    protected open fun onContentViewBefore(savedInstanceState: Bundle?) {
        "onContentViewBefore $savedInstanceState".logi()
    }

    protected open fun onContentViewAfter(savedInstanceState: Bundle?) {
        if (progressDialogHelper == null) {
            progressDialogHelper = ProgressDialogHelper(this)
        }
        "onContentViewAfter $savedInstanceState".logi()
    }

    protected open fun onCreateAfter(savedInstanceState: Bundle?) {
        "onCreateAfter $savedInstanceState".logi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateBefore(savedInstanceState)
        "onCreate $savedInstanceState".logi()
        super.onCreate(savedInstanceState)
        onContentViewBefore(savedInstanceState)
        setContentView(getLayoutId())
        onContentViewAfter(savedInstanceState)
        onCreateAfter(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        "onStart".logi()

    }

    override fun onResume() {
        super.onResume()
        "onResume".logi()

    }

    override fun onPause() {
        super.onPause()
        "onPause".logi()

    }

    override fun onStop() {
        super.onStop()
        "onStop".logi()

    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logi()
    }

    override fun onRestart() {
        super.onRestart()
        "onRestart".logi()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "onNewIntent".logi()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        "onAttachedToWindow".logi()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        "onDetachedFromWindow".logi()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        "onLowMemory".logi()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        "onLowMemory".logi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        "onSaveInstanceState".logi()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        "onRestoreInstanceState".logi()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        "onSaveInstanceState".logi()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle,
        persistentState: PersistableBundle
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        "onRestoreInstanceState".logi()
    }

    // ------------------------------ 统一适配 适配方法 一般很少调用 ------------------------------

    /**
     * 需要注意的是暂停 AndroidAutoSize 后, AndroidAutoSize 只是停止了对后续还没有启动的 [Activity] 进行适配的工作
     * 但对已经启动且已经适配的 [Activity] 不会有任何影响
     *
     * @param view [View]
     */
    fun stopAutoSize() {
        "AndroidAutoSize stops working!".logi()
        AutoSizeConfig.getInstance().stop(this)
    }

    /**
     * 需要注意的是重新启动 AndroidAutoSize 后, AndroidAutoSize 只是重新开始了对后续还没有启动的 [Activity] 进行适配的工作
     * 但对已经启动且在 stop 期间未适配的 [Activity] 不会有任何影响
     *
     * @param view [View]
     */
    fun restartAutoSize() {
        "AndroidAutoSize continues to work".logi()
        AutoSizeConfig.getInstance().restart()
    }

    // ------------------------------ 统一弹框 Toast ------------------------------
    override fun showProgress(message: CharSequence, title: CharSequence, cancelable: Boolean) {
        progressDialogHelper?.showProgress(message, title, cancelable)
    }

    override fun hideProgress() {
        progressDialogHelper?.hideProgress()
    }

    override fun showMessage(msg: CharSequence) {
        progressDialogHelper?.showMessage(msg)
    }
}