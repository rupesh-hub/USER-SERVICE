package com.rupesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
