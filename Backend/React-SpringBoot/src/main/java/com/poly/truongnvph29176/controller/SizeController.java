package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.ColorRequest;
import com.poly.truongnvph29176.dto.request.SizeRequest;
import com.poly.truongnvph29176.entity.Color;
import com.poly.truongnvph29176.entity.Size;
import com.poly.truongnvph29176.service.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/sizes")
public class SizeController {
    private final SizeService sizeService;

    @GetMapping("")
    public ResponseEntity<?> getAllSizes() {
        List<Size> list = sizeService.findAllSizes();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSizeById(@PathVariable("id") Integer id) {
        try {
            Size size = sizeService.findSizeById(id);
            return ResponseEntity.ok(size);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createSize(@Valid @RequestBody SizeRequest sizeRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Size size = sizeService.createSize(sizeRequest);
                return ResponseEntity.ok(size);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSize(@PathVariable("id") Integer id,
                                        @Valid @RequestBody SizeRequest sizeRequest,
                                        BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Size size = sizeService.updateSize(id, sizeRequest);
                return ResponseEntity.ok(size);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable("id") Integer id) {
        try {
            sizeService.deleteSize(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
