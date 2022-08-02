package com.example.barhop.repositories;

import com.example.barhop.entities.BarAndDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarAndDealRepository extends JpaRepository<BarAndDeal,Long>{
    List<BarAndDeal> findBarAndDealByCity (String loc);
}

