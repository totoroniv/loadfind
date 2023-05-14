package com.example.project.loadfind.controller;

import com.example.project.loadfind.cache.LoadFindRedisTemplateService;
import com.example.project.loadfind.dto.LoadFindDto;
import com.example.project.loadfind.service.LoadFindRepositoryService;
import com.example.project.loadfind.service.LoadFindSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class LoadFindController  {

    private final LoadFindRepositoryService loadFindRepositoryService;
    private final LoadFindRedisTemplateService loadFindRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메서드

    @GetMapping("/redis/save")

    public String save() {

        List<LoadFindDto> loadFindDtoList = loadFindRepositoryService.findAll()
                .stream().map(loadFind -> LoadFindDto.builder()
                        .id(loadFind.getId())
                        .loadName(loadFind.getLoadName())
                        .loadAddress(loadFind.getLoadAddress())
                        .latitude(loadFind.getLatitude())
                        .longitude(loadFind.getLongitude())
                        .build())
                .collect(Collectors.toList());

        loadFindDtoList.forEach(loadFindRedisTemplateService::save);

        return "success";
    }

}
