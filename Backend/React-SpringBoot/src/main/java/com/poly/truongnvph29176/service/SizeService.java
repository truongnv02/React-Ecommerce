package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.SizeRequest;
import com.poly.truongnvph29176.entity.Size;

import java.util.List;

public interface SizeService {
    List<Size> findAllSizes();
    Size createSize(SizeRequest sizeRequest);
    Size findSizeById(Integer id) throws Exception;
    Size updateSize(Integer id, SizeRequest sizeRequest) throws Exception;
    void deleteSize(Integer id) throws Exception;
}
