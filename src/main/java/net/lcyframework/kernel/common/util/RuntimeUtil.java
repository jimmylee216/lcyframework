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

package net.lcyframework.kernel.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

import net.lcyframework.kernel.common.sercurity.AES;
import net.lcyframework.kernel.common.sercurity.MD5;

/**
 * 系统运行时功能扩展类
 *
 * @author Jimmy Li
 * @since 1.0.0
 */
public final class RuntimeUtil {

    /** 进程id */
    public static final String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    /** 操作系统 */
    public static final String OSNAME = System.getProperty("os.name");
    /** 可达进程 */
    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors() + 1;
    /** 令牌 */
    public static final String TOKEN = MD5.encryptMD5(AES.encrypt(
            "lcyframework.TOKEN::encrypt:net.ytooframework.kernel.common.util.RuntimeUtil", null,
            null), MD5.MIN_MD5_LENGTH);

    private RuntimeUtil() { }

    /**
     * 杀死当前系统进行
     * @throws IOException IO异常
     */
    public static void killProcess() throws IOException {
        if (OSNAME.indexOf("Mac") > -1 || OSNAME.indexOf("Linux") > -1) {
            String[] cmds = new String[] {"/bin/sh", "-c", "kill -9 " + PID};
            Runtime.getRuntime().exec(cmds);
        } else if (OSNAME.indexOf("Windows") > -1) {
            Runtime.getRuntime().exec("cmd /c taskkill /pid " + PID + " /f ");
        }

    }

    /**
     * 根据进程号杀死对应的进程
     * @param pid 进程号
     * @throws IOException IO异常
     */
    public static void killProcess(final String pid) throws IOException {
        if (OSNAME.indexOf("Mac") > -1 || OSNAME.indexOf("Linux") > -1) {
            String[] cmds = new String[] {"/bin/sh", "-c", "kill -9 " + pid};
            Runtime.getRuntime().exec(cmds);

        } else if (OSNAME.indexOf("Windows") > -1) {
            Runtime.getRuntime().exec("cmd /c taskkill /pid " + pid + " /f ");

        }

    }

    /**
     * 根据进程号优雅退出进程
     * @param pid 进程号
     * @throws IOException IO异常
     */
    public static void exitProcess(final String pid) throws IOException {
        if (OSNAME.indexOf("Mac") > -1 || OSNAME.indexOf("Linux") > -1) {
            String[] cmds = new String[] {"/bin/sh", "-c", "kill -15 " + pid};
            Runtime.getRuntime().exec(cmds);
        } else if (OSNAME.indexOf("Windows") > -1) {
            Runtime.getRuntime().exec("cmd /c taskkill /pid " + pid + " /f ");

        }

    }

    /**
     * 根据进程号查询该进程是否存在
     * @param pid 进程号
     * @return 查询结果
     * @throws IOException IO异常
     */
    public static boolean existsProcess(final String pid) throws IOException {
        if (pid == null || "".equals(pid)) {
            return false;
        }

        Process process = null;
        boolean exsits = false;
        String result = null;
        if (OSNAME.indexOf("Mac") > -1 || OSNAME.indexOf("Linux") > -1) {
            String[] cmds = new String[] {"/bin/sh", "-c", "ps -f -p " + pid};
            process = Runtime.getRuntime().exec(cmds);

            InputStream in = process.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(in));

            while ((result = input.readLine()) != null) {
                if (StringUtil.isNotEmpty(result) && result.indexOf(pid) > -1) {
                    exsits = true;
                }
            }

        } else if (OSNAME.indexOf("Windows") > -1) {
            process = Runtime.getRuntime().exec("cmd /c Wmic Process where ProcessId=\"" + pid + "\" get ExecutablePath ");

            InputStream in = process.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(in));

            while ((result = input.readLine()) != null) {
                if (StringUtil.isNotEmpty(result) && result.indexOf("No Instance(s) Available") < 0) {
                    exsits = true;
                }
            }
        }

        return exsits;
    }

    /**
     * 判断当前运行的系统是否是Windows.
     * @return boolean
     */
    public static boolean isWindows() {
        return OSNAME.contains("Windows");
    }

    /**
     * 根据Class获取该Class所在的磁盘路径.
     * @param clz 查询的类
     * @return 返回该类的所在位置
     */
    public static String getPath(final Class<?> clz) {
        String runJarPath = clz.getProtectionDomain().getCodeSource().getLocation().getPath();
        String tmpPath = runJarPath.substring(0, runJarPath.lastIndexOf('/'));
        if (tmpPath.endsWith("/lib")) {
            tmpPath = tmpPath.replace("/lib", "");
        }

        return tmpPath.substring(isWindows() ? 1 : 0, tmpPath.lastIndexOf('/')) + '/';
    }

    /**
     * 获取运行时中的所有Jar文件
     * @return List
     * @throws IOException if I/O error occur
     */
    public static List<JarFile> classPaths() throws IOException {
        String[] classPaths = System.getProperty("java.class.path").split(":");
        if (classPaths.length > 0) {
            List<JarFile> jars = new ArrayList<>(classPaths.length);
            for (final String classPath : classPaths) {
                if (!classPath.endsWith("jar")) {
                    continue;
                }

                JarFile jar = new JarFile(new File(classPath));
                jars.add(jar);

            }

            return jars;
        }

        return Collections.emptyList();
    }

}
