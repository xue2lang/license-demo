package org.example.licenseplatform.config;

import org.example.licenseplatform.interceptor.LicenseVerifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LicenseVerifyInterceptor licenseVerifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(licenseVerifyInterceptor)
                .addPathPatterns("/**") //  拦截所有路径
                .excludePathPatterns(
                "/license/generate", // License 生成接口
                "/license/verify",  // License 验证接口
                 "/machine/info",   // 机器信息接口
                "/health",          // 健康检查接口
                "/actuator/**",     // Spring Actuator
                "/static/**",      // 静态资源
                "/favicon.ico",    // 网站图标
                "/error"         //    错误页面
                );
    }
}
