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
package net.lcyframework.kernel.model.response;

import java.util.ArrayList;
import java.util.Collection;

/**
 * * <pre>
 * 名称: ListResponse
 * 描述: ListResponse响应体
 * </pre>
 *
 * @author Jimmy Li
 * @since 1.0.0
 **/
public class ListResponse<T extends BaseResponse> extends ArrayList<T> implements BaseResponse {
    private static final long serialVersionUID = 2782887812008804947L;

    /**
     * 构造器
     *
     * @param c 集合
     */
    public ListResponse(final Collection<T> c) {
        super(c);
    }
}
