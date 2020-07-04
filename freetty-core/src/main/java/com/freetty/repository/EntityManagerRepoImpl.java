package com.freetty.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.freetty.entity.ArtistKind;

@Repository
public class EntityManagerRepoImpl implements EntityManagerRepo {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void insert(ArtistKind artistKind) {
		em.persist(artistKind);
	}

}
