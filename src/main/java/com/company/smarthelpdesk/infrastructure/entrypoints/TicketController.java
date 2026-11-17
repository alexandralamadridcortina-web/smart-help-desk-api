package com.company.smarthelpdesk.infrastructure.entrypoints;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketCommand;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketResult;
import com.company.smarthelpdesk.application.usecase.CreateTicketUseCase;
import com.company.smarthelpdesk.application.usecase.GetTicketByIdUseCase;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByIdUseCase getTicketByIdUseCase;
    public TicketController(
            CreateTicketUseCase createTicketUseCase,
            GetTicketByIdUseCase getTicketByIdUseCase
    ) {
        this.createTicketUseCase = createTicketUseCase;
        this.getTicketByIdUseCase = getTicketByIdUseCase;
    }
    @PostMapping
    public ResponseEntity<CreateTicketResult> createTicket(
            @RequestBody CreateTicketCommand command
    ) {
        CreateTicketResult result =
                createTicketUseCase.execute(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable UUID id
    ) {
        Ticket ticket = getTicketByIdUseCase.execute(id);
        TicketResponse response = TicketResponse.from(ticket);
        return ResponseEntity.ok(response);
    }
}