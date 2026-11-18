package com.company.smarthelpdesk.infrastructure.entrypoints;
import com.company.smarthelpdesk.application.exception.ResourceNotFoundException;
import com.company.smarthelpdesk.application.usecase.CreateTicketUseCase;
import com.company.smarthelpdesk.application.usecase.GetTicketByIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.company.smarthelpdesk.domain.model.ticket.Ticket;
import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;
import com.company.smarthelpdesk.domain.valueobject.TicketId;
import com.company.smarthelpdesk.domain.valueobject.UserId;
import com.company.smarthelpdesk.domain.valueobject.CategoryId;
import com.company.smarthelpdesk.application.command.ticket.CreateTicketResult;
import com.company.smarthelpdesk.domain.model.ticket.TicketStatus;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
class TicketControllerTest {
    @Test
    void shouldReturn404WhenTicketDoesNotExist() throws Exception {
        CreateTicketUseCase createTicketUseCase =
                mock(CreateTicketUseCase.class);
        GetTicketByIdUseCase getTicketByIdUseCase =
                mock(GetTicketByIdUseCase.class);
        when(getTicketByIdUseCase.execute(any(UUID.class)))
                .thenThrow(new ResourceNotFoundException(
                        "Ticket not found"
                ));
        TicketController controller = new TicketController(
                createTicketUseCase,
                getTicketByIdUseCase
        );
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        mockMvc.perform(
                        get("/api/tickets/" + UUID.randomUUID())
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Ticket not found"));
    }

    @Test

    void shouldReturn200WhenTicketExists() throws Exception {

        CreateTicketUseCase createTicketUseCase =

                mock(CreateTicketUseCase.class);

        GetTicketByIdUseCase getTicketByIdUseCase =

                mock(GetTicketByIdUseCase.class);

        UUID ticketId = UUID.randomUUID();

        Ticket ticket = Ticket.open(

                new TicketId(ticketId),

                new UserId(UUID.randomUUID()),

                new CategoryId(UUID.randomUUID()),

                "Computador no enciende",

                "El computador no responde",

                TicketPriority.HIGH

        );

        when(getTicketByIdUseCase.execute(ticketId))

                .thenReturn(ticket);

        TicketController controller = new TicketController(

                createTicketUseCase,

                getTicketByIdUseCase

        );

        MockMvc mockMvc = MockMvcBuilders

                .standaloneSetup(controller)

                .setControllerAdvice(new GlobalExceptionHandler())

                .build();

        mockMvc.perform(

                        get("/api/tickets/" + ticketId)

                )

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(ticketId.toString()))

                .andExpect(jsonPath("$.subject").value("Computador no enciende"))

                .andExpect(jsonPath("$.priority").value("HIGH"))

                .andExpect(jsonPath("$.status").value("OPEN"));

    }

    @Test
    void shouldReturn201WhenTicketIsCreated() throws Exception {
        CreateTicketUseCase createTicketUseCase =
                mock(CreateTicketUseCase.class);
        GetTicketByIdUseCase getTicketByIdUseCase =
                mock(GetTicketByIdUseCase.class);
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();
        CreateTicketResult result = new CreateTicketResult(
                ticketId,
                "Computador no enciende",
                TicketStatus.OPEN
        );
        when(createTicketUseCase.execute(any()))
                .thenReturn(result);
        TicketController controller = new TicketController(
                createTicketUseCase,
                getTicketByIdUseCase
        );
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        String json = """
           {
             "userId": "%s",
             "categoryId": "%s",
             "subject": "Computador no enciende",
             "description": "El computador no responde",
             "priority": "HIGH"
           }
           """.formatted(userId, categoryId);
        mockMvc.perform(
                        post("/api/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ticketId.toString()))
                .andExpect(jsonPath("$.subject")
                        .value("Computador no enciende"))
                .andExpect(jsonPath("$.status").value("OPEN"));
        verify(createTicketUseCase, times(1)).execute(any());
    }
}
