package com.freetty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freetty.entity.ArtistSchedule;

public interface ArtistScheduleRepo extends JpaRepository<ArtistSchedule, Integer> {

}
