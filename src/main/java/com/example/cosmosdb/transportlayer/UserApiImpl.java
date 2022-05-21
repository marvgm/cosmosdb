package com.example.cosmosdb.transportlayer;

import com.example.cosmosdb.entities.User;
import com.example.cosmosdb.interactor.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/v1")
@RestController
public class UserApiImpl {
    @Autowired
    UserUseCase userUseCase;

    @DeleteMapping("/deleteAll")
    public void deleteAllUsers() {
        userUseCase.deleteAll();
    }

    @PostMapping("/saveUserMono")
    public Mono<User> saveUser() {
       return userUseCase.saveUser();
    }

    @GetMapping("/findUserByIdMono")
    public Mono<User> findUserByIdMono() {
        return userUseCase.findUserById();
    }

    @PostMapping("/saveUserBlock")
    public User saveUserBlock() {
        return userUseCase.saveUserBlock();
    }

    @GetMapping("/findUserByIdBlock")
    public User findUserByIdBlock() {
        return userUseCase.findUserByIdBlock();
    }

}
