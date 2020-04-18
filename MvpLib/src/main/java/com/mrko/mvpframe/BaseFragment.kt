package com.mrko.mvpframe

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mrko.baselibrary.log.logi
import com.mrko.pet.utils.ProgressDialogHelper

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
 * @author:Created by Mrko on 2019-08-10.
 * @email:mrko0630@163.com
 * @description: BaseFragment
 * @since: 1.0
 */
abstract class BaseFragment : Fragment(), BaseView, IActivityBackPressed {
    protected open var mTAG: String = this.javaClass.simpleName
    protected var mContext: Context? = null
    private val mStateSaveIsHidden = "STATE_SAVE_IS_HIDDEN"
    protected var progressDialogHelper: ProgressDialogHelper? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        "onAttach".logi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(mStateSaveIsHidden)
            val ft = fragmentManager!!.beginTransaction()

            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
        "onCreate $savedInstanceState".logi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        "onCreateView $savedInstanceState".logi()
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (progressDialogHelper == null) {
            progressDialogHelper = ProgressDialogHelper(activity as Activity)
        }
        "onViewCreated".logi()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        "onActivityCreated $savedInstanceState".logi()
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

    override fun onDestroyView() {
        super.onDestroyView()
        "onDestroyView".logi()
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logi()
    }

    override fun onDetach() {
        super.onDetach()
        "onDetach".logi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(mStateSaveIsHidden, isHidden)
        "onSaveInstanceState".logi()
    }


    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun lazyLoad()

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

interface IActivityBackPressed {
    fun onActivityBackPressed(): Boolean = false
}