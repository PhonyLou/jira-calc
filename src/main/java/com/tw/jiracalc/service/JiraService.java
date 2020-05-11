package com.tw.jiracalc.service;

import com.tw.jiracalc.model.card.JiraCards;
import com.tw.jiracalc.model.cycletime.CycleTimeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class JiraService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CardHttpService cardHttpService;

    @Autowired
    public JiraService(CardHttpService cardHttpService) {
        this.cardHttpService = cardHttpService;
    }

    public JiraCards getCards(final String jql, final String jiraApiToken, final String jiraHost) {
        logger.info("enrichCardDetail starts");
        final Map<String, CompletableFuture<Map<String, Double>>> cycleTimeMap = new HashMap<>();
        final JiraCards jiraCards = cardHttpService.getCards(jql, jiraApiToken, jiraHost);
        jiraCards.issues.forEach(card -> cycleTimeMap.put(card.key, cardHttpService.getCycleTime(card.key, jiraApiToken, jiraHost)));
        logger.info("enrichCardDetail ends");

        jiraCards.issues.forEach(card -> {
            Map<String, Double> cycleTime = null;
            try {
                cycleTime = cycleTimeMap.get(card.key).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            CycleTimeBean cycleTimeBean = new CycleTimeBean();
            cycleTimeBean.cycleTime = cycleTime;
            card.fields.cycleTimeBean = cycleTimeBean;
        });

        logger.info("enrichCardDetail returns");
        return jiraCards;
    }
}
