package com.rupesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUsernameRequest {

    private String oldUsername;
    private String newUsername;

}
