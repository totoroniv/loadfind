package com.example.project.loadfind.service

import com.example.project.loadfind.cache.LoadFindRedisTemplateService
import com.example.project.loadfind.dto.LoadFindDto
import com.example.project.loadfind.entity.LoadFind
import org.assertj.core.util.Lists
import spock.lang.Specification

class LoadFindSearchServiceTest extends Specification {



    private LoadFindSearchService loadFindSearchService

    private LoadFindRepositoryService loadFindRepositoryService = Mock()
    private LoadFindRedisTemplateService loadFindRedisTemplateService = Mock()

    private List<LoadFind> loadFindList

    def setup() {
        loadFindSearchService = new LoadFindSearchService( loadFindRepositoryService, loadFindRedisTemplateService)

        loadFindList = Lists.newArrayList(
                LoadFind.builder()
                        .id(1L)
                        .loadName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                LoadFind.builder()
                        .id(2L)
                        .loadName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build())

    }

    def "레디스 장애시 DB를 이용하여 약국 데이터 조회"() {
        when:
        loadFindRedisTemplateService.findAll() >> []
        loadFindRepositoryService.findAll() >> loadFindList


        def result = loadFindSearchService.searchLoadFindDtoList()

        then:
        result.size() == 2
    }


}
