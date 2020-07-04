package com.freetty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.LicenseKind;

public interface LicenseKindRepo extends JpaRepository<LicenseKind, Integer> {

	List<LicenseKind> findByLicenseName(String licenseName);
}
