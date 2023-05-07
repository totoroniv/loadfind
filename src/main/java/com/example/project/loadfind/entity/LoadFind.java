package com.example.project.loadfind.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "loadfind")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadFind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loadName;
    private String loadAddress;
    private double latitude;
    private double longitude;

}
