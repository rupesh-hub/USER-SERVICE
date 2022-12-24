package com.rupesh.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    private String addressId;
    private String country;
    private String zone;
    private String city;
    private String street;
    private int zip;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long createdBy;
    private Long modifiedBy;
    private String userId;

}
