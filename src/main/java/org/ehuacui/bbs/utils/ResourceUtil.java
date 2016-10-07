/**
 * Copyright (c) 2011-2013, kidzhou 周磊 (zhouleib1412@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ehuacui.bbs.utils;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * 获取配置文件
 */
public class ResourceUtil {

    /**
     * 读取属性文件并转换键值
     */
    private static Map<String, String> readProperties(String resourceName) {
        Properties properties = new Properties();
        URL resource = Resources.getResource(resourceName);
        try {
            properties.load(new InputStreamReader(resource.openStream(), "UTF-8"));
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
        return Maps.fromProperties(properties);
    }

    /**
     * 读取属性文件("webconfig.properties")并转换键值
     */
    public static Map<String, String> readWebConfigProperties() {
        return readProperties("webconfig.properties");
    }

    /**
     * 读取属性文件("webconfig.properties")根据键获取值信息
     */
    public static String getWebConfigValueByKey(String key) {
        Map<String, String> data = readWebConfigProperties();
        if (data != null) {
            return data.get(key);
        } else {
            return null;
        }
    }

    public static Integer getWebConfigIntegerValueByKey(String key) {
        Map<String, String> data = readWebConfigProperties();
        if (data != null) {
            return Integer.valueOf(data.get(key));
        } else {
            return 0;
        }
    }

    public static Integer getWebConfigIntegerValueByKey(String key, Integer defaultValue) {
        Map<String, String> data = readWebConfigProperties();
        if (data != null) {
            return Integer.valueOf(data.get(key));
        } else {
            return defaultValue;
        }
    }

    public static Boolean getWebConfigBooleanValueByKey(String key) {
        Map<String, String> data = readWebConfigProperties();
        if (data != null) {
            return Boolean.valueOf(data.get(key));
        } else {
            return false;
        }
    }
}