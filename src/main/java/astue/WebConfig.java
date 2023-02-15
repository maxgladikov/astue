package astue;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/substations").setViewName("substations");
        registry.addViewController("/switchgears").setViewName("switchgears");
        registry.addViewController("/devices").setViewName("devices");
        registry.addViewController("/plants").setViewName("plants");
        registry.addViewController("/ieds").setViewName("ieds");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
//                .addResourceLocations("/webjars/");
    }
}
