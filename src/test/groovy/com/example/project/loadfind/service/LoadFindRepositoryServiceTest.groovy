package com.example.project.loadfind.service

import com.example.project.AbstractIntegrationContainerBaseTest
import com.example.project.loadfind.entity.LoadFind
import com.example.project.loadfind.repository.LoadFindRepository
import org.springframework.beans.factory.annotation.Autowired

class LoadFindRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {


    @Autowired
    private LoadFindRepositoryService loadFindRepositoryService

    @Autowired
    private LoadFindRepository loadFindRepository

    def setup () {
        loadFindRepository.deleteAll()
    }


    def "LoadFindRepository update - dirty checking seuccess"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifyAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def loadFind
                = LoadFind.builder()
                .loadAddress(inputAddress)
                .loadName(name)
                .build()

        when:
        def entity = loadFindRepository.save(loadFind)
        loadFindRepositoryService.updateAddress(entity.getId(), modifyAddress)

        def result = loadFindRepository.findAll()

        then:

        println("result.get(0).getLoadAddress() = " + result.get(0).getLoadAddress())
        println("modifyAddress                  = " + modifyAddress)

        result.get(0).getLoadAddress() == modifyAddress


    }

    def "LoadFindRepository update - dirty checking fail"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifyAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def loadFind
                = LoadFind.builder()
                .loadAddress(inputAddress)
                .loadName(name)
                .build()

        when:
        def entity = loadFindRepository.save(loadFind)
        loadFindRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifyAddress)

        def result = loadFindRepository.findAll()

        then:

        println("result.get(0).getLoadAddress() = " + result.get(0).getLoadAddress())
        println("modifyAddress                  = " + inputAddress)

        result.get(0).getLoadAddress() == inputAddress


    }

}
