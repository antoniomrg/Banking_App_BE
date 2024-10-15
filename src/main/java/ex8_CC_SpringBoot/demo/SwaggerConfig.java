package ex8_CC_SpringBoot.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.val;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@OpenAPIDefinition(
        info =  @Info(
                contact = @Contact(
                        name = "Antonio Morgano",
                        email = "antonio.morgano@yahoo.it"
                ),
                description = "OpenAPI documentation for banking application",
                title = "MyBankingApp API documentation",
                version = "1.0",
                termsOfService = "Terms of Service"
        )
)
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer sortTagsInCustomOrder() {
        return openApi -> {
            List<String> customOrder = Arrays.asList("Users", "Bank Accounts", "Bank Transactions");

            List<String> sortedTags = openApi.getTags().stream()
                    .map(Tag::getName)
                    .sorted(Comparator.comparingInt(customOrder::indexOf)).toList();

            openApi.setTags(sortedTags.stream()
                    .map(tagName -> openApi.getTags().stream()
                            .filter(tag -> tag.getName().equals(tagName))
                            .findFirst().orElse(null))
                    .collect(Collectors.toList()));
        };
    }

//    @Bean
//    public OpenApiCustomizer sortTagsExtended () {
//        return openApi -> {
//            List<String> customOrder = Arrays.asList("Users", "Bank Accounts", "Bank Transactions");
//
//            List<Tag> tags = openApi.getTags();
//            System.out.println(tags);
//
//            List<String> tagNames = new ArrayList<>();
//
//            for (Tag tag : tags) {
//                tagNames.add(tag.getName());
//            }
//            System.out.println(tagNames);
//
//            tagNames.sort(Comparator.comparingInt(customOrder::indexOf));
//
//            System.out.println("Sorted: " + tagNames);
//
//        };
//    }
}

