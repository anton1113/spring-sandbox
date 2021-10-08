package com.arash.example.springsandbox;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class DateTimeFormatFoo {

    public static void main(String[] args) throws IOException {
        String fmt1 = "yyyy-MM-dd'T'HH:mm:ssZ";
        String fmt2 = "yyyy-MM-dd'T'HH:mm:ss[.SSS]Z";

        ObjectMapper mpr1 = newMapper(fmt1);
        ObjectMapper mpr2 = newMapper(fmt2);

        String dateTime = "2021-08-17T13:54:05+0200";
//        OffsetDateTime dt1 = mpr1.readValue(dateTime, OffsetDateTime.class);
        OffsetDateTime dt2 = mpr2.readValue(dateTime, OffsetDateTime.class);

        System.out.println("wow");
    }

    private static ObjectMapper newMapper(String format) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(newJavaTimeModule(format));
        return mapper;
    }

    private static JavaTimeModule newJavaTimeModule(String dateTimeFormat) {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter formatter = ofPattern(dateTimeFormat).withZone(ZoneId.systemDefault());

        DateTimeFormatter readerFormatter = ofPattern(dateTimeFormat);
        module.addSerializer(OffsetDateTime.class, offsetDateTimeSerializer(formatter));
        module.addDeserializer(OffsetDateTime.class, offsetDateTimeDeserializer(readerFormatter));

        module.addSerializer(Instant.class, instantSerializer(formatter));
        module.addDeserializer(Instant.class, instantDeserializer(readerFormatter));

        return module;
    }

    private static StdSerializer<OffsetDateTime> offsetDateTimeSerializer(java.time.format.DateTimeFormatter formatter) {
        return new OffsetDateTimeSerializer(OffsetDateTimeSerializer.INSTANCE, false, formatter) {};
    }

    private static StdDeserializer<OffsetDateTime> offsetDateTimeDeserializer(java.time.format.DateTimeFormatter formatter) {
        return new InstantDeserializer<>(InstantDeserializer.OFFSET_DATE_TIME, formatter) {};
    }

    private static StdSerializer<Instant> instantSerializer(DateTimeFormatter formatter) {
        return new StdSerializer<>(Instant.class) {
            @Override
            public void serialize(final Instant instant, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
                generator.writeString(formatter.format(instant));
            }
        };
    }

    private static StdDeserializer<Instant> instantDeserializer(DateTimeFormatter formatter) {
        return new InstantDeserializer<>(InstantDeserializer.INSTANT, formatter) {};
    }
}
