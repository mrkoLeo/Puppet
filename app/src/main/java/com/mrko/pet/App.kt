package com.mrko.pet

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.mrko.baselibrary.log.Logger
import com.mrko.baselibrary.log.logi
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.DefaultAutoAdaptStrategy
import me.jessyan.autosize.external.ExternalAdaptManager
import me.jessyan.autosize.internal.CustomAdapt
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.utils.LogUtils
import me.jessyan.autosize.utils.ScreenUtils
import java.util.*

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
 * @author:Created by Mrko on 2019-08-07.
 * @email:mrko0630@163.com
 * @description:
 * @since: 1.0
 */
class App : Application(), Application.ActivityLifecycleCallbacks, AppCallBack {
    /**
     * activities 收集Activity
     */
    private lateinit var activities: Stack<Activity>

    companion object {
        private lateinit var mApp: App
        private lateinit var mContext: Context
        /**
         * 当前activity 前台统计
         */
        private var foregroundCount: Int = 0

        fun getApp(): App {
            return mApp
        }

        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        activities = Stack()
        mApp = this
        mContext = applicationContext
        AppInitHelper().initApp()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activities.add(activity)
        "${activity?.localClassName} onActivityCreated $savedInstanceState".logi()
    }

    override fun onActivityStarted(activity: Activity?) {
        foregroundCount++
        "${activity?.localClassName} onActivityStarted".logi()
    }

    override fun onActivityPaused(activity: Activity?) {
        "${activity?.localClassName} onActivityPaused".logi()

    }

    override fun onActivityStopped(activity: Activity?) {
        foregroundCount--
        "${activity?.localClassName} onActivityStopped".logi()
    }

    override fun onActivityDestroyed(activity: Activity?) {
        "${activity?.localClassName} onActivityDestroyed".logi()
        activities.remove(activity)
    }

    override fun onActivityResumed(activity: Activity?) {
        "${activity?.localClassName} onActivityResumed".logi()
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        "${activity?.localClassName} onActivitySaveInstanceState $outState".logi()
    }

    override fun getStackActivity(): Stack<Activity> {
        return activities
    }
}

interface AppCallBack {
    fun getStackActivity(): Stack<Activity>
}

/**
 * 三方SDK初始化
 */
class AppInitHelper {
    fun initApp() {
        // 初始化Logger
        Logger.initLogger(BuildConfig.DEBUG, BuildConfig.APPLICATION_ID)
        AutoSize.initCompatMultiProcess(App.getContext())
        AutoSizeConfig.getInstance()
            .setLog(false)
            .setUseDeviceSize(true)
            .setBaseOnWidth(false)
            .setAutoAdaptStrategy(DefaultAutoAdaptStrategy())
            .setCustomFragment(true).onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                AutoSizeConfig.getInstance().screenWidth = ScreenUtils.getScreenSize(activity)[0]
                AutoSizeConfig.getInstance().screenHeight = ScreenUtils.getScreenSize(activity)[1]
                LogUtils.d(
                    String.format(
                        Locale.ENGLISH,
                        "%s onAdaptBefore!",
                        target.javaClass.name
                    )
                )
            }

            override fun onAdaptAfter(target: Any, activity: Activity) {
                LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.javaClass.name))
            }
        }
        customAdaptForExternal()
    }

    /**
     * 给外部的三方库 [Activity] 自定义适配参数, 因为三方库的 [Activity] 并不能通过实现
     * [CustomAdapt] 接口的方式来提供自定义适配参数 (因为远程依赖改不了源码)
     * 所以使用 [ExternalAdaptManager] 来替代实现接口的方式, 来提供自定义适配参数
     */
    private fun customAdaptForExternal() {
        /**
         * [ExternalAdaptManager] 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 [ExternalAdaptManager] 的类注释
         *
         * 加入的 Activity 将会放弃屏幕适配, 一般用于三方库的 Activity, 详情请看方法注释
         * 如果不想放弃三方库页面的适配, 请用 addExternalAdaptInfoOfActivity 方法, 建议对三方库页面进行适配, 让自己的 App 更完美一点
         * 为指定的 Activity 提供自定义适配参数, AndroidAutoSize 将会按照提供的适配参数进行适配, 详情请看方法注释
         * 一般用于三方库的 Activity, 因为三方库的设计图尺寸可能和项目自身的设计图尺寸不一致, 所以要想完美适配三方库的页面
         * 就需要提供三方库的设计图尺寸, 以及适配的方向 (以宽为基准还是高为基准?)
         * 三方库页面的设计图尺寸可能无法获知, 所以如果想让三方库的适配效果达到最好, 只有靠不断的尝试
         * 由于 AndroidAutoSize 可以让布局在所有设备上都等比例缩放, 所以只要您在一个设备上测试出了一个最完美的设计图尺寸
         * 那这个三方库页面在其他设备上也会呈现出同样的适配效果, 等比例缩放, 所以也就完成了三方库页面的屏幕适配
         * 即使在不改三方库源码的情况下也可以完美适配三方库的页面, 这就是 AndroidAutoSize 的优势
         * 但前提是三方库页面的布局使用的是 dp 和 sp, 如果布局全部使用的 px, 那 AndroidAutoSize 也将无能为力
         * 经过测试 DefaultErrorActivity 的设计图宽度在 380dp - 400dp 显示效果都是比较舒服的
         */

        // AutoSizeConfig.getInstance().externalAdaptManager
        //   .addExternalAdaptInfoOfActivity(DefaultErrorActivity::class.java!!, ExternalAdaptInfo(true, 400f))
    }
}