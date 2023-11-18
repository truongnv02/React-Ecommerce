package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.ColorRequest;
import com.poly.truongnvph29176.entity.Color;
import com.poly.truongnvph29176.service.ColorService;
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
@RequestMapping("${api.prefix}/colors")
public class ColorController {
    private final ColorService colorService;

    @GetMapping("")
    public ResponseEntity<?> getAllColors() {
        List<Color> list = colorService.findAllColors();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getColorById(@PathVariable("id") Integer id) {
        try {
            Color color = colorService.findColorById(id);
            return ResponseEntity.ok(color);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createColor(@Valid @RequestBody ColorRequest colorRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Color color = colorService.createColor(colorRequest);
                return ResponseEntity.ok(color);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateColor(@PathVariable("id") Integer id,
                                         @Valid @RequestBody ColorRequest colorRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Color color = colorService.updateColor(id, colorRequest);
                return ResponseEntity.ok(color);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable("id") Integer id) {
        colorService.deleteColor(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
