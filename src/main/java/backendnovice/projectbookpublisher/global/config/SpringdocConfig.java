/**
 * @author : backendnovice@gmail.com
 * @date : 2023-06-30
 * @desc : Springdoc 설정 클래스
 *
 * 변경 내역 :
 */

package backendnovice.projectbookpublisher.global.config;

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
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        Info info = new Info()
                .title("Book Publisher Open API")
                .description("About API Documentation.")
                .version(version);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}