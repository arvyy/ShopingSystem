package lt.mif.ise;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

	@Value("${static.resources.location}")
	String staticResourcesLocation;
	
	@Value("${ise.productImage.location}")
	String productImagesLocation;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = staticResourcesLocation;
		if (!location.endsWith("/")) location += "/";
        registry.addResourceHandler("/**").addResourceLocations(location);
        
        String imgLocation = productImagesLocation;
        if (!imgLocation.endsWith("/")) location += "/";
        new File(imgLocation).mkdir();
        registry.addResourceHandler("/product-image/**").addResourceLocations(imgLocation);	
    }
	
}
