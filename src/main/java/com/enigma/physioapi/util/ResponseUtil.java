package com.enigma.physioapi.util;

import com.enigma.physioapi.dto.response.CommonResponse;
import com.enigma.physioapi.dto.response.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {
  public static <T> ResponseEntity<CommonResponse<T>> buildResponse(HttpStatus httpStatus, String message, T data) {
    CommonResponse<T> response = new CommonResponse<>(httpStatus.value(), message, data, null);
    return ResponseEntity.status(httpStatus).body(response);
  }

  public static <T> ResponseEntity<CommonResponse<?>> buildResponsePaging(HttpStatus httpStatus, String message, Page<T> page) {
    PagingResponse pagingResponse = PagingResponse.builder()
            .totalPages(page.getTotalPages())
            .totalItems(page.getTotalElements())
            .page(page.getPageable().getPageNumber() + 1)
            .size(page.getSize())
            .build();

    CommonResponse<List<T>> response = new CommonResponse<>(
            httpStatus.value(),
            message,
            page.getContent(),
            pagingResponse
    );
    return ResponseEntity.status(httpStatus).body(response);
  }

}
