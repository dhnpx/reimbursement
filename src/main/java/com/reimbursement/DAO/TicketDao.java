package com.reimbursement.DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import db.Database;
import com.reimbursement.Model.Ticket;

public class TicketDao {
    public static int addTicket(Ticket ticket) {
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO ticketapi.tickets(user_id, amount, type, description) VALUES(?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, ticket.getUserId());
            stmt.setInt(2, (int) (Math.floor(ticket.getAmount() * 100)));
            stmt.setString(3, ticket.getType());
            stmt.setString(4, ticket.getDesc());
            try {
                stmt.executeUpdate();
                ResultSet keys = stmt.getGeneratedKeys();
                keys.next();
                ticket.setId(keys.getInt("ticket_id"));
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Ticket> getPendingTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM ticketapi.tickets WHERE status='pending'")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticket_id"));
                ticket.setUserId(rs.getInt("user_id"));
                ticket.setAmount((float) rs.getInt("amount") / 100);
                ticket.setType(rs.getString("type"));
                ticket.setDesc(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
            return tickets;
        } catch (Exception e) {
            e.printStackTrace();
            return tickets;
        }
    }

    public static String getStatus(int ticket_id) {
        String status = "";
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "SELECT status FROM ticketapi.tickets WHERE ticket_id = ?")) {
                stmt.setInt(1, ticket_id);
                ResultSet rs =stmt.executeQuery();
                rs.next();
                status = rs.getString("status");
                return status;

            } catch (SQLException e) {
                e.printStackTrace();
                return status;
            }
    }

    public static int updateStatus(int ticket_id, String status) { 
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "UPDATE ticketapi.tickets SET status = ? WHERE ticket_id = ?")) {
            stmt.setString(1, status);
            stmt.setInt(2, ticket_id);
            try {
                int rows = stmt.executeUpdate();
                if (rows == 1) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }

    public static List<Ticket> getAllTicketsById(int user_id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM ticketapi.tickets WHERE user_id = ?")) {
                stmt.setInt(1, user_id);
                try {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Ticket ticket = new Ticket();
                        ticket.setId(rs.getInt("ticket_id"));
                        ticket.setUserId(rs.getInt("user_id"));
                        ticket.setAmount((float) rs.getInt("amount") / 100);
                        ticket.setType(rs.getString("type"));
                        ticket.setDesc(rs.getString("description"));
                        ticket.setStatus(rs.getString("status"));
                        tickets.add(ticket);
                    }
                    return tickets;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return tickets;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return tickets;
            }
    }
}
