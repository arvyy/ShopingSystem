package lt.mif.ise;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

	@Value("${static.resources.location}")
	String staticResourcesLocation;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = staticResourcesLocation;
		if (!location.endsWith("/")) location += "/";
        registry.addResourceHandler("/**").addResourceLocations(location);
    }
	
}
