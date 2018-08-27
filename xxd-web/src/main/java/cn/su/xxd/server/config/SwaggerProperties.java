package cn.su.xxd.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@Data
@ConfigurationProperties(prefix = "xxd.swagger")
public class SwaggerProperties {
	private String title = "xxd Application API";
	private String description = "API documentation";
	private String version = "1.0.0";
	private String termsOfServiceUrl;
	private String contactName = "xxd";
	private String contactUrl = "http://www.github.com";
	private String contactEmail = "ekkoxusu@163.com";
	private String license;
	private String licenseUrl;
	private String defaultIncludePattern = "/.*";
	private String basePackage = "cn.su.xxd.server.controller";
	private String host;
}
