package co.com.pragma.api;
import co.com.pragma.api.mapper.userDtoMapper;
import co.com.pragma.api.userDto.userOutDto;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.ResponseEntity.*;

/**
 * API Rest controller.
 * 
 * Example of how to declare and use a use case:
 * <pre>
 * private final MyUseCase useCase;
 * 
 * public String commandName() {
 *     return useCase.execute();
 * }
 * </pre>
 */
@RestController
@RequestMapping(value = "pragma/hu1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final UserUseCase userUseCase;
    private final userDtoMapper userDtoMapper;

    @GetMapping(path = "/userUsecase")
    public Flux<userOutDto> listUsers() {
        return userUseCase.listUser()        // Flux<User>
                .map(userDtoMapper::toDto); // Flux<userOutDto>
    }
}
