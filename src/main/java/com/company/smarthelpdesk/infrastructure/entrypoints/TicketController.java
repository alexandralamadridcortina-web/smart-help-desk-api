package com.company.smarthelpdesk.infrastructure.entrypoints;

import com.company.smarthelpdesk.application.command.ticket.CreateTicketCommand;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketResult;
import com.company.smarthelpdesk.application.usecase.CloseTicketUseCase;
import com.company.smarthelpdesk.application.usecase.CreateTicketUseCase;
import com.company.smarthelpdesk.application.usecase.GetTicketByIdUseCase;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import com.company.smarthelpdesk.application.usecase.ChangeTicketPriorityUseCase;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByIdUseCase getTicketByIdUseCase;
    private final CloseTicketUseCase closeTicketUseCase;
    private final ChangeTicketPriorityUseCase changeTicketPriorityUseCase;


    public TicketController(
            CreateTicketUseCase createTicketUseCase,
            GetTicketByIdUseCase getTicketByIdUseCase,
            CloseTicketUseCase closeTicketUseCase,
            ChangeTicketPriorityUseCase changeTicketPriorityUseCase
    ) {
        this.createTicketUseCase = createTicketUseCase;
        this.getTicketByIdUseCase = getTicketByIdUseCase;
        this.closeTicketUseCase = closeTicketUseCase;
        this.changeTicketPriorityUseCase = changeTicketPriorityUseCase;
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

    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> closeTicket(
            @PathVariable UUID id
    ) {
        closeTicketUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/priority")
    public ResponseEntity<Void> changePriority(
            @PathVariable UUID id,
            @RequestBody ChangePriorityRequest request
    ) {
        changeTicketPriorityUseCase.execute(id, request.priority());
        return ResponseEntity.noContent().build();
    }
    public record ChangePriorityRequest(TicketPriority priority) {
    }
}
