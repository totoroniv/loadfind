package com.example.project.direction.service;

import com.example.project.api.dto.DocumentDto;
import com.example.project.direction.entity.Direction;
import com.example.project.loadfind.service.LoadFindSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {


    private static final int MAX_SEARCH_COUNT = 3;  // 최대 검색 수
    private static final double RADIUS_KM = 10.0;   // 반경 10 km

    private final LoadFindSearchService loadFindSearchService;

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return loadFindSearchService.searchLoadFindDtoList()
                .stream().map(loadFindDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetLoadName(loadFindDto.getLoadName())
                                .targetAddress(loadFindDto.getLoadAddress())
                                .targetLatitude(loadFindDto.getLatitude())
                                .targetLongitude(loadFindDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                loadFindDto.getLatitude(), loadFindDto.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());


    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6471;
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

    }




}
