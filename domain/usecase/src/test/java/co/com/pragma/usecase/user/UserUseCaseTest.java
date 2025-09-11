package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exception.EmailAlreadyInUseException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
       User user = new User("Oscar", "Juan", LocalDate.of(1997,7,5),"Pasto","3234657453","omelo@gmail.com","1085335644",new BigDecimal(13000),"ADMIN","123456");

        when(userRepository.RegisterUser(any(User.class)))
                .thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.registerUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void registerUserWhitError_shouldReturnEmailError() {
        User user = new User("Oscar", "Juan", LocalDate.of(1997,7,5),
                "Pasto","3234657453","omelo@gmail.com","1085335644",
                new BigDecimal(13000),"ADMIN","123456");

        Throwable cause = new Throwable("duplicate key value violates unique constraint \"users_email_key\"");
        RuntimeException dbException = new RuntimeException(
                "duplicate key value violates unique constraint \"users_email_key\"",cause
        );

        when(userRepository.RegisterUser(any(User.class)))
                .thenReturn(Mono.error(dbException));

        StepVerifier.create(userUseCase.registerUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error)
                            .isInstanceOf(EmailAlreadyInUseException.class);
                    assertThat(error.getMessage())
                            .contains("the email:" + user.getEmail() + " is duplicate");
                })
                .verify();
    }

    @Test
    void registerUserGeneralError_shouldReturnError() {
        User user = new User("Oscar", "Juan", LocalDate.of(1997,7,5),
                "Pasto","3234657453","omelo@gmail.com","1085335644",
                new BigDecimal(13000),"ADMIN","123456");

        Throwable cause = new Throwable("general error");
        RuntimeException other_error = new RuntimeException(
                "other error",cause
        );

        when(userRepository.RegisterUser(any(User.class)))
                .thenReturn(Mono.error(other_error));

        StepVerifier.create(userUseCase.registerUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error)
                            .isInstanceOf(RuntimeException.class);
                    assertThat(error.getMessage())
                            .contains("Error creating user");
                })
                .verify();
    }

    @Test
    void registerUserFieldsError_shouldReturnValidationError() {
        User user = new User(null, "Juan", LocalDate.of(1997,7,5),
                "Pasto","3234657453","omelo@gmail.com","1085335644",
                new BigDecimal(13000),"ADMIN","123456");

        StepVerifier.create(userUseCase.registerUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error)
                            .isInstanceOf(RuntimeException.class);
                    assertThat(error.getMessage())
                            .contains("Error the field is empty");
                })
                .verify();
    }

    @Test
    void registerUserSalaryError_shouldReturnValidationError() {
        User user = new User("Roberto", "Juan", LocalDate.of(1997,7,5),
                "Pasto","3234657453","omelo@gmail.com","1085335644",
                new BigDecimal(15000001),"ADMIN","123456");

        StepVerifier.create(userUseCase.registerUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error)
                            .isInstanceOf(RuntimeException.class);
                    assertThat(error.getMessage())
                            .contains("The salary is not in the range");
                })
                .verify();
    }

    @Test
    void getUserWithEmail_shouldReturnUser() {
        User user = new User("Roberto", "Juan", LocalDate.of(1997,7,5),
                "Pasto","3234657453","omelo@gmail.com","1085335644",
                new BigDecimal(15000001),"ADMIN","123456");

        String email = "omelo@gmail.com";

        when(userRepository.findUserByEmail(email)).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.findUserByEmail(email))
                .expectNext(user)
                .verifyComplete();
    }
}

