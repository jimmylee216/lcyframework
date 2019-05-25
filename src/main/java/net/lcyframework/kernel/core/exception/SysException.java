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
 * 名称: SysException
 * 描述: 系统级运行时异常
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 3116483353040779859L;

    private transient Object[] args;
    private transient BaseResponse returnObj;
    private int errorCode;

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     */
    public SysException(final int errorCode, final String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param returnObj BaseResponse
     */
    public SysException(final int errorCode, final String msg, final BaseResponse returnObj) {
        super(msg);
        this.errorCode = errorCode;
        this.returnObj = returnObj;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param cause Throwable
     */
    public SysException(final int errorCode, final String msg, final Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    /**
     * 构造方法
     * @param cause     Throwable
     * @param msg       错误消息
     * @param errorCode 错误代码
     * @param returnObj BaseResponse
     */
    public SysException(final int errorCode, final String msg, final Throwable cause, final BaseResponse returnObj) {
        super(msg, cause);
        this.errorCode = errorCode;
        this.returnObj = returnObj;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param args      args
     */
    public SysException(final int errorCode, final String msg, final Object[] args) {
        super(msg);
        this.errorCode = errorCode;
        this.args = args;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param args      args
     * @param returnObj     BaseResponse
     */
    public SysException(final int errorCode, final String msg, final Object[] args, final BaseResponse returnObj) {
        super(msg);
        this.errorCode = errorCode;
        this.args = args;
        this.returnObj = returnObj;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       错误消息
     * @param args      args
     * @param cause     Throwable
     */
    public SysException(final int errorCode, final String msg, final Object[] args, final Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param msg       消息
     * @param args      the args
     * @param cause     错误栈
     * @param returnObj BaseResponse
     */
    public SysException(final int errorCode, final String msg, final Object[] args, final Throwable cause,
            final BaseResponse returnObj) {
        super(msg, cause);
        this.errorCode = errorCode;
        this.args = args;
        this.returnObj = returnObj;
    }

    /**
     * 构造方法
     * @param cause     错误栈
     */
    public SysException(final Throwable cause) {
        super(cause);
    }

    /**
     * 构造方法
     * @param cause     错误栈
     * @param returnObj BaseResponse
     */
    public SysException(final Throwable cause, final BaseResponse returnObj) {
        super(cause);
        this.returnObj = returnObj;
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
     * 栈
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public int getCode() {
        return errorCode;
    }

    public void setCode(final int errorCode) {
        this.errorCode = errorCode;
    }

}
