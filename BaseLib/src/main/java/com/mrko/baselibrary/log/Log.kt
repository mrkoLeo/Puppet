package com.mrko.baselibrary.log

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.mrko.baselibrary.log.Logger.Companion.d
import com.mrko.baselibrary.log.Logger.Companion.e
import com.mrko.baselibrary.log.Logger.Companion.i
import com.mrko.baselibrary.log.Logger.Companion.json
import com.mrko.baselibrary.log.Logger.Companion.w
import com.mrko.baselibrary.log.Logger.Companion.wtf
import com.mrko.baselibrary.log.Logger.Companion.xml
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


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
 * @description: Logger 初始化
 * @since: 1.0
 */
class Logger {
    companion object {
        /**
         * 初始化操作
         */
        @JvmStatic
        fun initLogger(printEnable: Boolean, tag: String) {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(2)
                .methodOffset(5)
                .logStrategy(LogcatLogStrategy())
                .tag(tag)
                .build()
            Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return printEnable
                }
            })
        }

        @JvmStatic
        fun t(@Nullable tag: String) {
            Logger.t(tag)
        }

        @JvmStatic
        fun log(priority: Int, @Nullable tag: String, @Nullable message: String, @Nullable throwable: Throwable) {
            Logger.log(priority, tag, getAutoJumpLogInfo() + message, throwable)
        }

        @JvmStatic
        internal fun d(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.d(getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun d(@Nullable any: Any) {
            Logger.d(getAutoJumpLogInfo() + any)
        }

        @JvmStatic
        internal fun e(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.e(null, getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun e(@Nullable throwable: Throwable, @NonNull message: String, @Nullable vararg args: Any) {
            Logger.e(throwable, getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun i(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.i(getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun v(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.v(getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun w(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.w(getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun wtf(@NonNull message: String, @Nullable vararg args: Any) {
            Logger.wtf(getAutoJumpLogInfo() + message, *args)
        }

        @JvmStatic
        internal fun json(@Nullable json: String) {
            Logger.json(json)
        }

        @JvmStatic
        internal fun xml(@Nullable xml: String) {
            Logger.xml(xml)
        }

        private fun getAutoJumpLogInfo(): String {
            val elements = Thread.currentThread().stackTrace
            return if (elements.size < 5) {
                "\n"
            } else {
                val className =
                    "类 - " + elements[4].className.substring(elements[4].className.lastIndexOf(".") + 1)
                val methodName = "方法 -  ${elements[4].methodName}"
                val lineNumber = "行数 - ${elements[4].lineNumber}"
                val n = "\n"
                className + n + methodName + n + lineNumber + n
            }
        }

    }
}

/**
 * 便捷打印日志
 */
fun <T : String> T.logd(@Nullable vararg args: Any) {
    d(this, *args)
}

fun Any.logd() {
    d(this)
}

fun <T : String> T.loge(@Nullable vararg args: Any) {
    e(this, *args)
}

fun <T : String> T.logd(@Nullable throwable: Throwable, @Nullable vararg args: Any) {
    e(throwable, this, *args)
}

fun <T : String> T.logi(@Nullable vararg args: Any) {
    i(this, *args)
}

fun <T : String> T.logv(@Nullable vararg args: Any) {
    i(this, *args)
}

fun <T : String> T.logw(@Nullable vararg args: Any) {
    w(this, *args)
}

fun <T : String> T.logwtf(@Nullable vararg args: Any) {
    wtf(this, *args)
}

fun <T : String> T.logjson() {
    json(this)
}

fun <T : String> T.logxml() {
    xml(this)
}




