package org.ehuacui.bbs.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * <p>
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonUtil {

    private ObjectMapper mapper;

    private JsonUtil(Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JsonUtil nonEmptyMapper() {
        return new JsonUtil(Include.NON_EMPTY);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static JsonUtil nonDefaultMapper() {
        return new JsonUtil(Include.NON_DEFAULT);
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) throws IOException {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new IOException("write to json string error:" + object, e);
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
        if (StringUtil.isBlank(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new IOException("parse json string error:" + jsonString, e);
        }
    }

    /**
     * 解析JSON-->Map
     */
    public Map fromJson2Map(String jsonString) throws IOException {
        return fromJson(jsonString, Map.class);
    }

    /**
     * 解析JSON-->List<Map>(LinkedList<LinkedHashMap>)
     */
    public List<Map> getList(String jsonValue) throws IOException {
        TypeFactory t = TypeFactory.defaultInstance();
        // 指定容器结构和类型
        return mapper.readValue(jsonValue, t.constructCollectionType(List.class, Map.class));
        // 如果T确定的情况下可以使用下面的方法：
        // return objectMapper.readValue(jsonVal, new TypeReference<List<T>>() {});
    }

}
