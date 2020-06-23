package com.social.network.userqueryservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.userqueryservice.dto.UserDto;
import com.social.network.userqueryservice.query.UserMapper;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserDto, UserMapper> {

    public UserController(final UserMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('ui')")
    public UserDto getUser(@PathVariable("id") final String id, @RequestParam final Map<String, Object> requestParams) {
        final UserDto entity = getEntity(id, requestParams);
        if (entity == null) {
        }
        return entity;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public List<UserDto> getUsers(@RequestParam final Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }

}
