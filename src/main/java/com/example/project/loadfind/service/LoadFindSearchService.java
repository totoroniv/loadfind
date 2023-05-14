package com.example.project.loadfind.service;

import com.example.project.loadfind.cache.LoadFindRedisTemplateService;
import com.example.project.loadfind.dto.LoadFindDto;
import com.example.project.loadfind.entity.LoadFind;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadFindSearchService {

    private final LoadFindRepositoryService loadFindRepositoryService;

    private final LoadFindRedisTemplateService loadFindRedisTemplateService;

    public List<LoadFindDto> searchLoadFindDtoList() {

        //redis
        List<LoadFindDto>  loadFindDtoList = loadFindRedisTemplateService.findAll();
        if ( !loadFindDtoList.isEmpty()) {
            log.info("redis findAll success");
            return loadFindDtoList;
        }

        //db
        return loadFindRepositoryService.findAll()
                .stream()
                .map(this::convertToLoadFindDto)
                .collect(Collectors.toList());
    }

    private LoadFindDto convertToLoadFindDto(LoadFind loadFind) {

        return LoadFindDto.builder()
                .id(loadFind.getId())
                .loadAddress(loadFind.getLoadAddress())
                .loadName(loadFind.getLoadName())
                .latitude(loadFind.getLatitude())
                .longitude(loadFind.getLongitude())
                .build();
    }

}
