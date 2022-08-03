package com.example.barhop.repositories;


import com.example.barhop.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor,Long>{
    List<Vendor> findVendorById (long kw);
    Vendor findVendorByMail(String kn);
}
