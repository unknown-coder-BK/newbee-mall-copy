package ltd.newbee.mall.config.properties;

import ltd.newbee.mall.interceptor.MallLoginValidateInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${local.uploadDir}")
    private String uploadDir;

//    @Resource
//    private RepeatSubmitInterceptor repeatSubmitInterceptor;

////    @Value("${wayn.viewModel}")
////    private boolean viewModel;
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/", "/index");
//    }
//
//
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uploadDir + "/");
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + uploadDir + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MallLoginValidateInterceptor())
                  .excludePathPatterns("/")
                  .excludePathPatterns("/index")
                  .excludePathPatterns("/login")
                  .excludePathPatterns("/common/**")
                  .excludePathPatterns("/admin/**")
                  .excludePathPatterns("/goods-img/**")
                  .excludePathPatterns("/mall/**")
                  .excludePathPatterns("/seckill/list")
                  .excludePathPatterns("/seckill/detail/*")
                  .excludePathPatterns("/seckill/time/now")
                  .excludePathPatterns("/seckill/*/exposer");
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/logout")
//                .excludePathPatterns("/register")
//                .excludePathPatterns("/index")
//                .excludePathPatterns("/search")
//                .excludePathPatterns("/goods/**")
//                .excludePathPatterns("/shopCart/getUserShopCartCount")
//                .excludePathPatterns("/seckill/detail/*")

//                .excludePathPatterns("/upload/**")




//        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
//        registry.addInterceptor(new AdminLoginInterceptor())
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login")
//                .excludePathPatterns("/admin/dist/**")
//                .excludePathPatterns("/admin/plugins/**");
//

//        registry.addInterceptor(repeatSubmitInterceptor)
//                .addPathPatterns("/**");
//
//        registry.addInterceptor(new AdminViewModelInterceptor(viewModel))
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login")
//                .excludePathPatterns("/admin")
//                .excludePathPatterns("/admin/statistics")
//                .excludePathPatterns("/admin/goods")
//                .excludePathPatterns("/admin/goods/add")
//                .excludePathPatterns("/admin/goods/edit/*")
//                .excludePathPatterns("/admin/goods/list")
//                .excludePathPatterns("/admin/users")
//                .excludePathPatterns("/admin/users/list")
//                .excludePathPatterns("/admin/carousels")
//                .excludePathPatterns("/admin/carousels/list")
//                .excludePathPatterns("/admin/indexConfigs")
//                .excludePathPatterns("/admin/indexConfigs/list")
//                .excludePathPatterns("/admin/categories")
//                .excludePathPatterns("/admin/categories/list")
//                .excludePathPatterns("/admin/orders")
//                .excludePathPatterns("/admin/orders/list")
//                .excludePathPatterns("/admin/coupon")
//                .excludePathPatterns("/admin/coupon/list")
//                .excludePathPatterns("/admin/seckill")
//                .excludePathPatterns("/admin/seckill/list")
//                .excludePathPatterns("/admin/profile")
//                .excludePathPatterns("/admin/logout")
//                .excludePathPatterns("/admin/dist/**")
//                .excludePathPatterns("/admin/plugins/**");
    }

}
