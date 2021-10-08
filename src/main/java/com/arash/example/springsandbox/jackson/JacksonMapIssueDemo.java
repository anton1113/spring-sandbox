package com.arash.example.springsandbox.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JacksonMapIssueDemo {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        ElasticAdDTO elasticAdDTO = new ElasticAdDTO("f1", "f2");
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO("b1", "b2");

//        Model model = new Model(Map.of(elasticAdDTO, List.of(subscriptionDTO)));
//        String modelJson = OBJECT_MAPPER.writeValueAsString(model);
//        System.out.println(modelJson);
//        Model modelDeserialized = OBJECT_MAPPER.readValue(modelJson, Model.class);
//        System.out.println("breakpoint");

        ElasticAdSubscriptionsMapping mapping = new ElasticAdSubscriptionsMapping(elasticAdDTO, List.of(subscriptionDTO));
        ProperModel properModel = new ProperModel(List.of(mapping));
        String properModelJson = OBJECT_MAPPER.writeValueAsString(properModel);
        System.out.println(properModelJson);
        ProperModel properModelDeserialized = OBJECT_MAPPER.readValue(properModelJson, ProperModel.class);
        System.out.println();
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class ElasticAdDTO {

    private String f1;
    private String f2;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class SubscriptionDTO {

    private String b1;
    private String b2;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Model {

    private Map<ElasticAdDTO, List<SubscriptionDTO>> data;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class ProperModel {

    private List<ElasticAdSubscriptionsMapping> mappings;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class ElasticAdSubscriptionsMapping {

    private ElasticAdDTO elasticAd;
    private List<SubscriptionDTO> subscriptions;
}
