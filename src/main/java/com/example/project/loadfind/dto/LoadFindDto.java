package com.example.project.loadfind.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadFindDto
{
    private Long id;
    private String loadName;
    private String loadAddress;
    private double latitude;
    private double longitude;

}
