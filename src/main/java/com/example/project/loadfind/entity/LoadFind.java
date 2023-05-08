package com.example.project.loadfind.entity;

import com.example.project.BaseTimeEntity;
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
public class LoadFind extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loadName;
    private String loadAddress;
    private double latitude;
    private double longitude;


    public void changeLoadFindAddress(String address) {
        this.loadAddress = address;
    }

}
