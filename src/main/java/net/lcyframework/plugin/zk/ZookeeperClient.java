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

package net.lcyframework.plugin.zk;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.PathUtils;
import org.apache.zookeeper.CreateMode;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 名称: ZookeeperClient
 * 描述: zk 客户端
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
@Slf4j
public class ZookeeperClient {

    private CuratorFramework zkclient;

    private ZooKeeperInfo zkComponent;

    /**
     * 构造函数
     * @param url url
     * @param zkComponent zk信息
     */
    public ZookeeperClient(final String url, final ZooKeeperInfo zkComponent) {
        this.zkComponent = zkComponent;

        try {
            zkclient = crateWithOptions(url,
                    new ExponentialBackoffRetry(ZookeeperConstants.SLEEP_TIME_MS, ZookeeperConstants.MAX_RETRIES),
                    ZookeeperConstants.CONNECTION_TIMEOUT_MS, ZookeeperConstants.SESSION_TIMEOUT_MS);
            zkclient.getConnectionStateListenable().addListener(createConnectionListener());
            zkclient.start();
            zkclient.getZookeeperClient().blockUntilConnectedOrTimedOut();
        } catch (final InterruptedException e) {
            log.error("zk register encounter InterruptedException", e);
        } catch (final Exception e) {
            log.error("zk register encounter Exception", e);
        }
        log.info("zookeeper established connection successful");
    }

    /**
     * 创建连接监听
     * @return ConnectionStateListener
     */
    public ConnectionStateListener createConnectionListener() {
        return new ConnectionStateListener() {

            @Override
            public void stateChanged(final CuratorFramework client, final ConnectionState newState) {
                log.info("zk client connection state changed: {}", newState.toString());

                if (newState == ConnectionState.RECONNECTED || newState == ConnectionState.CONNECTED) {
                    while (true) {
                        try {
                            if (client.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                                createEphemeralNode(zkComponent.getServerPath(),
                                        JSON.toJSONString(zkComponent.getServerInfo()));
                                break;
                            }
                        } catch (final InterruptedException e) {
                            log.error("InterruptedException", e);
                        }
                    }
                }
            }
        };
    }

    /**
     * 创建CuratorFramework
     * @param connection          连接
     * @param retryPolicy         重试机制
     * @param connectionTimeoutMs 连接超时时间，毫秒
     * @param  sessionTimeoutMs   会话超时时间，毫秒
     * @return CuratorFramework
     * @throws NoSuchAlgorithmException 无此类算法异常
     */
    public CuratorFramework crateWithOptions(final String connection, final RetryPolicy retryPolicy,
            final int connectionTimeoutMs, final int sessionTimeoutMs) throws NoSuchAlgorithmException {
        Preconditions.checkNotNull(connection);
        Preconditions.checkNotNull(retryPolicy);
        Preconditions.checkNotNull(connectionTimeoutMs);
        Preconditions.checkNotNull(sessionTimeoutMs);

        return CuratorFrameworkFactory.builder().connectString(connection).sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs).retryPolicy(retryPolicy).build();
    }

    /**
     * zk执行业务方法
     * @param action action
     * @param <T> 返回类型
     * @return T
     */
    public <T> T execute(final ZookeeperCallback<T> action) {
        Preconditions.checkNotNull(action, "Callback object must not be null");
        try {
            return action.doInZookeeper(zkclient);
        } catch (final Throwable e) {
            log.error("zk register encounter Exception", e);
        }
        return null;
    }

    /**
     * 取得节点值
     * @param <T> 返回类型
     * @param path  路径
     * @param clazz 类型
     * @return T
     */
    public <T> T getNodeValue(final String path, final Class<T> clazz) {
        return execute(new ZookeeperCallback<T>() {
            @Override
            public T doInZookeeper(final CuratorFramework zkClient) throws Exception {
                PathUtils.validatePath(path);
                String value = new String(zkClient.getData().forPath(path));
                return JSON.parseObject(value, clazz);
            }
        });
    }

    /**
     * createEphemeralNode
     * @param path 路径
     */
    public void createEphemeralNode(final String path) {
        createNode(path, StringUtils.EMPTY, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * createEphemeralNode
     * @param path 路径
     * @param value 值
     */
    public void createEphemeralNode(final String path, final String value) {
        createNode(path, value, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * createPersistentNode
     * @param path 路径
     */
    public void createPersistentNode(final String path) {
        createPersistentNode(path, StringUtils.EMPTY);
    }

    /**
     * createPersistentNode
     * @param path 路径
     * @param value 值
     */
    public void createPersistentNode(final String path, final String value) {
        createNode(path, value, CreateMode.PERSISTENT);
    }

    /**
     * createNode
     * @param path  路径
     * @param value 值
     * @param mode  节点信息
     */
    public void createNode(final String path, final String value, final CreateMode mode) {
        execute(new ZookeeperCallback<String>() {
            @Override
            public String doInZookeeper(final CuratorFramework zkClient) throws Exception {

                String result = zkclient.create().creatingParentsIfNeeded().withMode(mode).forPath(path + "-",
                        value.getBytes());

                log.info("create path :{} , result :{}", path, result);
                return result;
            }
        });
    }

}
