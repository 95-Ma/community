package motor.community.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 自定义拦截器类
 *
 * @author motor
 * @create 2021-01-08-16:43
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private SessionInterceptor sessionInterceptor;


    /**
     * 存储用户状态拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截的请求范围
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}
