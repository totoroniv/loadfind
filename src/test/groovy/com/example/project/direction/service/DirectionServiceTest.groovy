package com.example.project.direction.service

import com.example.project.api.dto.DocumentDto
import com.example.project.api.service.KakaoCategorySearchService
import com.example.project.direction.repository.DirectionRepository
import com.example.project.loadfind.dto.LoadFindDto
import com.example.project.loadfind.service.LoadFindSearchService
import spock.lang.Specification

class DirectionServiceTest extends  Specification {


    private LoadFindSearchService loadFindSearchService = Mock()
    private DirectionRepository directionRepository = Mock()
    private KakaoCategorySearchService kakaoCategorySearchService = Mock()
    private Base62Service base62Service = Mock()


    private DirectionService directionService = new DirectionService(
            loadFindSearchService, directionRepository, kakaoCategorySearchService, base62Service)

    private  List<LoadFindDto> loadFindList

    def setup() {
        loadFindList = new ArrayList<>()
        loadFindList.addAll(
                LoadFindDto.builder()
                        .id(1L)
                        .loadName("돌곶이온누리약국")
                        .loadAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                LoadFindDto.builder()
                        .id(2L)
                        .loadName("호수온누리약국")
                        .loadAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )
    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인" () {

        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:

        println( documentDto.getAddressName() + " " + documentDto.getLatitude() + " " + documentDto.getLongitude())
        println( loadFindList.get(0).getProperties())
        println( loadFindList.get(1).getProperties())

        loadFindSearchService.searchLoadFindDtoList() >> loadFindList
        def results = directionService.buildDirectionList(documentDto)

        then:

        println( results.get(0).getProperties())
        println( results.get(1).getProperties())

        results.size() == 2
        results.get(0).targetLoadName == "호수온누리약국"
        results.get(1).targetLoadName == "돌곶이온누리약국"

    }

    def "buildDirectionList - 정해진 반경 10km 내에 검색이 되는지 확인" () {

        given:
        loadFindList.add(
                LoadFindDto.builder()
                        .id(3L)
                        .loadName("경기약국")
                        .loadAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        println( documentDto.getAddressName() + " " + documentDto.getLatitude() + " " + documentDto.getLongitude())
        println( loadFindList.get(0).getProperties())
        println( loadFindList.get(1).getProperties())
        println( loadFindList.get(2).getProperties())

        loadFindSearchService.searchLoadFindDtoList() >> loadFindList
        def results = directionService.buildDirectionList(documentDto)

        then:

        println( results.get(0).getProperties())
        println( results.get(1).getProperties())

        results.size() == 2
        results.get(0).targetLoadName == "호수온누리약국"
        results.get(1).targetLoadName == "돌곶이온누리약국"


    }



}
