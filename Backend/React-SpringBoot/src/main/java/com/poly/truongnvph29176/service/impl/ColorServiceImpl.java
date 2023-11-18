package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.ColorRequest;
import com.poly.truongnvph29176.entity.Color;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.ColorRepository;
import com.poly.truongnvph29176.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color createColor(ColorRequest colorRequest) {
        Color color = Color.builder()
                .name(colorRequest.getName())
                .build();
        return colorRepository.save(color);
    }

    @Override
    public Color findColorById(Integer id) throws Exception {
        return colorRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Color with id = " + id)
                );
    }

    @Override
    public Color updateColor(Integer id, ColorRequest colorRequest) throws Exception {
        Color color = findColorById(id);
        color.setName(colorRequest.getName());
        colorRepository.save(color);
        return color;
    }

    @Override
    public void deleteColor(Integer id) {
        colorRepository.deleteById(id);
    }
}
