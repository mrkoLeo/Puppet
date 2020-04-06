package com.mrko.pet

import android.os.Bundle
import com.mrko.androidlib.webview.WebViewFragment
import com.mrko.mvpframe.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
        super.onCreateAfter(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.mFrameLayout,
                WebViewFragment.newInstance(),
                WebViewFragment::class.java.simpleName
            )
            .commit()
    }
}
