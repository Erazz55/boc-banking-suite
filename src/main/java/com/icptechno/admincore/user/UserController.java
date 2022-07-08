package com.icptechno.admincore.user;

import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import com.icptechno.admincore.common.security.AppUserDetail;
import com.icptechno.admincore.user.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    final UserConverter userConverter;
    final UserService userService;

    public UserController(UserConverter userConverter, UserService userService) {
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseHolder<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtos = userService.getAll().stream().map(userConverter::convertToDto).collect(Collectors.toList());
        return ResponseBuilder.okResponseBuilder(userDtos).build();
    }

    @GetMapping("/logged")
    public ResponseEntity<ResponseHolder<LoggedUserDto>> getLoggedUser() {
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = userService.getOneByIdWithPermissions(userDetail.getId());
        LoggedUserDto userDto = userConverter.convertToLoggedDto(user);
        return ResponseBuilder.okResponseBuilder(userDto).build();
    }

    @PutMapping("/profile/avatar")
    public ResponseEntity<ResponseHolder<Boolean>> updateAvatar(@RequestBody ProfileAvatarDto profileAvatarDto) {
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = userService.getOneByIdWithPermissions(userDetail.getId());

        user.setAvatarLink(profileAvatarDto.getAvatarLink());

        userService.updateById(user.getId(), user);
        return ResponseBuilder.okResponseBuilder(true).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<ResponseHolder<Boolean>> updateUserProfile(@RequestBody ProfileEditDto profileEditDto) {
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = userService.getOneByIdWithPermissions(userDetail.getId());

        user.setFirstName(profileEditDto.getFirstName());
        user.setLastName(profileEditDto.getLastName());
        user.setMobileNumber(profileEditDto.getMobileNumber());

        userService.updateById(user.getId(), user);
        return ResponseBuilder.okResponseBuilder(true).build();
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<ResponseHolder<UserDto>> getUserById(@PathVariable Long id) {
        ApplicationUser user = userService.getOneById(id);
        UserDto userDto = userConverter.convertToDto(user);
        return ResponseBuilder.okResponseBuilder(userDto).build();
    }

    @PostMapping("")
    public ResponseEntity<ResponseHolder<Long>> createUser(@RequestBody UserCreateDto userCreateDto) {

        ApplicationUser user = userConverter.convertToEntity(userCreateDto);

        // create user
        user = userService.create(user);

        // save user
        return ResponseBuilder.createdResponseBuilder(user.getId()).build();
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<ResponseHolder<Object>> updateUserById(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        ApplicationUser user = userConverter.convertToEntity(userUpdateDto);
        userService.updateById(id, user);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<ResponseHolder<Object>> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

}
