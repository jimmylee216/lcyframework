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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 名称: ZookeeperConstants
 * 描述: zk 工具类
 * 场景: 获取本机IP
 * </pre>
 * @author yto.net.cn
 * @since 1.0.0
 */
@Slf4j
public final class ZookeeperUtil {

    private static List<String> IP_LIST;

    private ZookeeperUtil() { }
    /**
     * 获取本机IP
     *
     * @return ip的list
     */
    public static String getIp() {
        if (IP_LIST != null) {
            return IP_LIST.get(0);
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
        ZookeeperUtil.IP_LIST = ipList;
        return  ZookeeperUtil.IP_LIST.get(0);
    }

}
