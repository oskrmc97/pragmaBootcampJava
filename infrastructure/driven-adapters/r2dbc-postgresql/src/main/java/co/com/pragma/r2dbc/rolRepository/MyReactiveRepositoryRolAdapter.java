package co.com.pragma.r2dbc.rolRepository;

import co.com.pragma.model.user.RolUser;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.RolRepository;
import co.com.pragma.r2dbc.entity.RolEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveRepositoryRolAdapter extends ReactiveAdapterOperations<RolUser, RolEntity, Integer, MyRolReactiveRepository> implements RolRepository {

    public MyReactiveRepositoryRolAdapter(MyRolReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, RolUser.class));
    }

    @Override
    public Mono<RolUser> findRolByName(String name) {
        return null;
    }

    @Override
    public Mono<RolUser> findRolById(Integer id) {
        return repository.findById(id) .map(entity -> mapper.map(entity, RolUser.class));
    }
}
