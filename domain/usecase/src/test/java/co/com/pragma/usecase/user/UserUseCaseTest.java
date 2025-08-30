package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void registerUser_shouldReturnUser() {
        // Arrange
       User user = new User("Oscar", "Juan", LocalDate.of(1997,7,5),"Pasto","3234657453","omelo@gmail.com",new BigDecimal(13000));

        when(userRepository.RegisterUser(any(User.class)))
                .thenReturn(Mono.just(user));

        // Act & Assert
        StepVerifier.create(userUseCase.registerUser(user))
                .expectNext(user)
                .verifyComplete();
    }
}
