package com.example.project.loadfind.repository

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.loadfind.entity.LoadFind
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime


class LoadFindRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private LoadFindRepository loadFindRepository

    def setup() {

        loadFindRepository.deleteAll()
    }

    def "LoadFindRepository save"() {
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def loadFind
                = LoadFind.builder()
                .loadAddress(address)
                .loadName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = loadFindRepository.save(loadFind)

        then:
        result.getLoadAddress() == address
        result.getLoadName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }


    def "LoadFindRepository saveAll" () {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def loadFind
                = LoadFind.builder()
                .loadAddress(address)
                .loadName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        loadFindRepository.saveAll (Arrays.asList(loadFind))
        def result = loadFindRepository.findAll()

        then:
        println( "result.size() = " + result.size() )
        result.size() == 1

    }

    def "BaseTimeEntity 등록" () {
        given:
        def now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def loadFind
                = LoadFind.builder()
                .loadAddress(address)
                .loadName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        loadFindRepository.save(loadFind)
        def result = loadFindRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)


    }


}