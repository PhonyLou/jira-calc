package com.tw.jiracalc.service;

import com.tw.jiracalc.model.card.JiraCards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardHttpServiceTest {
    @Test
    void CardHttpService_getCards_health_check() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CardHttpService cardHttpService = new CardHttpService(restTemplate);
        JiraCards expect = new JiraCards();

        doReturn(new ResponseEntity<>(expect, HttpStatus.OK))
                .when(restTemplate)
                .exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(JiraCards.class));

        JiraCards jiraCards = cardHttpService.getCards("jql", "jiraApiToken", "jiraHost");
        Assertions.assertEquals(expect, jiraCards);
    }

}
