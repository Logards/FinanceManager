package com.example.financemanager.repository;

import com.example.financemanager.classe.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseRepository {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ObservableList<Expense> expenseObservableList = FXCollections.observableArrayList();

    public ObservableList<Expense> getAllExpenses() {
        try (Connection connection = Database.connect()) {
            String query = "SELECT * FROM expenses";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String dateStr = rs.getString("period");
                LocalDate date = LocalDate.parse(dateStr, FORMATTER);
                Float total = rs.getFloat("total");
                float accommodation = rs.getFloat("accommodation");
                float food = rs.getFloat("food");
                float outing = rs.getFloat("outing");
                float transport = rs.getFloat("transport");
                float travel = rs.getFloat("travel");
                float taxes = rs.getFloat("taxes");
                float others = rs.getFloat("others");

                expenseObservableList.add(new Expense(date, total, accommodation, food, outing, transport, travel, taxes, others));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseObservableList;
    }

    public void addExpense(Expense expense) {
        String query = "INSERT INTO expenses (period, total, accommodation, food, outing, transport, travel, taxes, others) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expense.getPeriod().format(FORMATTER));
            statement.setFloat(2, expense.getTotal());
            statement.setFloat(3, expense.getAccommodation());
            statement.setFloat(4, expense.getFood());
            statement.setFloat(5, expense.getOuting());
            statement.setFloat(6, expense.getTransport());
            statement.setFloat(7, expense.getTravel());
            statement.setFloat(8, expense.getTaxes());
            statement.setFloat(9, expense.getOthers());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
