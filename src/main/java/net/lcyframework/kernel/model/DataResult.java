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

package net.lcyframework.kernel.model;

import java.util.Date;
import java.util.function.Function;

import net.lcyframework.kernel.model.response.BaseResponse;

/**
 * <pre>
 * 名称: 统一返回API数据格式
 * 描述: DataResult响应实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class DataResult<T extends BaseResponse> {

    /**
     * 状态
     */
    private int status;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
    /**
     * 时间
     */
    private Date time;

    /**
     * 构造方法
     */
    public DataResult() {
    }

    /**
     * 构造方法
     * @param status 状态
     * @param message 消息
     * @param data 数据
     */
    public DataResult(final int status, final String message, final T data) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.setTime(new Date());
    }

    /**
     * 构造方法
     * @param data 数据
     */
    public DataResult(final T data) {
        this(0, null, data);
    }

    public T getData() {
        return (T) data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * 失败实体
     * @param status  状态码
     * @param message 消息
     * @param result  结果
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> fail(final int status, final String message, final V result) {
        return new DataResult<V>(status, message, result);
    }

    /**
     * 失败实体
     * @param message 消息
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> fail(final String message) {
        DataResult<V> result = new DataResult<V>();
        result.setMessage(message);
        result.setStatus(-1);
        return result;
    }

    /**
     * 失败实体
     * @param status  状态码
     * @param message 消息
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> fail(final int status, final String message) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功实体
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> ok() {
        DataResult<V> result = new DataResult<V>();
        result.setMessage("success");
        return result;
    }

    /**
     * 成功实体
     * @param data  数据
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> ok(final V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * 成功实体
     * @param data  数据
     * @param message 消息
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> ok(final String message, final V data) {
        DataResult<V> result = new DataResult<V>();
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 成功实体
     * @param status 状态码
     * @param data  数据
     * @param message 消息
     * @param <V> 继承自 BaseResponse
     * @return dataResult
     */
    public static <V extends BaseResponse> DataResult<V> ok(final Integer status, final String message, final V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        result.setTime(new Date());
        return result;
    }

    @Override
    public String toString() {
        return "DataResult{" + "status =" + status + ", message =" + message + ", data =" + data + ", time = " + time + "}";
    }

    /**
     * 定制化输出
     * @param fun the fun
     * @return string
     */
    public String toString(final Function<DataResult<T>, String> fun) {
        if (null == fun) {
            return toString();
        }
        return fun.apply(this);
    }
}
