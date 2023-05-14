package com.example.project.loadfind.cache

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.loadfind.dto.LoadFindDto
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class LoadFindRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private LoadFindRedisTemplateService loadFindRedisTemplateService

    def setup() {

        loadFindRedisTemplateService.findAll()
            .forEach (dto -> {
                loadFindRedisTemplateService.delete(dto.getId())
            })
    }

    def "save success"() {
        given:
        String loadName = "name"
        String loadAddress = "address"
        LoadFindDto dto =
            LoadFindDto.builder()
                .id(1L)
                .loadName(loadName)
                .loadAddress(loadAddress)
                .build()
        when:
        loadFindRedisTemplateService.save(dto)
        List<LoadFindDto> result = loadFindRedisTemplateService.findAll()

        then:
        result.size() ==1
        result.get(0).id == 1L
        result.get(0).loadName == loadName
        result.get(0).loadAddress == loadAddress

    }

    def "success fail" () {
        given:
        LoadFindDto dto = LoadFindDto.builder().build()

        when:
        loadFindRedisTemplateService.save(dto)
        List<LoadFindDto> result = loadFindRedisTemplateService.findAll()

        then:
        result.size() == 0

    }



    def "delete" () {
        given:
        String loadName = "name"
        String loadAddress = "address"
        LoadFindDto dto =
                LoadFindDto.builder()
                        .id(1L)
                        .loadName(loadName)
                        .loadAddress(loadAddress)
                        .build()
        when:
        loadFindRedisTemplateService.save(dto)
        loadFindRedisTemplateService.delete(dto.getId())

        def result = loadFindRedisTemplateService.findAll()

        then:
        result.size() == 0
    }


}
