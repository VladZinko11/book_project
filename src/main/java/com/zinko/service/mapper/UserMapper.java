package com.zinko.service.mapper;

import com.zinko.data.model.User;
import com.zinko.service.dto.UserAuthDto;
import com.zinko.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserAuthDto userAuthDto);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}