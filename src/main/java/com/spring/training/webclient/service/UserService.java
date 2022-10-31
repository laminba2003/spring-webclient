package com.spring.training.webclient.service;

import com.spring.training.webclient.exception.EntityNotFoundException;
import com.spring.training.webclient.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    final WebClient client;

    public Flux<User> getUsers() {
        return client.get().uri("/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

    public Mono<User> getUser(String id) {
        return client.get().uri("/users/{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new EntityNotFoundException(id)))
                .bodyToMono(User.class);
    }

    public Mono<User> createUser(User user) {
        return client.post().uri("/users")
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> updateUser(String id, User user) {
        return client.put().uri("/users/{id}", id)
                .body(Mono.just(user), User.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new EntityNotFoundException(id)))
                .bodyToMono(User.class);
    }

    public Mono<Void> deleteUser(String id) {
        return client.delete().uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

}