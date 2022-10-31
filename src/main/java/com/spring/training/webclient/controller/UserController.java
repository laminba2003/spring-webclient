package com.spring.training.webclient.controller;

import com.spring.training.webclient.model.User;
import com.spring.training.webclient.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    final UserService service;

    @GetMapping("{id}")
    public Mono<User> getUser(@PathVariable("id") String id) {
        return service.getUser(id);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PutMapping("{id}")
    public Mono<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return service.updateUser(id,user);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable("id") String id) {
        return service.deleteUser(id);
    }

}
