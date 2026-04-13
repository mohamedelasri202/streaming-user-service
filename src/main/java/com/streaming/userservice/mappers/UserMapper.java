package com.streaming.userservice.mappers;

import com.streaming.userservice.dto.UserDTO;
import com.streaming.userservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDTO toDTO(User user);


    User fromDTO(UserDTO userDTO);
}