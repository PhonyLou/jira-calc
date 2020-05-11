package com.tw.jiracalc.controller;

import com.tw.jiracalc.service.FileService;
import com.tw.jiracalc.service.JiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jira")
public class JiraController {

    private final JiraService jiraService;
    private final FileService fileService;

    @Autowired
    public JiraController(JiraService jiraService, FileService fileService) {
        this.jiraService = jiraService;
        this.fileService = fileService;
    }

    @GetMapping(value = "/getCardsFile")
    String getCardsFile(@RequestHeader Map<String, String> header) {
        final List<String> displayStages = new ArrayList<>(Arrays.asList(header.get("card-stage").split(",")))
                .stream().map(String::trim).collect(Collectors.toList());

        return fileService.generateCycleTimeFile(
                jiraService.getCards(header.get("jql"), header.get("jira-token"), header.get("jira-host")),
                displayStages);
    }

}
