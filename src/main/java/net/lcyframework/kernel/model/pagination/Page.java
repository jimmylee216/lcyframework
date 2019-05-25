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

package net.lcyframework.kernel.model.pagination;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 *
 * @author Jimmy Li
 * @since 1.0.0
 */
public class Page<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -7391097880029846491L;

    private int pageNo = 1;
    private int limit = 20;
    private long total;
    private int pages = 1;
    private String orderby;
    private Object paramData;
    private List<T> resultData;
    private String pk = "id";

    /**
     * 构造方法
     */
    public Page() {
    }

    /**
     * 构造方法
     * @param pageNo 页码
     * @param limit  查询记录数
     */
    public Page(final int pageNo, final int limit) {
        int pn = pageNo <= 0 ? 1 : pageNo;
        int lm = limit <= 0 ? 1 : limit;
        this.pageNo = pn;
        this.limit = lm;
    }

    /**
     * 获取当前页码
     * @return 页码
     */
    public int getPages() {
        if (getTotal() == 0) {
            setPages(1);
        } else {
            if (getTotal() % getLimit() == 0) {
                setPages((int) getTotal() / getLimit());
            } else {
                setPages((int) (getTotal() / getLimit()) + 1);
            }
        }
        return this.pages;
    }

    public void setPages(final int pages) {
        this.pages = pages;
    }

    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置页码
     * @param pageNo 页码
     */
    public void setPageNo(final int pageNo) {
        int pn = pageNo <= 0 ? 1 : pageNo;
        this.pageNo = pn;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * 设置limit
     * @param limit 查询条数
     */
    public void setLimit(final int limit) {
        int lm = limit <= 0 ? 1 : limit;
        this.limit = lm;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    public Object getParamData() {
        return paramData;
    }

    public void setParamData(final Object paramData) {
        this.paramData = paramData;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(final String orderby) {
        this.orderby = orderby;
    }

    public List<T> getResultData() {
        return resultData;
    }

    public void setResultData(final List<T> resultData) {
        this.resultData = resultData;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(final String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
