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

import com.google.common.collect.Maps;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 * 名称: ZooKeeperInfo
 * 描述: ZooKeeperInfo
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
@SuppressWarnings("restriction")
public class ZooKeeperInfo {

    /**
     * 1MB
     */
    private static final long LENGTH = 1024 * 1024;

    private String mail;
    private String phone;
    private String product;
    private String project;
    private String version;
    private String port;

    /**
     * 构造方法
     * @param mail      邮箱
     * @param phone     电话
     * @param product   product
     * @param project   project
     * @param version   版本号
     * @param port      端口
     */
    public ZooKeeperInfo(final String mail, final String phone, final String product, final String project,
            final String version, final String port) {
        this.mail = mail;
        this.phone = phone;
        this.product = product;
        this.project = project;
        this.version = version;
        this.port = port;
    }

    /**
     * 获取服务信息
     * @return map
     */
    public Map<String, String> getServerInfo() {
        Map<String, String> infos = Maps.newHashMap();
        // 注册服务负责人信息
        setOwnerInfo(infos);
        // 机器信息
        setSystemInfo(infos);
        // 运行时jvm信息
        setRuntimeInfo(infos);
        // jvm 内存信息
        setMxBeanInfo(infos);

        return infos;
    }

    public String getServerPath() {
        return ZookeeperConstants.FILE_SEPARATOR + ZookeeperConstants.NAME_SERVICE + ZookeeperConstants.FILE_SEPARATOR
                + product + ZookeeperConstants.FILE_SEPARATOR + project + ZookeeperConstants.FILE_SEPARATOR + version
                + ZookeeperConstants.FILE_SEPARATOR + ZookeeperUtil.getIp() + ZookeeperConstants.FILE_COLON + port;
    }

    private void setOwnerInfo(final Map<String, String> infos) {
        infos.put("mail", mail);
        infos.put("phone", phone);
    }

    private void setSystemInfo(final Map<String, String> infos) {
        Properties props = System.getProperties();
        infos.put("os.name", props.getProperty("os.name"));
        infos.put("os.version", props.getProperty("os.version"));
        infos.put("java.version", props.getProperty("java.version"));
        infos.put("java.home", props.getProperty("java.home"));
        infos.put("use.dir", props.getProperty("use.dir"));
    }

    private void setRuntimeInfo(final Map<String, String> infos) {
        Runtime runtime = Runtime.getRuntime();
        infos.put("used.memory", formatOSInfo((runtime.totalMemory() - runtime.freeMemory())));
        infos.put("free.memory", formatOSInfo(runtime.freeMemory()));
        infos.put("total.memory", formatOSInfo(runtime.totalMemory()));
        infos.put("max.memory", formatOSInfo(runtime.maxMemory()));
    }

    private void setMxBeanInfo(final Map<String, String> infos) {
        MemoryMXBean mm = (MemoryMXBean) ManagementFactory.getMemoryMXBean();
        infos.put("heap.used.memory ", formatOSInfo(mm.getHeapMemoryUsage().getInit()));
        infos.put("noheap.used.memory ", formatOSInfo(mm.getNonHeapMemoryUsage().getInit()));
        infos.put("noheap.used.memory ", formatOSInfo(mm.getNonHeapMemoryUsage().getInit()));

        OperatingSystemMXBean osm = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        infos.put("free.swap.space", formatOSInfo(osm.getFreeSwapSpaceSize()));
        infos.put("free.physical.memory", formatOSInfo(osm.getFreePhysicalMemorySize()));
        infos.put("total.physical.memory", formatOSInfo(osm.getTotalPhysicalMemorySize()));
        infos.put("available.processors", String.valueOf(osm.getAvailableProcessors()));
        infos.put("app.start.time", String.valueOf(new Date()));

        ThreadMXBean tm = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        infos.put("total.thread.count", String.valueOf(tm.getThreadCount()));
        infos.put("peak.thread.count", String.valueOf(tm.getPeakThreadCount()));
    }

    private static String formatOSInfo(final long num) {
        return String.valueOf(num / (LENGTH)) + "MB";
    }

}
