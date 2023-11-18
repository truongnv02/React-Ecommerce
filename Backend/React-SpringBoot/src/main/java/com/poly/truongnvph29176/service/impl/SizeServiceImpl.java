package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.SizeRequest;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.entity.Size;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.SizeRepository;
import com.poly.truongnvph29176.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    @Override
    public List<Size> findAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public Size createSize(SizeRequest sizeRequest) {
        Size size = Size.builder()
                .value(sizeRequest.getValue())
                .build();
        return sizeRepository.save(size);
    }

    @Override
    public Size findSizeById(Integer id) throws Exception {
        return sizeRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Size with id = " + id)
                );
    }

    @Override
    public Size updateSize(Integer id, SizeRequest sizeRequest) throws Exception {
        Size size = findSizeById(id);
        size.setValue(sizeRequest.getValue());
        sizeRepository.save(size);
        return size;
    }

    @Override
    public void deleteSize(Integer id) throws Exception {
        Size size = findSizeById(id);
        if(!size.getProductDetails().isEmpty()) {
            throw new RuntimeException("The Size cannot be deleted because it is related to product_detail.");
        }
        sizeRepository.delete(size);
    }
}
