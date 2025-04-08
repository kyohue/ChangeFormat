package org.kall;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigFactory {
    private static final Properties props = new Properties();
    private static final Properties propsReal = new Properties();
    private static final Properties propsTemplate = new Properties();
    private static final Log logger = LogFactory.getLog(ConfigFactory.class);

    static {
        try (InputStream input = ConfigFactory.class.getClassLoader().getResourceAsStream("application.properties");
             InputStream inputT = ConfigFactory.class.getClassLoader().getResourceAsStream("template.properties")
        ) {
            if (input == null || inputT == null) throw new RuntimeException("配置文件未找到");
            props.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            propsTemplate.load(new InputStreamReader(inputT, StandardCharsets.UTF_8));
            String filename;
            if (props.getProperty("isMacEnvironment","false").equalsIgnoreCase("true")) {
                filename = "mac.properties";
            }else
                filename = "winDesktop.properties";
            try (InputStream in = ConfigFactory.class.getClassLoader().getResourceAsStream(filename)) {
                if (in == null) throw new RuntimeException("配置文件未找到:" + filename);
                propsReal.load(new InputStreamReader(in, StandardCharsets.UTF_8));
                logger.info("当前配置文件是：" + filename);
            }catch (Exception e) {
                throw new RuntimeException("配置加载失败",e);
            }
        } catch (Exception e) {
            throw new RuntimeException("配置加载失败", e);
        }
    }

    // 获取配置值（带默认值）
    public static String getProperty(String key, String defaultValue) {
        return propsReal.getProperty(key, defaultValue);
    }

    public static String getProperty_T(String key, String defaultValue) {
        return propsTemplate.getProperty(key, defaultValue);
    }
    // 获取配置值（无默认值）
    public static String getProperty(String key) {
        return propsReal.getProperty(key);
    }

    public static String getProperty_T(String key) {
        return propsTemplate.getProperty(key);
    }
}
