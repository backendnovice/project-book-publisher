/**
 * @author    : backendnovice@gmail.com
 * @date      : 2023-07-19
 * @desc      : Springdoc 을 설정하는 클래스.
 * @changelog :
 * 23-07-19 - backendnovice@gmail.com - 주석 한글화 수정
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
     * Open API를 설정하고 Bean으로 등록한다.
     * @param version
     *      문서 버전
     * @return
     *      OpenAPI
     */
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String version) {
        Info info = new Info()
                .title("project-book-publisher OpenAPI")
                .description("About API Documentation.")
                .version(version);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
