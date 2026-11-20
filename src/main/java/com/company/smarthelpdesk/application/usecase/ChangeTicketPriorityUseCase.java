package com.company.smarthelpdesk.application.usecase;

import com.company.smarthelpdesk.domain.model.ticket.TicketPriority;

import java.util.UUID;

public interface ChangeTicketPriorityUseCase {

    void execute(UUID ticketId, TicketPriority newPriority);

}
