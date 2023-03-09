package ru.novoselov.authservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.novoselov.authservice.config.DefaultMapperConfig;
import ru.novoselov.authservice.data.entity.Role;
import ru.novoselov.authservice.data.entity.User;
import ru.novoselov.authservice.model.request.SignupRequest;
import ru.novoselov.authservice.model.response.SignupResponse;

import java.util.Collection;

@Mapper(componentModel = "spring", config = DefaultMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "username", source = "request.username")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    User toUser(SignupRequest request, Collection<Role> roles);

    SignupResponse toResponse(User user);
}
