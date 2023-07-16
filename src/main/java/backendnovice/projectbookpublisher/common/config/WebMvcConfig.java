/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-16
 * @desc : Configure resources path.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${image.resource.path}")
    private String resourcePath;

    @Value("${image.real.path}")
    private String realPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(realPath);
    }
}
