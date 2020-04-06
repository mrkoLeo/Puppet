package com.mrko.baselibrary.utils

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
 * @author:Created by Mrko on 2020-01-15.
 * @email:mrko0630@163.com
 * @description:
 * @since: 1.0
 */

/**
 * java 三目运算符
 */
fun <T> T?.then(default: T) = this ?: default

fun <T> T?.then(block: () -> T) = this ?: block

/**
 * 任意对象转为hashMap
 */
//fun <T> T?.toMap(): HashMap<String, String>? {
//    try {
//        val json = JsonUtils.getGson().toJson(this)
//        val type = object : TypeToken<java.util.HashMap<String, String>>(){}.type
//        return JsonUtils.getGson().fromJson(json, type)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return null
//}
//
///**
// * map转为对象
// */
//inline fun <reified T> Map<String, String>.toObject(): T {
//    val json = JsonUtils.getGson().toJson(this)
//    return JsonUtils.getGson().fromJson(json, T::class.java)
//}

/**
 * 取出列表中某一字段的去重后的数据集合
 */
inline fun <T, C> Collection<C>.setOf(block: (C) -> T): List<T> {
    val list = arrayListOf<T>()
    for (item in this) {
        val value = block(item)
        if (!list.contains(value)) {
            list.add(value)
        }
    }

    return list
}

inline fun <E> Collection<E>.toStringByFieldWithSeparator(
    separator: String = "",
    block: (E) -> String
): String {
    val string = StringBuilder()
    for ((index, item) in this.withIndex()) {
        string.append(block(item))
        if (index != size - 1) {
            string.append(separator)
        }
    }

    return string.toString()
}

inline fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}


