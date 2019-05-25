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

package net.lcyframework.kernel.core.dao;

import java.io.Serializable;
import java.util.List;

import net.lcyframework.kernel.model.pagination.Page;

/**
 * <pre>
 * 名称: Dao
 * 描述: 通用的dao方法
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public interface Dao {

    /**
     * MYBATIS_DAO
     */
    String MYBATIS_DAO = "daoMybatis";

    /**
     * 查询数据
     * @param namespace namespace
     * @param statement statement
     * @param paramData 查询条件
     * @param <T> 返回结果
     * @return T
     */
    <T> T get(String namespace, String statement, Object paramData);

    /**
     * 查询数据
     * @param namespace namespace
     * @param statement statement
     * @param key   查询条件
     * @param value 查询值
     * @param <T> 返回结果
     * @return T
     */
    <T> T load(String namespace, String statement, String key, Object value);

    /**
     * 查询数据
     * @param namespace namespace
     * @param statement statement
     * @param <T> 返回结果
     * @return T
     */
    <T> List<T> query(String namespace, String statement);

    /**
     * 查询数据
     * @param namespace namespace
     * @param statement statement
     * @param paramData 查询条件
     * @param <T> 返回结果
     * @return T
     */
    <T> List<T> query(String namespace, String statement, Object paramData);

    /**
     * 分页查询
     * @param namespace namespace
     * @param statement statement
     * @param page 分页查询条件
     * @param <T> 返回结果
     * @return list
     */
    <T extends Serializable> List<T> queryByPage(String namespace, String statement, Page<T> page);

    /**
     * 查询条数
     * @param namespace namespace
     * @param statement statement
     * @return int
     */
    int count(String namespace, String statement);

    /**
     * 查询记录数
     * @param namespace namespace
     * @param statement statement
     * @param paramData 查询条件
     * @return int
     */
    int count(String namespace, String statement, Object paramData);

    /**
     * 插入数据
     * @param namespace namespace
     * @param statement statement
     * @param t 实体对象
     * @param <T> 返回结果
     * @return list
     */
    <T> int insert(String namespace, String statement, T t);

    /**
     * 跟新数据
     * @param namespace namespace
     * @param statement statement
     * @param paramData 查询条件
     * @return int
     */
    int update(String namespace, String statement, Object paramData);

    /**
     * 删除数据
     * @param namespace namespace
     * @param statement statement
     * @param t 实体对象
     * @param <T> 返回结果
     * @return int
     */
    <T> int delete(String namespace, String statement, T t);

    /**
     * 分页查询
     * @param namespace namespace
     * @param statement statement
     * @param paramData 查询条件
     * @return list
     */
    String getSql(String namespace, String statement, Object paramData);

}
