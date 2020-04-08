package com.mrko.baselibrary.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mrko.baselibrary.gson.adapter.BooleanDefaultAdapter
import com.mrko.baselibrary.gson.adapter.DoubleDefaultAdapter
import com.mrko.baselibrary.gson.adapter.IntegerDefaultAdapter
import com.mrko.baselibrary.gson.adapter.LongDefaultAdapter
import java.lang.reflect.Modifier


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
 * @author:Created by Mrko on 2020/4/7.
 * @email:mrko0630@163.com
 * @description: GsonHelper Gson的统一处理类，便于统一修改
 * @since: 1.0
 *
 * <pre class = "GsonHelper">
 * // 为数组类型自定义反序列化适配器
 * var listDeserializer: JsonDeserializer<List<*>?> =
 *     JsonDeserializer<List<*>?> { arg0, arg1, arg2 ->
 *         if (arg0.isJsonArray) {
 *             val jsonArray = arg0.asJsonArray
 *             if (jsonArray.size() == 0) {
 *                 return@JsonDeserializer Collections.EMPTY_LIST
 *             }
 *             val resultList: MutableList<Any> = mutableListOf()
 *             for (element in jsonArray) {
 *                 resultList.add(arg2.deserialize<Any>(element, arg1))
 *             }
 *             resultList
 *         } else {
 *             Collections.EMPTY_LIST
 *         }
 *     }
 *
 * var strategy: ExclusionStrategy = object : ExclusionStrategy {
 *     // 过滤指定的类
 *     override fun shouldSkipClass(arg0: Class<*>?): Boolean {
 *         return false
 *     }
 *     // 过滤指定的字段
 *     override fun shouldSkipField(arg0: FieldAttributes): Boolean {
 *         return false
 *     }
 * }
 *
 * fun createGson(): Gson {
 *   return GsonBuilder()
 *     // 某些字段是空时，也需要传给后台需加上
 *     .serializeNulls()
 *     // 对字段的命名规则处理，枚举类型，详情看源码，也支持自定义
 *     .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
 *     // 过滤规则同时适用于序列化/反序列化
 *     .setExclusionStrategies(strategy)
 *     // 过滤规则只适用于序列化
 *     .addSerializationExclusionStrategy(strategy)
 *     // 过滤规则只适用于反序列化
 *     .addDeserializationExclusionStrategy(strategy)
 *     // 设置字段序列化/反序列化过滤规则
 *     .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
 *     // 注册自定义的反序列化适配器
 *     .registerTypeHierarchyAdapter(MutableList::class.java, listDeserializer)
 *     // 优雅的打印出来
 *     .setPrettyPrinting()
 *     // 设置版本
 *     .setVersion(1.0)
 *     .create()
 * }
 *
 */

class GsonHelper {
    companion object {
        fun createGson() : Gson {
            return GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd:hh-mm")
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .registerTypeAdapter(Integer::class.java, IntegerDefaultAdapter())
                .registerTypeAdapter(Int::class.java, IntegerDefaultAdapter())
                .registerTypeAdapter(Double::class.java, DoubleDefaultAdapter())
                .registerTypeAdapter(Long::class.java, LongDefaultAdapter())
                .registerTypeAdapter(Boolean::class.java, BooleanDefaultAdapter())
                .setPrettyPrinting()
                .create()
        }
    }
}


