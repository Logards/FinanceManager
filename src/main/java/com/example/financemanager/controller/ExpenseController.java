package com.example.financemanager.controller;

import com.example.financemanager.ExpenseDialog;
import com.example.financemanager.classe.Expense;
import com.example.financemanager.repository.ExpenseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ExpenseController {

    private final ObservableList<Expense> expenseData = FXCollections.observableArrayList();
    private final ExpenseRepository expenseRepository = new ExpenseRepository();
    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private Button addButton;

    @FXML
    public void initialize() {
        getExpenseData();
        expenseTable.setItems(expenseData);

        expenseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void getExpenseData() {
        expenseData.clear();
        expenseData.addAll(expenseRepository.getAllExpenses());
    }

    @FXML
    public void showAddExpenseDialog() {
        ExpenseDialog dialog = new ExpenseDialog();
        dialog.showAndWait().ifPresent(expenseData::add);
    }
}