package com.michal.remitlytaskapp.api;

import com.michal.remitlytaskapp.dto.RequestDto;
import com.michal.remitlytaskapp.dto.ResponseDto;
import com.michal.remitlytaskapp.service.ConverterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class ConverterController {

    private final ConverterService converterService;

    @PostMapping(path = "/convert")
    ResponseEntity<ResponseDto> convert(@Valid @RequestBody RequestDto data){
        ResponseDto response = converterService.convert(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
