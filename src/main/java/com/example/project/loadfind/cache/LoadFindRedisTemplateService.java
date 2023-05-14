package com.example.project.loadfind.cache;


import com.example.project.loadfind.dto.LoadFindDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadFindRedisTemplateService {

    private static final String CACHE_KEY = "LOADFIND";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;
    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(LoadFindDto loadFindDto) {
        if(Objects.isNull(loadFindDto) || Objects.isNull(loadFindDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    loadFindDto.getId().toString(),
                    serializeLoadFindDto(loadFindDto));

            log.info("[LoadFindRedisTemplateService save success] id: {}", loadFindDto.getId());

        } catch (Exception e) {
            log.error("[LoadFindRedisTemplateService save error] {}", e.getMessage());
        }

    }

    public List<LoadFindDto> findAll() {

        try {
            List<LoadFindDto> list = new ArrayList<>();

            for ( String value : hashOperations.entries(CACHE_KEY).values()) {
                LoadFindDto loadFindDto = deserializeLoadFindDto(value);
                list.add(loadFindDto);
            }

            return list;

        } catch (Exception e) {
            log.error("[LoadFindRedisTemplateService findAll error] {}", e.getMessage());
            return Collections.emptyList();
        }

    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[LoadFindRedisTemplateService delte]: {}", id);
    }


    private String serializeLoadFindDto(LoadFindDto loadFindDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(loadFindDto);
    }

    private LoadFindDto deserializeLoadFindDto(String value) throws  JsonProcessingException {
        return objectMapper.readValue(value, LoadFindDto.class);
    }


}
