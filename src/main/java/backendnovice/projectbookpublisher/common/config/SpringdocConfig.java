/**
 * @author : backendnovice@gmail.com
 * @date : 2023-07-04
 * @desc : Configure Springdoc.
 *
 * changelog :
 */

package backendnovice.projectbookpublisher.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {
    /**
     * Register OpenAPI as bean.
     * @param version
     *      Documentation version
     * @return
     *      OpenAPI
     */
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        Info info = new Info()
                .title("Project-Book-Publisher OpenAPI")
                .description("About API Documentation.")
                .version(version);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
