package com.rupesh.userservice.util.global;

import com.rupesh.userservice.dto.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class GlobalUtil<T> {

    public static <T> GlobalResponse<T> globalResponse(final String message,
                                                           final HttpStatus httpStatus,
                                                           final Pageable pageable,
                                                           final T data) {
        return GlobalResponse
                .<T>builder()
                .message(message)
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .timeStamp(LocalDateTime.now())
                .data(data)
                .pageable(pageable)
                .build();
    }

}
