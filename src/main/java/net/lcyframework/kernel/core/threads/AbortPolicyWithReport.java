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

package net.lcyframework.kernel.core.threads;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import net.lcyframework.kernel.core.config.BaseProperties;

/**
 * <pre>
 * 名称: AbortPolicyWithReport
 * 描述: AbortPolicyWithReport
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
@Slf4j
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {

    /**
     * DEFAULT_APPLICATION_NAME
     */
    public static final String DEFAULT_APPLICATION_NAME = "default.application.name";
    private List<String> ipList;

    private final String threadName;

    /**
     * 构造方法
     * @param threadName 线程名
     */
    public AbortPolicyWithReport(final String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor e) {
        String msg = String.format(
                "Thread pool is EXHAUSTED!"
                        + " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s), in %s/%s!",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(),
                e.getLargestPoolSize(), e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(),
                e.isTerminating(), getIp(), BaseProperties.getString(DEFAULT_APPLICATION_NAME));
        log.error(msg);
        throw new RejectedExecutionException(msg);
    }

    private String getIp() {
        if (ipList != null) {
            return ipList.get(0);
        }
        List<String> ipList = new ArrayList<>();
        Enumeration<?> allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (final SocketException e) {
            log.error(e.getMessage(), e);
            return "localhost";
        }
        InetAddress ip;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration<?> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    String localIp = ip.getHostAddress();
                    if ("127.0.0.1".equals(localIp)) {
                        continue;
                    }
                    ipList.add(localIp);
                }
            }
        }
        return ipList.get(0);
    }

}
