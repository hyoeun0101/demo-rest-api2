package com.example.demorestapi2.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 필드 네임
        jsonGenerator.writeFieldName("errors");
        jsonGenerator.writeStartArray();

        errors.getFieldErrors().forEach(error -> {
            try {
                jsonGenerator.writeStartObject();

                jsonGenerator.writeStringField("field", error.getField());
                jsonGenerator.writeStringField("objectName", error.getObjectName());
                jsonGenerator.writeStringField("code", error.getCode());
                jsonGenerator.writeStringField("defaultMessage", error.getDefaultMessage());




                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });



        jsonGenerator.writeEndArray();
    }
}
