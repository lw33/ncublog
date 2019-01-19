package cn.edu.ncu.ncublog.config;

import cn.edu.ncu.ncublog.interceptor.LoginInterceptor;
import cn.edu.ncu.ncublog.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author lw
 * @Date 2018-09-01 19:47:15
 **/
@Component
public class SpringMVCConfig extends WebMvcConfigurerAdapter{

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/", "/index","/index/*","/log*","/reg*","/log*/","/reg*/");
        super.addInterceptors(registry);
    }
}
