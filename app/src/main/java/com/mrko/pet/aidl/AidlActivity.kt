package com.mrko.pet.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mrko.baselibrary.log.logd
import com.mrko.mvpframe.BaseActivity
import com.mrko.pet.R
import kotlinx.android.synthetic.main.activity_aidl.*
import kotlin.random.Random

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
 * @author:Created by Mrko on 2020/4/17.
 * @email:mrko0630@163.com
 * @description: AIDL 类调用测试
 * @since: 1.0
 */
class AidlActivity : BaseActivity() {
    private var mAidlInterface: IMyAidlInterface? = null

    private val mCalculateViewModel: CalculateViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CalculateViewModel::class.java)
    }
    private var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 服务连接成功回调
            mAidlInterface = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // 服务断开回调
            mAidlInterface = null
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_aidl
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
        bindService()
        initView()
    }

    private fun initView() {
        mCalculateViewModel.value1.observe(this, Observer<Number> {
            if (mCalculateViewModel.value1.value == null) {
                return@Observer
            }
            mValue1.text = (mCalculateViewModel.value1.value as Number).toString()
        })
        mCalculateViewModel.value2.observe(this, Observer<Number> {
            if (mCalculateViewModel.value2.value == null) {
                return@Observer
            }
            mValue2.text = (mCalculateViewModel.value2.value as Number).toString()
        })
        mCalculateViewModel.total.observe(this, Observer<Number> {
            if (mCalculateViewModel.total.value == null) {
                return@Observer
            }
            mTotal.text = (mCalculateViewModel.total.value as Number).toString()
        })

        mSubmit.setOnClickListener {
            val value1 = Random.nextInt(10)
            val value2 = Random.nextInt(10)
            val total = mAidlInterface?.add(value1, value2)
            mCalculateViewModel.value1.value = value1
            mCalculateViewModel.value2.value = value2
            mCalculateViewModel.total.value = total
            total?.logd()
        }
    }

    private fun bindService() {
        val intent = Intent(this@AidlActivity, MyAidlService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}