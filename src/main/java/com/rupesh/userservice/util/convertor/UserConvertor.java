package com.rupesh.userservice.util.convertor;

import com.rupesh.userservice.dto.UserDTO;
import com.rupesh.userservice.entity.User;

public class UserConvertor {

    public static User toEntity(final UserDTO userDTO) {

        final User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setCreatedDate(userDTO.getCreatedDate());
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setModifiedBy(userDTO.getModifiedBy());
        user.setModifiedDate(userDTO.getModifiedDate());
        user.setEnabled(user.isEnabled());

        return user;
    }

    public static UserDTO toDto(final User user) {
        final UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPhone(user.getPhone());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedDate(user.getCreatedDate());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setModifiedBy(user.getModifiedBy());
        userDTO.setModifiedDate(user.getModifiedDate());
        userDTO.setEnabled(user.isEnabled());

        return userDTO;
    }

}
