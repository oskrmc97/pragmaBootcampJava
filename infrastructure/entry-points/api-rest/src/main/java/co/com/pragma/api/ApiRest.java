package co.com.pragma.api;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

}
