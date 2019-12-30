package com.example.demo.generate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * 读取模板文件进行生成
 *
 * @author yk
 */
public class CommonPageParser {

    private CommonPageParser() {
    }

    private static VelocityEngine ve;
    private static final Log log = LogFactory.getLog(CommonPageParser.class);

    private static boolean isReplace = false;

    private static void getVelocityEngine(){
        String templateBasePath = AbstractCodeGenerateFactory.getProjectPath()
                + "src/main/resources/templates";
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.description",
                "Velocity File Resource Loader");
        properties.setProperty("file.resource.loader.path",
                templateBasePath);
        properties.setProperty("file.resource.loader.cache", "true");
        properties.setProperty(
                "file.resource.loader.modificationCheckInterval", "30");
        properties.setProperty("runtime.log.logsystem.class",
                "org.apache.velocity.runtime.log.Log4JLogChute");
        properties.setProperty("runtime.log.logsystem.log4j.logger",
                "org.apache.velocity");
        properties.setProperty("directive.set.null.allowed", "true");
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
        ve = velocityEngine;
    }

    public static void writerPage(VelocityContext context, String templateName,
                                  String fileDirPath, String targetFile) {
        getVelocityEngine();
        File file = new File(fileDirPath + targetFile);
        if (!(file.exists())) {
            isReplace = new File(file.getParent()).mkdirs();
        } else if (!isReplace) {
            log.info("替换文件:" + file.getAbsolutePath());
        }
        try (FileOutputStream fos = new FileOutputStream(file); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                fos, StandardCharsets.UTF_8))) {

            Template template = ve.getTemplate(templateName, "UTF-8");

            template.merge(context, writer);
            writer.flush();

            log.info("生成文件：" + file.getAbsolutePath());
        } catch (Exception e) {
            log.error(e);
        }
    }
}
