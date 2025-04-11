package com.geckotech.assessment.integration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class Requester {
    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;

    public Requester(
        WebApplicationContext context,
        ObjectMapper objectMapper
    ) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    private MockMvc mockMvc() {
        return MockMvcBuilders
            .webAppContextSetup(context)
            .build();
    }

    public MvcResult jsonPost(
        String relativeUrl,
        String accessToken,
        Object payload,
        Map<String, String> headers
    ) throws Exception {
        return post(relativeUrl, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, payload, headers);
    }

    public MvcResult jsonDelete(String relativeUrl, String accessToken)  throws Exception {
        MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete(relativeUrl);
        delete.accept(MediaType.APPLICATION_JSON);
        return mockMvc().perform(delete)
            .andReturn();
    }

    public MvcResult jsonGet(String relativeUrl, String accessToken) throws Exception {
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get(relativeUrl);
        return performRequest(get);
    }

    // todo think of overview pagination
    public MvcResult jsonGet(
        String relativeUrl,
        String accessToken, // todo think of authorization
        MediaType accept,
        Integer page,
        Integer size
    ) throws Exception {
        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("$relativeUrl?page=$page&size=$size");
        get.accept(accept);
        return performRequest(get);
    }

    private MvcResult performRequest(
        MockHttpServletRequestBuilder get
    ) throws Exception {
        return mockMvc().perform(get)
            .andReturn();
    }

    private MvcResult post(
        String relativeUrl,
        MediaType responseType,
        MediaType requestType,
        Object payload,
        Map<String, String> headers
    ) throws Exception {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(relativeUrl);

        headers.forEach(post::header);

        post.accept(responseType)
            .contentType(requestType)
            .content(objectMapper.writeValueAsString(payload));
        return mockMvc().perform(post)
            .andReturn();
    }
}
