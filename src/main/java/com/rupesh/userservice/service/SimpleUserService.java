package com.rupesh.userservice.service;

import com.rupesh.userservice.dto.*;
import com.rupesh.userservice.entity.User;
import com.rupesh.userservice.repository.UserRepository;
import com.rupesh.userservice.rest.AddressClient;
import com.rupesh.userservice.util.convertor.UserConvertor;
import com.rupesh.userservice.util.global.GlobalResponse;
import com.rupesh.userservice.util.global.GlobalUtil;
import com.rupesh.userservice.util.helper.UserHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rupesh.userservice.util.helper.UserConstants.*;

@Service
public class SimpleUserService implements IUserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final AddressClient addressClient;

    @Value("${address-service.base-url}")
    private String addressBaseUrl;

    public SimpleUserService(UserRepository userRepository,
                             RestTemplate restTemplate,
                             AddressClient addressClient) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.addressClient = addressClient;
    }

    @Override
    @Transactional
    public GlobalResponse<UserDTO> saveUser(final UserDTO userDTO) {
        final AddressDTO addressDTO = userDTO.getAddress();
        final LocalDateTime createdDateTime = LocalDateTime.now();
        final Long createdBy = 1L;
        userDTO.setUserId(UserHelper.generateUserId());
        userDTO.setPassword(UserHelper.generatePassword());
        userDTO.setCreatedDate(createdDateTime);
        userDTO.setCreatedBy(createdBy);

        final User savedUser = userRepository.save(UserConvertor.toEntity(userDTO));

        addressDTO.setCreatedDate(createdDateTime);
        addressDTO.setCreatedBy(createdBy);
        addressDTO.setUserId(savedUser.getUserId());
//        this.saveAddress(addressDTO);

        return GlobalUtil
                .globalResponse(
                        String.format(USER_SAVED_SUCCESS, userDTO.getEmail()),
                        HttpStatus.CREATED,
                        null,
                        Optional.ofNullable(savedUser)
                                .map(UserConvertor::toDto)
                                .orElse(null)
                );
    }


    @Override
    @Transactional
    public GlobalResponse<UserDTO> updateUser(final UserDTO userDTO, final String userId) {
        final LocalDateTime updatedDate = LocalDateTime.now();
        final Long updatedBy = 1L;

        return GlobalUtil
                .globalResponse(
                        String.format(USER_UPDATED_SUCCESS, userDTO.getEmail()),
                        HttpStatus.CREATED,
                        null,
                        Optional.ofNullable(userRepository.save(UserConvertor.toEntity(userDTO)))
                                .map(UserConvertor::toDto)
                                .orElse(null)
                );
    }

    @Override
    public GlobalResponse<UserDTO> getUserByUsername(final String username) {

        final UserDTO userDTO = userRepository.findByUsername(username)
                .map(UserConvertor::toDto)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));

//        userDTO.setAddress(addressClient.getAddressByUserId(userDTO.getUserId()));

        return GlobalUtil
                .globalResponse(
                        String.format(USER_UPDATED_SUCCESS, username),
                        HttpStatus.OK,
                        null,
                        userDTO
                );
    }

    @Override
    public GlobalResponse<UserDTO> getUserByUserId(final String userId) {

        final UserDTO userDTO = userRepository.findByUserId(userId)
                .map(UserConvertor::toDto)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND_BY_USER_ID, userId)));
//        userDTO.setAddress(getAddress(userId));

        return
                GlobalUtil
                        .globalResponse(
                                String.format(USER_FOUND_BY_USER_ID, userId),
                                HttpStatus.OK,
                                null,
                                userDTO
                        );


    }

    @Override
    public GlobalResponse<?> getAllUser(final int page, final int limit, final String sortDir, final String sortBy) {
        var sort = sortDir.equalsIgnoreCase(SortDirection.ASC.toString())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        final Page<User> userPage = userRepository.findAll(PageRequest.of(page, limit, sort));

        final List<UserResponse> userResponseList = new ArrayList();

        userPage.getContent().forEach(user -> {
            UserDTO userDTO = UserConvertor.toDto(user);

            final AddressDTO addressDTO = this.getAddressFeign(userDTO.getUserId());
            userDTO.setAddress(addressDTO);

            userResponseList.add(
                    UserResponse
                            .builder()
                            .user(userDTO)
                            .build()
            );
        });


        return GlobalUtil
                .globalResponse(
                        String.format(ALL_USER_FOUND),
                        HttpStatus.OK,
                        Pageable
                                .builder()
                                .page(userPage.getNumber())
                                .totalElements(userPage.getTotalElements())
                                .totalPages(userPage.getTotalPages())
                                .size(userPage.getSize())
                                .hasNext(userPage.hasNext())
                                .hasPrevious(userPage.hasPrevious())
                                .isFirst(userPage.isFirst())
                                .isLast(userPage.isLast())
                                .totalItems(userResponseList.size())
                                .isSorted(userPage.getSort().isSorted())
                                .build(),
                        userResponseList);
    }

    @Override
    public GlobalResponse<Boolean> deleteUser(final String userId) {
        final User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND_BY_USER_ID, userId)));

        userRepository.delete(user);

        return GlobalUtil
                .globalResponse(
                        String.format(USER_FOUND_BY_USER_ID, userId),
                        HttpStatus.CREATED,
                        null,
                        null);
    }

    @Override
    public GlobalResponse<Boolean> changePassword(final ChangePasswordRequest pwdChangeReq) {

        final String username = pwdChangeReq.getUsername();
        final String oldPassword = pwdChangeReq.getOldPassword();
        final String newPassword = pwdChangeReq.getNewPassword();
        final String confirmNewPassword = pwdChangeReq.getConfirmNewPassword();

        //0.validate newPassword and confirmPassword
        if (!newPassword.equals(confirmNewPassword))
            throw new RuntimeException("new password and confirm new password doesn't match.");

        //1.get user using username
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));

        //2.match dbPassword and oldPassword

        //3.update password
        user.setPassword(UserHelper.encodePassword(confirmNewPassword));
        userRepository.save(user);
        return GlobalUtil
                .globalResponse(
                        String.format(USER_UPDATED_SUCCESS, username),
                        HttpStatus.OK,
                        null,
                        true
                );
    }

    @Override
    public GlobalResponse<Boolean> changeUsername(final ChangeUsernameRequest changeUsernameReq) {
        final String username = changeUsernameReq.getOldUsername();
        final String newUsername = changeUsernameReq.getNewUsername();

        //1.get user using username
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format(USER_NOT_FOUND_BY_USERNAME, username)));

        //2.update username
        user.setEmail(newUsername);
        userRepository.save(user);
        return GlobalUtil
                .globalResponse(
                        String.format(USER_UPDATED_SUCCESS, username),
                        HttpStatus.OK,
                        null,
                        true
                );
    }

    /*CALLING REST API USING WEB-CLIENT*/
    private AddressDTO getAddress(final String userId) {
        return this.restTemplate.getForObject(addressBaseUrl+"/user/{userId}", AddressDTO.class, userId);
    }

    private AddressDTO getAddressFeign(final String userId){
        return this.addressClient.getAddressByUserId(userId);
    }


}
