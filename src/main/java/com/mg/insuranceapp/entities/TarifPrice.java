package com.mg.insuranceapp.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class TarifPrice {

    @Id
    private String id;
    private BigDecimal price;

    public TarifPrice() {

    }

    public TarifPrice(final String id, final BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
