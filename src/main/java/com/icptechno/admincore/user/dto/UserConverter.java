package com.icptechno.admincore.user.dto;

import com.icptechno.admincore.user.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ApplicationUser convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, ApplicationUser.class);
    }

    public ApplicationUser convertToEntity(UserShallowDto userDto) {
        return modelMapper.map(userDto, ApplicationUser.class);
    }

    public ApplicationUser convertToEntity(UserCreateDto userDto) {
        return modelMapper.map(userDto, ApplicationUser.class);
    }

    public ApplicationUser convertToEntity(UserUpdateDto userDto) {
        return modelMapper.map(userDto, ApplicationUser.class);
    }

    public UserDto convertToDto(ApplicationUser user) {
        return modelMapper.map(user, UserDto.class);
    }

    public LoggedUserDto convertToLoggedDto(ApplicationUser user) {
        return modelMapper.map(user, LoggedUserDto.class);
    }

}
