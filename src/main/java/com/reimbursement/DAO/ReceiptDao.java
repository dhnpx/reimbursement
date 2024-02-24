package com.reimbursement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;

public class ReceiptDao {
    public static int saveFile(int ticket_id, String fileName) {
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO ticketapi.receipts(ticket_id, file_name) VALUES(?, ?)")) {
            stmt.setInt(1, ticket_id);
            stmt.setString(2, fileName);
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

    public static String getFileName(int ticket_id) {
        String fileName = "";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT file_name FROM ticketapi.receipts WHERE ticket_id = ?")) {
            stmt.setInt(1, ticket_id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            fileName = rs.getString("file_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
