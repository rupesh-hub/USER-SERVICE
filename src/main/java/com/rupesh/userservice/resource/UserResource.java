package com.rupesh.userservice.resource;

import com.rupesh.userservice.dto.ChangePasswordRequest;
import com.rupesh.userservice.dto.ChangeUsernameRequest;
import com.rupesh.userservice.dto.UserDTO;
import com.rupesh.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/user"})
public class UserResource {

    private final IUserService userService;


    @Autowired
    public UserResource(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody final UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") final int page,
            @RequestParam(name = "limit", defaultValue = "10") final int limit,
            @RequestParam(name = "sortDir", defaultValue = "ASC") final String sortDir,
            @RequestParam(name = "sortBy") final String sortBy
    ) {
        return ResponseEntity.ok(userService.getAllUser(page, limit, sortDir, sortBy));
    }

    @GetMapping(path = "/by-user-id")
    public ResponseEntity<?> getByUserId(@RequestParam(name = "userId") final String userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @GetMapping(path = "/by-username")
    public ResponseEntity<?> getByUsername(@RequestParam(name = "username") final String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping(path = "/")
    public ResponseEntity<?> updateUser(
            @RequestBody final UserDTO userDTO,
            @RequestParam(name = "username") final String username
    ) {
        return ResponseEntity.ok(userService.updateUser(userDTO, username));
    }

    @PutMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePasswordRequest passwordChangeReq) {
        return ResponseEntity.ok(userService.changePassword(passwordChangeReq));
    }

    @PutMapping(path = "/change-username")
    public ResponseEntity<?> changeUsername(@RequestBody final ChangeUsernameRequest changeUsernameReq) {
        return ResponseEntity.ok(userService.changeUsername(changeUsernameReq));
    }


    @DeleteMapping(path = "/")
    public ResponseEntity<?> deleteUser(@RequestParam(name = "userId") final String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }


}
