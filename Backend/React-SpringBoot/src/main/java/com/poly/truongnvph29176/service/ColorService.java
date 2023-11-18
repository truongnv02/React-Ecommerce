package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.ColorRequest;
import com.poly.truongnvph29176.entity.Color;

import java.util.List;

public interface ColorService {
    List<Color> findAllColors();
    Color createColor(ColorRequest colorRequest);
    Color findColorById(Integer id) throws Exception;
    Color updateColor(Integer id, ColorRequest colorRequest) throws Exception;
    void deleteColor(Integer id);
}
