package com.tw.jiracalc.service;

import com.tw.jiracalc.model.card.JiraCards;
import com.tw.jiracalc.model.history.JiraCardHistory;
import com.tw.jiracalc.util.CycleTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CardHttpService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    @Autowired
    public CardHttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JiraCards getCards(final String jql, final String jiraApiToken, final String jiraHost) {
        logger.info("JiraService.getCards starts");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", jiraApiToken);

        String url = jiraHost + "/rest/api/2/search?jql=" + jql;

        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<JiraCards> response = restTemplate.exchange(url, HttpMethod.GET, entity, JiraCards.class);

        logger.info("JiraService.getCards will return soon");
        return response.getBody();
    }

    @Async
    public CompletableFuture<Map<String, Double>> getCycleTime(final String jiraId, final String jiraToken, final String jiraHost) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", jiraToken);

        String url = jiraHost + "/rest/internal/2/issue/"+jiraId+"/activityfeed";
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<JiraCardHistory> response = restTemplate.exchange(url, HttpMethod.GET, entity, JiraCardHistory.class);

        JiraCardHistory jiraCardHistory = response.getBody();
        Map<String, Double> finalResult = CycleTimeHelper.calculateCycleTime(jiraCardHistory);

        return CompletableFuture.completedFuture(finalResult);
    }
}
