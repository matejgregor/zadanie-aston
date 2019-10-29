package com.mg.insuranceapp.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class AdditionalFee {
    @Id
    private String id;
    private BigDecimal percentage;

    public AdditionalFee() {

    }

    public AdditionalFee(final String id, final BigDecimal percentage) {
        this.id = id;
        this.percentage = percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(final BigDecimal percentage) {
        this.percentage = percentage;
    }
}
