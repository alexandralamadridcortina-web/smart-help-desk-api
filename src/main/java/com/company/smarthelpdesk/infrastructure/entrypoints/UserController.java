package com.company.smarthelpdesk.infrastructure.entrypoints;
import com.company.smarthelpdesk.application.command.user.CreateUserCommand;
import com.company.smarthelpdesk.application.command.user.CreateUserResult;
import com.company.smarthelpdesk.application.usecase.CreateUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }
    @PostMapping
    public ResponseEntity<CreateUserResult> createUser(
            @RequestBody CreateUserCommand command
    ) {
        CreateUserResult result = createUserUseCase.execute(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
}