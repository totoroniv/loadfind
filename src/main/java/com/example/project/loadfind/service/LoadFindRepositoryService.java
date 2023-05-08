package com.example.project.loadfind.service;

import com.example.project.loadfind.entity.LoadFind;
import com.example.project.loadfind.repository.LoadFindRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadFindRepositoryService {

    private final LoadFindRepository loadFindRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        LoadFind entity = loadFindRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[LoadFindRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        entity.changeLoadFindAddress(address);
    }


    public void updateAddressWithoutTransaction(Long id, String address) {
        LoadFind entity = loadFindRepository.findById(id).orElse(null);

        if (Objects.isNull(entity)) {
            log.error("[LoadFindRepositoryService updateAddressWithoutTransaction] not found id: {}", id);
            return;
        }

        entity.changeLoadFindAddress(address);
    }



}
