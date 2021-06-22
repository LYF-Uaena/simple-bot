package com.mirai.lyf.bot.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author LYF on 2020-09-29
 */
@Slf4j
@SuppressWarnings("unused")
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    static {
        XML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 把JavaBean对象转换为Json字符串
     *
     * @param bean JavaBean
     * @return Json字符串
     */
    public static String toJson(Object bean) {
        String jsonString = StringUtils.EMPTY;
        try {
            jsonString = OBJECT_MAPPER.writeValueAsString(bean);
        } catch (IOException ex) {
            log.warn("Bean to Json Error", ex);
        }

        return jsonString;
    }

    /**
     * 根据Json字符串，转换为JavaBean对象
     *
     * @param jsonString Json字符串
     * @param clazz1     指定Type.class
     * @param clazz2     指定Type.class
     * @param <T>        指定Type
     * @return JavaBean对象
     */
    public static <T, K> T toBean(final String jsonString, Class<T> clazz1, Class<K> clazz2) {
        T t = null;
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz1, clazz2);
            t = OBJECT_MAPPER.readValue(jsonString, javaType);
        } catch (IOException ex) {
            log.warn("Json to Bean Error", ex);
        }
        return t;
    }

    /**
     * 根据指定的文件内容，转换指定的JavaBean对象
     *
     * @param file  new File("src/test/resources/json_car.json")
     * @param clazz 转换指定Type.class
     * @param <T>   指定Type
     * @return JavaBean对象
     */
    public static <T> T toBean(File file, Class<T> clazz) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(file, clazz);
        } catch (IOException ex) {
            log.warn("Json to Bean Error", ex);
        }
        return t;
    }

    /**
     * 根据URL指定的文件内容，转换指定的JavaBean对象
     *
     * @param url   new URL("file:src/test/resources/json_car.json")
     * @param clazz 转换指定Type.class
     * @param <T>   指定Type
     * @return JavaBean对象
     */
    public static <T> T toBean(URL url, Class<T> clazz) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(url, clazz);
        } catch (IOException ex) {
            log.warn("Json to Bean Error", ex);
        }
        return t;
    }

    /**
     * 根据resource文件，转换为JavaBean对象的list
     *
     * @param resource resource文件
     * @param clazz    指定Type.class
     * @param <T>      指定Type
     * @return JavaBean对象的list
     * @throws IOException IO异常
     */
    public static <T> List<T> toBeanList(Resource resource, Class<T> clazz) throws IOException {
        String lines = StringUtils.join(IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8), "");
        return toBeanList(lines, clazz);
    }

    /**
     * 根据Json字符串，转换为JavaBean对象的list
     *
     * @param jsonString Json字符串
     * @param clazz      指定Type.class
     * @param <T>        指定Type
     * @return JavaBean对象的list
     */
    public static <T> List<T> toBeanList(String jsonString, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        try {
            list = OBJECT_MAPPER.readValue(jsonString, type);
        } catch (IOException ex) {
            log.warn("Json to BeanList Error", ex);
        }
        return list;
    }

    /**
     * 根据xml字符串转换为Bean
     *
     * @param xmlString xml字符串
     * @param clazz     指定Type.class
     * @param <T>       指定Type
     * @return bean对象
     */
    public static <T> T xmlToBean(final String xmlString, Class<T> clazz) {
        T t = null;
        try {
            t = XML_MAPPER.readValue(xmlString, clazz);
        } catch (IOException ex) {
            log.warn("XML string to Bean Error", ex);
        }
        return t;
    }

    /**
     * 根据map转换为Bean
     *
     * @param map   map对象
     * @param clazz 指定Type.class
     * @param <T>   指定Type
     * @return 转换后的Bean
     */
    public static <T> T mapToBean(final Map map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    /**
     * 把JavaBean转换为XML的字符串
     *
     * @param bean Java bean
     * @return 转换后的XML字符串
     */
    public static String toXml(Object bean) {
        String jsonString = StringUtils.EMPTY;
        try {
            jsonString = XML_MAPPER.writeValueAsString(bean);
        } catch (IOException ex) {
            log.warn("Bean to Json Error", ex);
        }

        return jsonString;
    }

}
