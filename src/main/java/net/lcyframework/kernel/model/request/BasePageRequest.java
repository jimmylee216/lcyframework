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

import lombok.EqualsAndHashCode;

/**
 * <pre>
 * 分页请求体基类
 * 描述: BasePageRequest 实体
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
//@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BasePageRequest extends BaseRequest {

    private static final long serialVersionUID = 6800879676466447183L;

    private int pageNo;

    private int limit;

    /**
     * 请求起始索引
     */
    private Integer start;

    //region pageNo
    public Integer getPageNo() {
        return (this.pageNo <= 0) ? 1 : this.pageNo;
    }

    public void setPageNo(final Integer pageNo) {
        this.pageNo = pageNo <= 0 ? 1 : pageNo;
    }
    //endregion

    //region limit
    public Integer getLimit() {
        return (this.limit <= 0) ? 1 : this.limit;
    }

    public void setLimit(final Integer limit) {
        this.limit = limit <= 0 ? 1 : limit;
    }
    //endregion

    //region start
    public Integer getStart() {
        return  this.start;
    }

    public void setStart(final Integer start) {
        this.start = start;
    }
    //endregion
}
