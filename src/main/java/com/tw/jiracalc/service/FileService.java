package com.tw.jiracalc.service;

import com.tw.jiracalc.beans.card.JiraCard;
import com.tw.jiracalc.beans.card.JiraCards;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    public static String generateCycleTimeFile(JiraCards jiraCards, final List<String> cardStages) {

        final StringBuffer contentBuffer = new StringBuffer();
        final StringBuffer stageHeader = new StringBuffer();
        cardStages.forEach(stage -> stageHeader.append(",").append(stage));
        final String tableHeader = "jiraId,Issue Type,Status,Summary,Priority,Assignee,Reporter" + stageHeader.toString();
        contentBuffer.append(tableHeader);
        contentBuffer.append("\r\n");

        jiraCards.getIssues().forEach(jiraCard -> {
            contentBuffer
                    .append(jiraCard.getKey()).append(",")
                    .append(jiraCard.getFields().getIssuetype().getName()).append(",")
                    .append(jiraCard.getFields().getStatus().getStatusCategory().getName()).append(",")
                    .append(jiraCard.getFields().getSummary().replaceAll(",", " ")).append(",");

            if (null == jiraCard.getFields().getPriority()) {
                contentBuffer.append("-").append(",");
            } else {
                contentBuffer.append(jiraCard.getFields().getPriority().getName()).append(",");
            }

            if (null == jiraCard.getFields().getAssignee()) {
                contentBuffer.append("-").append(",");
            } else {
                contentBuffer.append(jiraCard.getFields().getAssignee().getDisplayName()).append(",");
            }

            contentBuffer
                    .append(jiraCard.getFields().getReporter().getDisplayName());
            cardStages.forEach(stage -> {
                final String stageCostStr = getLeadTimes(jiraCard, stage);
                contentBuffer.append(",").append(stageCostStr);
            });
            contentBuffer.append("\r\n");
        });

        return contentBuffer.toString();
    }

    private static String getLeadTimes(JiraCard jiraCard, String stageName) {
        Double stageTime = jiraCard.getFields().getCycleTimeBean().getCycleTime().get(stageName);
        String leadHours;
        if (null != stageTime) {
            leadHours = String.valueOf(stageTime);
        } else {
            leadHours = "-";
        }
        return leadHours;
    }
}
