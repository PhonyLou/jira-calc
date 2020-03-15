package com.tw.jiracalc.controller;

import com.tw.jiracalc.beans.card.JiraCard;
import com.tw.jiracalc.beans.card.JiraCards;
import com.tw.jiracalc.service.CardHttpService;
import com.tw.jiracalc.service.FileService;
import com.tw.jiracalc.service.JiraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class JiraControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    JiraService jiraService;
    @MockBean
    FileService fileService;
    @MockBean
    CardHttpService cardHttpService;

    @Test
    void return_string_when_normal() throws Exception {
        final String expect = "Mock Result";

        when(cardHttpService.getCards(any(), any())).thenReturn(new JiraCards());
        when(jiraService.enrichCardDetail(any(), any())).thenReturn(new JiraCards());
        when(fileService.generateCycleTimeFile(any(), any())).thenReturn("Mock Result");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("card-stage", "a, b, c");
        HttpHeaders httpHeaders = new HttpHeaders(headers);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/jira/getCardsFile").headers(httpHeaders));
        result.andExpect(status().is2xxSuccessful())
                .andExpect(content().string(expect));
    }
}
