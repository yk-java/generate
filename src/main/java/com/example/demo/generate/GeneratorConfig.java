package com.example.demo.generate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yk
 */
@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfig {

    private String url;
    private String username;
    private String password;
    private String databaseName;
    private String sourcePackage;
    private String webPackage;
    private String businessPackage;
    private String templatePath;
    private String projectPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getSourcePackage() {
        return sourcePackage;
    }

    public void setSourcePackage(String sourcePackage) {
        this.sourcePackage = sourcePackage;
    }

    public String getWebPackage() {
        return webPackage;
    }

    public void setWebPackage(String webPackage) {
        this.webPackage = webPackage;
    }

    public String getBusinessPackage() {
        return businessPackage;
    }

    public void setBusinessPackage(String businessPackage) {
        this.businessPackage = businessPackage;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public String toString() {
        return "GeneratorConfig{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", sourcePackage='" + sourcePackage + '\'' +
                ", webPackage='" + webPackage + '\'' +
                ", businessPackage='" + businessPackage + '\'' +
                ", templatePath='" + templatePath + '\'' +
                ", projectPath='" + projectPath + '\'' +
                '}';
    }
}
