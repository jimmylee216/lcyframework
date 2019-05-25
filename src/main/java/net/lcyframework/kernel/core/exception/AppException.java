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

package net.lcyframework.kernel.core.exception;

import net.lcyframework.kernel.model.response.BaseResponse;

/**
 * <pre>
 * 名称: AppException
 * 描述: 应用级运行时异常
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 8688953989589840707L;

    private transient Object[] args;
    private transient BaseResponse returnObj;
    private int errorCode;

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     */
    public AppException(final int errorCode, final String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param returnObj BaseResponse
     */
    public AppException(final int errorCode, final String msg, final BaseResponse returnObj) {
        super(msg);
        this.returnObj = returnObj;
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param cause Throwable
     */
    public AppException(final int errorCode, final String msg, final Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param cause     Throwable
     * @param msg       错误消息
     * @param returnObj BaseResponse
     */
    public AppException(final String msg, final Throwable cause, final BaseResponse returnObj) {
        super(msg, cause);
        this.returnObj = returnObj;
    }

    /**
     * 构造方法
     * @param cause     Throwable
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param returnObj BaseResponse
     */
    public AppException(final int errorCode, final String msg, final Throwable cause, final BaseResponse returnObj) {
        super(msg, cause);
        this.returnObj = returnObj;
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param args      args
     */
    public AppException(final int errorCode, final String msg, final Object[] args) {
        super(msg);
        this.args = args;
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param args      args
     * @param cause     Throwable
     */
    public AppException(final int errorCode, final String msg, final Object[] args, final Throwable cause) {
        super(msg, cause);
        this.args = args;
        this.errorCode = errorCode;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(final Object[] args) {
        this.args = args;
    }

    public BaseResponse getReturnObj() {
        return this.returnObj;
    }

    /**
     * @return throwable 栈
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return this.errorCode;
    }

    public void setCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public void setReturnObj(final BaseResponse returnObj) {
        this.returnObj = returnObj;
    }
}
