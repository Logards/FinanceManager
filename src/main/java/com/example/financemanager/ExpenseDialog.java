package com.example.financemanager;

import com.example.financemanager.classe.Expense;
import com.example.financemanager.controller.ExpenseDialogController;
import com.example.financemanager.repository.ExpenseRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.util.Objects;

public class ExpenseDialog extends Dialog<Expense> {
    private final ExpenseRepository expenseRepository = new ExpenseRepository();

    public ExpenseDialog() {
        try {
            setTitle("Add Expense");
            setHeaderText("Enter the details of the expense");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("expense-dialog.fxml"));
            DialogPane dialogPane = loader.load();
            setDialogPane(dialogPane);
            ExpenseDialogController expenseDialogController = loader.getController();
            setResultConverter(buttonType -> {
                if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                    return null;
                }
                expenseRepository.addExpense(expenseDialogController.getExpense());
                return expenseDialogController.getExpense();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
