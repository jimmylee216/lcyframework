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

package net.lcyframework.kernel.model.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 请求体基类
 * 描述: BaseRequest 实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public abstract class BaseRequest implements java.io.Serializable {

    private static final long serialVersionUID = 8976588576075723075L;

    /**
     * 需要请求的目标Api地址
     */
    @JSONField(serialize = false)
    private String url;

    /**
     * Api版本号
     */
    @JSONField(serialize = false)
    private String version;

    /**
     * 请求源（Web、Android、Ios...）
     */
    @JSONField(serialize = false)
    private String source;

    /**
     * 请求编号
     */
    @JSONField(serialize = false)
    @Getter
    @Setter
    private String requestId;

    /**
     * APi请求头
     */
    @JSONField(serialize = false)
    private Map<String, String> headers = new HashMap<>();

    /**
     * 构造方法
     */
    public BaseRequest() {
        this.headers = new HashMap<>();
    }

    /**
     * 获取版本号
     * @return 版本号
     */
    public String getVersion() {
        if (version == null || "".equals(version)) {
            return "1.0";
        }
        String regEx = "v?(([A-Z]*)[0-9]+([.]{1}[0-9]+){0,1}$)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(version);
        boolean rs = matcher.matches();
        return rs ? matcher.group(1) : "1.0";
    }

    /**
     * 获取参数
     * @return map
     */
    @JSONField(serialize = false)
    public Map<String, Object> getParamData() {
        Map<String, Object> mp = new HashMap<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                mp.put(field.getName(), field.get(this));
            } catch (final Exception e) {

            }
        }
        mp.remove("serialVersionUID");
        return mp;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
