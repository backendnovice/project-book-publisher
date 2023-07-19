/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : 리소스 경로를 설정하는 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
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
