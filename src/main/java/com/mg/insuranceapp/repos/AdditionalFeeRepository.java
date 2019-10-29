package com.mg.insuranceapp.repos;

import com.mg.insuranceapp.entities.AdditionalFee;
import com.mg.insuranceapp.enums.InsuranceAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalFeeRepository extends JpaRepository<AdditionalFee, InsuranceAdditional> {
}
