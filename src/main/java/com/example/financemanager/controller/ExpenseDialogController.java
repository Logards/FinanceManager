package com.example.financemanager.controller;

import com.example.financemanager.classe.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExpenseDialogController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField accommodationTextField;
    @FXML
    private TextField foodTextField;
    @FXML
    private TextField outgoingTextField;
    @FXML
    private TextField transportTextField;
    @FXML
    private TextField travelTextField;
    @FXML
    private TextField taxesTextField;
    @FXML
    private TextField otherTextField;

    private Button addButton;

    @FXML
    private void initialize() {
        // Récupérer le bouton après que la boîte de dialogue soit complètement initialisée
        // L'exécution est reportée pour s'assurer que DialogPane est complètement initialisé
        datePicker.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DialogPane dialogPane = (DialogPane) datePicker.getScene().getRoot();
                addButton = (Button) dialogPane.lookupButton(
                        dialogPane.getButtonTypes().stream()
                                .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                                .findFirst().orElse(null)
                );

                // Désactiver le bouton initialement
                if (addButton != null) {
                    addButton.setDisable(true);
                }

                // Configurer les écouteurs sur chaque champ
                setupValidationListeners();
            }
        });
    }

    private void setupValidationListeners() {
        // Ajouter des écouteurs pour tous les champs obligatoires
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> validateInputs());

        // Vous pouvez ajuster les champs obligatoires selon vos besoins
        // Si seule la date est obligatoire, gardez uniquement l'écouteur ci-dessus

        // Si d'autres champs sont requis, ajoutez des écouteurs pour eux aussi
        accommodationTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        foodTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        outgoingTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        transportTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        travelTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        taxesTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
        otherTextField.textProperty().addListener((obs, oldVal, newVal) -> validateInputs());
    }

    private void validateInputs() {
        if (addButton == null) return;

        // Vérifiez que la date est sélectionnée
        boolean isDateSelected = datePicker.getValue() != null;

        // Vérifiez que tous les champs de montant sont remplis
        boolean allFieldsFilled =
                !accommodationTextField.getText().trim().isEmpty() &&
                        !foodTextField.getText().trim().isEmpty() &&
                        !outgoingTextField.getText().trim().isEmpty() &&
                        !transportTextField.getText().trim().isEmpty() &&
                        !travelTextField.getText().trim().isEmpty() &&
                        !taxesTextField.getText().trim().isEmpty() &&
                        !otherTextField.getText().trim().isEmpty();

        // Vérifiez que tous les champs numériques contiennent des nombres valides
        boolean allValidNumbers = validateNumericFields();

        // Activer/désactiver le bouton selon les conditions
        addButton.setDisable(!(isDateSelected && allFieldsFilled && allValidNumbers));
    }

    private boolean validateNumericFields() {
        // Vérifiez que tous les champs remplis contiennent des nombres valides
        return isValidFloatOrEmpty(accommodationTextField.getText()) &&
                isValidFloatOrEmpty(foodTextField.getText()) &&
                isValidFloatOrEmpty(outgoingTextField.getText()) &&
                isValidFloatOrEmpty(transportTextField.getText()) &&
                isValidFloatOrEmpty(travelTextField.getText()) &&
                isValidFloatOrEmpty(taxesTextField.getText()) &&
                isValidFloatOrEmpty(otherTextField.getText());
    }

    private boolean isValidFloatOrEmpty(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }

        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Expense getExpense() {
        try {
            float accommodation = accommodationTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(accommodationTextField.getText());
            float food = foodTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(foodTextField.getText());
            float outing = outgoingTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(outgoingTextField.getText());
            float transport = transportTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(transportTextField.getText());
            float travel = travelTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(travelTextField.getText());
            float taxes = taxesTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(taxesTextField.getText());
            float other = otherTextField.getText().isEmpty() ? 0.0f : Float.parseFloat(otherTextField.getText());

            return new Expense(
                    datePicker.getValue(),
                    0.0f, // Le total est calculé dans le constructeur
                    accommodation,
                    food,
                    outing,
                    transport,
                    travel,
                    taxes,
                    other
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Veuillez entrer des valeurs numériques valides", e);
        }
    }
}