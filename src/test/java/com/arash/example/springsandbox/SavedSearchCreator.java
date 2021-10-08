package com.arash.example.springsandbox;

import com.arash.example.springsandbox.util.ResourceReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SavedSearchCreator {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final String GRAPHQL_URL = "https://api-customer.1.qa.retail.auto1.cloud/v1/retail-customer-gateway/graphql";
    private static final String USERNAME = "saved-search-dev@ukr.net";
    private static final String PASSWORD = "saved-search-password";

    private static final int GENERATE_SAVED_SEARCHES_COUNT = 501;
    private static final int MAX_PRICE = 75_000;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void generateSavedSearches() {
        long startedAt = System.currentTimeMillis();
        String authToken = auth();
        for (int i = 0; i < GENERATE_SAVED_SEARCHES_COUNT; i++) {
            THREAD_POOL.submit(() -> createSavedSearch(authToken, ))
            createSavedSearch(authToken, i, 5000 + i);
        }
        long finishedAt = System.currentTimeMillis();
        log.info("Time elapsed: {} millis", (finishedAt - startedAt));
    }

    @Test
    void deleteAll() {
        long startedAt = System.currentTimeMillis();
        String authToken = auth();
        deleteAll(authToken);
        long finishedAt = System.currentTimeMillis();
        log.info("Time elapsed: {} millis", (finishedAt - startedAt));
    }

    @Test
    void findAll() {
        long startedAt = System.currentTimeMillis();
        String authToken = auth();
        findAll(authToken);
        long finishedAt = System.currentTimeMillis();
        log.info("Time elapsed: {} millis", (finishedAt - startedAt));
    }

    private String auth() {
        String authQuery = String.format(ResourceReader.readFile("/gql/auth.gql"), PASSWORD, USERNAME);
        String response = sendGraphqlQuery(authQuery, String.class).getBody();
        return extractAccessToken(response);
    }

    private void createSavedSearch(String authToken, int priceFilter) {
        String createSavedSearchQuery = String.format(
                ResourceReader.readFile("/gql/create_saved_search.gql"),
                priceFilter, "DE", priceFilter, priceFilter * 100);
        sendGraphqlQuery(createSavedSearchQuery, authToken, String.class);
    }

    private List<String> findAll(String authToken) {
        List<String> ids = new ArrayList<>();
        int pageNumber = 0;
        List<String> pageIds = findSavedSearchPage(authToken, pageNumber, 25);
        while (!pageIds.isEmpty()) {
            ids.addAll(pageIds);
            pageNumber++;
            pageIds = findSavedSearchPage(authToken, pageNumber, 25);
        }
        return ids;
    }

    private List<String> findSavedSearchPage(String authToken, int pageNumber, int pageSize) {
        String findSavedSearchPageQuery = String.format(
                ResourceReader.readFile("/gql/find_saved_search.gql"), pageNumber, pageSize);
        String response = sendGraphqlQuery(findSavedSearchPageQuery, authToken, String.class).getBody();
        return extractSavedSearchIds(response);
    }

    private void deleteSavedSearch(String authToken, String savedSearchId) {
        String createSavedSearchQuery = String.format(
                ResourceReader.readFile("/gql/delete_saved_search.gql"), savedSearchId);
        log.debug("Deleting saved search with id [{}]", savedSearchId);
        sendGraphqlQuery(createSavedSearchQuery, authToken, String.class);
    }

    private void deleteAll(String authToken) {
        List<String> savedSearchIds = findAll(authToken);
        savedSearchIds.forEach(id -> deleteSavedSearch(authToken, id));
    }

    private <T> ResponseEntity<T> sendGraphqlQuery(String query, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String rawQuery = toRawQuery(query);
        RequestEntity<String> req = new RequestEntity<>(rawQuery, headers, HttpMethod.POST, URI.create(GRAPHQL_URL));
        return restTemplate.exchange(req, responseType);
    }

    private <T> ResponseEntity<T> sendGraphqlQuery(String query, String authToken, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        String rawQuery = toRawQuery(query);
        RequestEntity<String> req = new RequestEntity<>(rawQuery, headers, HttpMethod.POST, URI.create(GRAPHQL_URL));
        return restTemplate.exchange(req, responseType);
    }

    @SneakyThrows
    private String toRawQuery(String query) {
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        return MAPPER.writeValueAsString(map);
    }

    @SneakyThrows
    private String extractAccessToken(String response) {
        return MAPPER.readTree(response).get("data").get("signIn").get("accessToken").asText();
    }

    @SneakyThrows
    private String extractSavedSearchId(String response) {
        return MAPPER.readTree(response).get("data").get("createSavedSearch").get("id").asText();
    }

    @SneakyThrows
    private List<String> extractSavedSearchIds(String response) {
        Iterator<JsonNode> iterator = MAPPER.readTree(response)
                .get("data").get("findSavedSearch").get("entities").elements();
        List<String> results = new ArrayList<>();
        while (iterator.hasNext()) {
            results.add(iterator.next().get("id").asText());
        }
        return results;
    }
}
