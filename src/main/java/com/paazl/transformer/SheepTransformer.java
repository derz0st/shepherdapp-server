package com.paazl.transformer;

import com.paazl.data.Sheep;
import com.paazl.service.SheepDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SheepTransformer implements Transformer<Sheep, SheepDto> {

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SheepDto toDto(Sheep entity) {
        return modelMapper.map(entity, SheepDto.class);
    }
}
