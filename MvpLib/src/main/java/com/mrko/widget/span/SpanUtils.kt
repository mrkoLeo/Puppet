package com.mrko.pet.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

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
 * @description: 用于处理text 富文本的工具类
 * @since: 1.0
 */
class SpanUtils {
    companion object {

        /**
         * 设置背景色
         * @param text
         * @param filter
         * @param color
         * @return
         */
        fun setForeground(text: String, filter: String, color: Int): SpannableString {
            val sp = SpannableString(text)
            val contains = text.contains(filter)
            if (contains) {
                val position = text.indexOf(filter)
                sp.setSpan(
                    ForegroundColorSpan(color),
                    position,
                    position + filter.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            return sp
        }

        fun setForeground(
            text: String,
            filter: String,
            color: Int,
            filterColor: Int
        ): SpannableString {
            val sp = SpannableString(text)
            val contains = text.contains(filter)
            sp.setSpan(
                ForegroundColorSpan(color),
                0,
                text.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            if (contains) {
                val position = text.indexOf(filter)
                sp.setSpan(
                    ForegroundColorSpan(filterColor),
                    position,
                    position + filter.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            return sp
        }

        /**
         * 设置背景色
         * @param text
         * @param filters
         * @param color
         * @return
         */
        fun setForeground(text: String, filters: List<String>, color: Int): SpannableString {
            val sp = SpannableString(text)
            for (filter in filters) {
                val contains = text.contains(filter)
                if (contains) {
                    val position = text.indexOf(filter)
                    sp.setSpan(
                        ForegroundColorSpan(color),
                        position,
                        position + filter.length,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                }
            }
            return sp
        }

        /**
         * 设置前景色
         * @param text
         * @param start
         * @param end
         * @param color
         * @return
         */
        fun setForeground(text: String, start: Int, end: Int, color: Int): SpannableString {
            val sp = SpannableString(text)
            sp.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            return sp
        }

        /**
         * 设置背景色
         * @param text
         * @param filter
         * @param color
         * @return
         */
        fun setBackground(text: String, filter: String, color: Int): SpannableString {
            val sp = SpannableString(text)
            val contains = text.contains(filter)
            if (contains) {
                val position = text.indexOf(filter)
                sp.setSpan(
                    BackgroundColorSpan(color),
                    position,
                    position + filter.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            return sp
        }


        /**
         * 设置加粗和斜体
         * @param text
         * @param start
         * @param end
         * @param typeface
         * public static final int NORMAL = 0;
         * public static final int BOLD = 1;
         * public static final int ITALIC = 2;
         * public static final int BOLD_ITALIC = 3;
         * @return
         */
        fun setStyle(text: String, start: Int, end: Int, typeface: Int): SpannableString {
            val sp = SpannableString(text)
            sp.setSpan(StyleSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return sp
        }

        fun setStyle(text: String, filter: String, typeface: Int): SpannableString {
            val sp = SpannableString(text)
            val contains = text.contains(filter)
            if (contains) {
                val position = text.indexOf(filter)
                sp.setSpan(
                    StyleSpan(typeface),
                    position,
                    position + filter.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
            }
            return sp
        }
    }
}