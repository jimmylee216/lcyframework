/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lcyframework.kernel.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 名称: JsonUtil
 * 描述: Json工具类处理
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public final class JsonUtil implements java.io.Serializable {

    private static final long serialVersionUID = -8872078079583695100L;

    static {
        JSON.DEFFAULT_DATE_FORMAT = DateUtil.HUJIANG_DATE_FORMAT;
    }

    private JsonUtil() { }

    /**
     * 对象转json
     * @param obj 对象
     * @param serializerFeature 序列化特性
     * @return String str
     */
    public static String object2JSON(final Object obj, final SerializerFeature... serializerFeature) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, serializerFeature);
    }

    /**
     * 对象转json
     * @param obj 对象
     * @param serializeConfig 序列化配置
     * @param serializerFeature 序列化特性
     * @return String str
     */
    public static String object2JSON(final Object obj, final SerializeConfig serializeConfig,
            final SerializerFeature... serializerFeature) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, serializeConfig, serializerFeature);
    }

    /**
     * 对象转json
     * @param obj 对象
     * @return String str
     */
    public static String object2JSON(final Object obj) {
        if (obj == null) {
            return "{}";
        }
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * json转对象
     * @param json json字符串
     * @param clazz 类型
     * @param <T> 泛型
     * @return T 对象实例
     */
    public static <T> T json2Object(final String json, final Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    /**
     * json转对象
     * @param json json字符串
     * @param reference 类型
     * @param <T> 泛型
     * @return T 对象实例
     */
    public static <T> T json2Reference(final String json, final TypeReference<T> reference) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, reference);
    }

    /**
     * json转对象
     * @param json json字符串
     * @param type 类型
     * @param features 特性
     * @param <T> 泛型
     * @return T 对象实例
     */
    public static <T> T json2Reference(final String json, final Type type, final Feature... features) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, type, features);
    }

    /**
     * json转Map
     * @param json json字符串
     * @param features 特性
     * @return Map实例
     */
    public static Map<String, Object> json2Map(final String json, final Feature... features) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        }, features);
    }

    /**
     * json转Map
     * @param json json字符串
     * @return Map实例
     */
    public static Map<String, Object> json2Map(final String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * json转对象
     * @param json json字符串
     * @param reference 类型
     * @param features 特性
     * @param <T> 泛型
     * @return T 对象实例
     */
    public static <T> T json2Reference(final String json, final TypeReference<T> reference, final Feature... features) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, reference, features);
    }

    /**
     * 字节反序列化
     * @param bytes 字节数组
     * @param <T> 泛型
     * @return T 对象实例
     */
    public static <T> T deserialize(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return JSON.parseObject(bytes, new TypeReference<T>() {
        }.getType());
    }

    /**
     * 对象序列化
     * @param t 对象实例
     * @param <T> 泛型
     * @return byte[]字节数据
     */
    public static <T> byte[] serialize(final T t) {
        if (t == null) {
            return null;
        }
        return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
    }

    /**
     * fastjson 转成实体类
     * @param cls        类型
     * @param jsonString json字符串
     * @param <T>        集合类型
     * @return T 泛型
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromFastjson(final String jsonString, final Class<?> cls) {
        return (T) JSON.parseObject(jsonString, cls);
    }

    /**
     * fastjson 将实体类转成json
     * @param obj 对象
     * @return String json字符串
     */
    public static String toFastjson(final Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * fastjson 转成List
     * @param cls        类型
     * @param jsonString json字符串
     * @param <T>        集合类型
     * @return List      集合
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseArray(final String jsonString, final Class<?> cls) {
        return (List<T>) JSON.parseArray(jsonString, cls);
    }
}
