package com.rupesh.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth = new Date();
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long createdBy;
    private Long modifiedBy;
    private boolean enabled;
    private AddressDTO address;

}
