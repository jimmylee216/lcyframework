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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * 名称: ZookeeperConfig
 * 描述: zk 配置项
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
@Configuration
public class ZookeeperConfig {

    @Value("${zk.url}")
    private String url;

    @Value("${zk.mail}")
    private String mail;

    @Value("${zk.phone}")
    private String phone;

    @Value("${zk.product}")
    private String product;

    @Value("${zk.project}")
    private String project;

    @Value("${zk.version}")
    private String version;

    @Value("${server.port}")
    private String port;

    /**
     * 装载zk客户端
     * @return ZookeeperClient
     */
    @Bean(name = "zookeeperClient")
    public ZookeeperClient zookeeperClient() {
        return new ZookeeperClient(url, zookeeperComponent());
    }

    /**
     * 装载zk客户端信息
     * @return ZooKeeperInfo
     */
    @Bean(name = "zooKeeperComponent")
    public ZooKeeperInfo zookeeperComponent() {
        return new ZooKeeperInfo(mail, phone, product, project, version, port);
    }

}
