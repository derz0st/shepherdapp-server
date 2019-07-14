package com.paazl.service;

import java.util.List;

public class SheepOrderDto {
    private List<SheepDto> orderedSheeps;

    public SheepOrderDto() {
    }

    public SheepOrderDto(List<SheepDto> orderedSheeps) {
        this.orderedSheeps = orderedSheeps;
    }

    public List<SheepDto> getOrderedSheeps() {
        return orderedSheeps;
    }

    public void setOrderedSheeps(List<SheepDto> orderedSheeps) {
        this.orderedSheeps = orderedSheeps;
    }
}
