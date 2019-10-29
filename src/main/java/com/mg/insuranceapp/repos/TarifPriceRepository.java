package com.mg.insuranceapp.repos;

import com.mg.insuranceapp.entities.TarifPrice;
import com.mg.insuranceapp.enums.InsuranceTarif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifPriceRepository extends JpaRepository<TarifPrice, InsuranceTarif> {
}
