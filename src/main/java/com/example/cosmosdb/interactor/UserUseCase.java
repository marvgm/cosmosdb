package com.example.cosmosdb.interactor;

import com.example.cosmosdb.datasource.cosmos.CosmosDataSource;
import com.example.cosmosdb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserUseCase {

    @Autowired
    private CosmosDataSource cosmosDataSource;

    public void deleteAll(){
        cosmosDataSource.deleteAll();
    }

    public Mono<User> saveUser(){
        return cosmosDataSource.saveUser();
    }

    public Mono<User> findUserById(){
        return cosmosDataSource.findUserById();
    }

    public User saveUserBlock(){
        return cosmosDataSource.saveUserMonoBlock();
    }

    public User findUserByIdBlock(){
        return cosmosDataSource.findUserByidBlock();
    }


}
