package com.example.project.direction.controller

import com.example.project.direction.dto.OutputDto
import com.example.project.loadfind.service.LoadFindRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private LoadFindRecommendationService loadFindRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(loadFindRecommendationService))
            .build()


        outputDtoList = new ArrayList<>()

        outputDtoList.addAll(
                OutputDto.builder()
                        .loadFindName("laod1")
                        .build(),
                OutputDto.builder()
                        .loadFindName("load2")
                        .build()


        )

    }

    def "GET /" () {
        expect:
        mockMvc.perform(get("/"))
            .andExpect (handler().handlerType(FormController.class))
            .andExpect(handler().methodName("main"))
            .andExpect (status().isOk())
            .andExpect (view().name("main"))
            .andDo (log())

    }

    def "POST /search" () {

        given:
        String inputAddress = "서울 성북구 종암동"

        when:
        def resultActions = mockMvc.perform ( post("/search")
                .param("address", inputAddress))

        then:
        1 * loadFindRecommendationService.recommendationLoadFindList(argument -> {
            assert argument == inputAddress
        }) >> outputDtoList

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputFormList"))
                .andExpect(model().attribute("outputFormList", outputDtoList))
                .andDo(print())
    }

}
