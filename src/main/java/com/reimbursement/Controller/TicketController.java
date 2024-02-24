package com.reimbursement.Controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.reimbursement.Model.Ticket;
import com.reimbursement.DAO.TicketDao;


public class TicketController {
    public static int submit(Ticket ticket) {
        String type = ticket.getType();
        if (!type.equals("travel") && !type.equals("lodging") && !type.equals("food")) {
            ticket.setType("other");
        }
        int added = TicketDao.addTicket(ticket);
        if (added == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    public static List<Ticket> getList() {
        List<Ticket> tickets = TicketDao.getPendingTickets();
        return tickets;
    }

    public static int processTicket(byte[] jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        int ticket_id = 0;
        String status = "";
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode idNode = rootNode.path("ticket_id");
            ticket_id = idNode.asInt();
            JsonNode statusNode = rootNode.path("status");
            status = statusNode.asText();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        String ticketStatus = TicketDao.getStatus(ticket_id);
        System.out.println(ticketStatus);
        if (ticketStatus.equals("pending")) {
            int updated = TicketDao.updateStatus(ticket_id, status);
            if (updated == 1) {
                return 1;
            } else {
                return 0;
            }
        } else if (ticketStatus.equals("")) {
            return 2;    
        } else {
            return 3;
        }
    }

    public static List<Ticket> getPreviousTickets(int user_id) {
        List<Ticket> tickets = TicketDao.getAllTicketsById(user_id);
        return tickets;
    }
}
