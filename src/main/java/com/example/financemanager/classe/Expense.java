package com.example.financemanager.classe;

import java.time.LocalDate;
import java.util.Objects;

public class Expense {
    private LocalDate period;
    private Float total;
    private float accommodation;
    private float food;
    private float outing;
    private float transport;
    private float travel;
    private float taxes;
    private float others;

    public Expense(LocalDate period, Float total, float accommodation, float food, float outing, float transport, float travel, float taxes, float others) {
        this.period = period;
        this.accommodation = accommodation;
        this.food = food;
        this.outing = outing;
        this.transport = transport;
        this.travel = travel;
        this.taxes = taxes;
        this.others = others;
        this.total = Objects.requireNonNullElseGet(total, this::getTotal);

    }

    public float getTotal() {
        return this.accommodation + this.food + this.outing + this.transport + this.travel + this.taxes + this.others;
    }

    public void setTotal() {
        this.total = accommodation + food + outing + transport + travel + taxes + others;
    }

    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    public float getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(float accommodation) {
        this.accommodation = accommodation;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public float getOuting() {
        return outing;
    }

    public void setOuting(float outing) {
        this.outing = outing;
    }

    public float getTransport() {

        return transport;
    }

    public void setTransport(float transport) {
        this.transport = transport;
    }

    public float getTravel() {
        return travel;
    }

    public void setTravel(float travel) {
        this.travel = travel;
    }

    public float getTaxes() {
        return taxes;
    }

    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public float getOthers() {
        return others;
    }

    public void setOthers(float others) {
        this.others = others;
    }
}
