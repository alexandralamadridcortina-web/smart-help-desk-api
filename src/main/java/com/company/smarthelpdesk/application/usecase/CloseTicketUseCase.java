package com.company.smarthelpdesk.application.usecase;
import java.util.UUID;
public interface CloseTicketUseCase {
    void execute(UUID ticketId);
}