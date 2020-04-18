package com.mrko.pet.utils

import android.app.Activity
import android.app.ProgressDialog
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mrko.baselibrary.R
import java.lang.ref.WeakReference

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
 * @author:Created by Mrko on 2019-08-11.
 * @email:mrko0630@163.com
 * @description: 统一封装的转圈dialog
 * @since: 1.0
 */
class ProgressDialogHelper(context: Activity) {

    private lateinit var progressDialog: ProgressDialog
    private val reference: WeakReference<Activity> = WeakReference(context)

    fun showProgress(
        message: CharSequence = "请稍等",
        title: CharSequence = "",
        cancelable: Boolean = true
    ) {
        var temp = message
        val context = reference.get() ?: return

        if (!::progressDialog.isInitialized) {
            temp = SpanUtils.setForeground(
                temp.toString(),
                temp.toString(),
                ContextCompat.getColor(context, R.color.material_grey_600)
            )
            progressDialog = ProgressDialog(context)
            progressDialog.setMessage(temp)
            progressDialog.setTitle(title)
            progressDialog.setCancelable(cancelable)
            progressDialog.isIndeterminate = true
        }
        progressDialog.setCancelable(cancelable)
        if (!progressDialog.isShowing && !context.isFinishing) {
            progressDialog.show()
        }
    }

    fun hideProgress() {
        val context = reference.get() ?: return
        if (::progressDialog.isInitialized && !context.isFinishing && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun showMessage(msg: CharSequence) {
        val context = reference.get() ?: return
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun release() {
        if (::progressDialog.isInitialized) {
            progressDialog.dismiss()
            reference.clear()
        }
    }
}