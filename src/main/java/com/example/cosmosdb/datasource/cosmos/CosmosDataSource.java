package com.example.cosmosdb.datasource.cosmos;

import com.example.cosmosdb.Repository.UserRepository;
import com.example.cosmosdb.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class CosmosDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosDataSource.class);

    @Autowired
    private UserRepository repository;

    public void deleteAll() {
        this.repository.deleteAll().block();
        LOGGER.info("Deleted all data in container.");
    }

    public Mono<User> saveUser(){
        // Save the User class to Azure Cosmos DB database.
       return repository.save(getUser());
    }

    public Flux<User> findUserByName(){
        return repository.findByFirstName("testFirstName");
    }

    public Mono<User> findUserById() {
        //  Nothing happens until we subscribe to these Monos.
        //  findById will not return the user as user is not present.
        final Mono<User> findByIdMono = repository.findById(getUser().getId());
        final User findByIdUser = findByIdMono.block();
        Assert.isNull(findByIdUser, "User must be null");
        return null;
    }

    public User saveUserMonoBlock() {
        final User savedUser = saveUser().block();
        Assert.state(savedUser != null, "Saved user must not be null");
        Assert.state(savedUser.getFirstName().equals(getUser().getFirstName()), "Saved user first name doesn't match");
        return savedUser;
    }

    public List<User> findListUserByNameBlock(){
        return findUserByName().collectList().block();
    }

    public User findUserByidBlock(){
      final Optional<User> optionalUserResult = repository.findById(getUser().getId()).blockOptional();
      Assert.isTrue(optionalUserResult.isPresent(), "Cannot find user.");

      final User result = optionalUserResult.get();
      Assert.state(result.getFirstName().equals(getUser().getFirstName()), "query result firstName doesn't match!");
      Assert.state(result.getLastName().equals(getUser().getLastName()), "query result lastName doesn't match!");

      LOGGER.info("findOne in User collection get result: {}", result);
      LOGGER.info("spring-cloud-azure-data-cosmos-sample successfully run.");

      return result;
    }

    private User getUser() {
        return new User("testId", "testFirstName", "testLastName", "test address line one");
    }
}
