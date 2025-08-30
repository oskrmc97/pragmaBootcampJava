package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.userEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<User, userEntity, BigInteger, MyReactiveRepository> implements UserRepository {
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
    }

    @Override
    public Flux<User> listUser() {
        return repository.findAll()
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    @Transactional
    public Mono<User> RegisterUser(User user) {
        userEntity userEntity = mapper.map(user, userEntity.class);
        return repository.save(userEntity).map(entity -> mapper.map(entity, User.class));
    }

}
