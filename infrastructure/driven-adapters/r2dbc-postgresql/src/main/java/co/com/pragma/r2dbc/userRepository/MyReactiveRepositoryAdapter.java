package co.com.pragma.r2dbc.userRepository;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.LogInDTO;
import co.com.pragma.model.user.dto.SignUpDTO;
import co.com.pragma.model.user.dto.TokenDTO;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.userEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.rolRepository.MyRolReactiveRepository;
import co.com.pragma.r2dbc.security.jwt.provider.JwtProvider;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public class MyReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<User, userEntity, BigInteger, MyUserReactiveRepository>
        implements UserRepository {

    private final MyUserReactiveRepository repository;
    private final MyRolReactiveRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;  // nuevo

    public MyReactiveRepositoryAdapter(MyUserReactiveRepository repository,
                                       MyRolReactiveRepository rolRepository,
                                       ObjectMapper mapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Flux<User> listUser() {
        return repository.findAll()
                .flatMap(entity ->
                        rolRepository.findById(entity.getRolUser())
                                .map(rolEntity -> UserMapper.toDomain(entity, rolEntity))
                );
    }

    @Override
    @Transactional
    public Mono<User> RegisterUser(User user) {
        userEntity entity = mapper.map(user, userEntity.class);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity)
                .flatMap(savedEntity ->
                        rolRepository.findById(savedEntity.getRolUser())
                                .map(rolEntity -> UserMapper.toDomain(savedEntity, rolEntity))
                );
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return repository.findByEmail(email)
                .flatMap(entity ->
                        rolRepository.findById(entity.getRolUser())
                                .map(rolEntity -> UserMapper.toDomain(entity, rolEntity))
                );
    }

    @Override
    public Mono<User> signUp(SignUpDTO signUpDTO) {
        return null;
    }

    @Override
    public Mono<TokenDTO> login(LogInDTO logInDTO) {
        return repository.findByEmail(logInDTO.email())
                .filter(userEntity -> passwordEncoder.matches(logInDTO.password(), userEntity.getPassword()))
                .map(userEntity -> new TokenDTO(jwtProvider.generateToken(userEntity)))
                .switchIfEmpty(Mono.error(new Throwable("bad credentials")));
    }


}
