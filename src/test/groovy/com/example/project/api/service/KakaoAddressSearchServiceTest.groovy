package com.example.project.api.service

import com.example.project.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;


    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다." () {
        given:
        String address = null


        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)


        then:
        println("result = " + result )
        result == null
    }

    def "주소값이 valid이면, requestAddressSearch 메소드는 정상적으로 document를 반환한다." () {
        given:
        String address = "서울 성북구 종암로 10길"


        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)


        then:
        println("result.documentList.size() = " + result.documentList.size())
        println("result.metaDto.totalCount  = " + result.metaDto.totalCount)
        println("result.documentList.get(0).addressName = " + result.documentList.get(0).addressName)
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null

    }
}