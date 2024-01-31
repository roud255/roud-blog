package top.roud.roudblogcms.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author roud
 * @version 1.0.0
 * @describe
 * @date 2024-01-27 16:58
 */
//@Configuration
//public class JacksonConfiguration {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//
//        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializers()
//                .serializerByType(Long.class, ToStringSerializer.instance)
//                .serializerByType(Long.TYPE, ToStringSerializer.instance);
//    }
//}
public class CustomLongSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value > 1000000000L) {
            gen.writeString(value.toString());
        } else {
            gen.writeNumber(value);
        }
    }
}
