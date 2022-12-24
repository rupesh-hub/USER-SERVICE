package com.rupesh.userservice.service;

import com.rupesh.userservice.dto.ChangePasswordRequest;
import com.rupesh.userservice.dto.ChangeUsernameRequest;
import com.rupesh.userservice.dto.UserDTO;
import com.rupesh.userservice.util.global.GlobalResponse;

public interface IUserService {

    GlobalResponse<UserDTO> saveUser(final UserDTO userDTO);

    GlobalResponse<UserDTO> updateUser(final UserDTO userDTO, final String userId);

    GlobalResponse<UserDTO> getUserByUsername(final String username);

    GlobalResponse<UserDTO> getUserByUserId(final String userId);

    GlobalResponse<?> getAllUser(final int page, final int limit, final String sortDir, final String sortBy);

    GlobalResponse<Boolean> deleteUser(final String userId);

    GlobalResponse<Boolean> changePassword(final ChangePasswordRequest changePasswordRequest);

    GlobalResponse<Boolean> changeUsername(final ChangeUsernameRequest changeUsernameRequest);

}
