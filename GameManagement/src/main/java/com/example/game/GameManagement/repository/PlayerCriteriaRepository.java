package com.example.game.GameManagement.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.game.GameManagement.model.PlayerPage;
import com.example.game.GameManagement.model.PlayerSearchCriteria;
import com.example.game.GameManagement.model.Players;

@Component
@Repository
public class PlayerCriteriaRepository {

	public final EntityManager entityManager;
	public final CriteriaBuilder criteriaBuilder;

	public PlayerCriteriaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	public Page<Players> findAllWithFilter(PlayerPage playerPage, PlayerSearchCriteria playerSearchCriteria) {
		CriteriaQuery<Players> criteriaQuery = criteriaBuilder.createQuery(Players.class);
		Root<Players> playerRoot = criteriaQuery.from(Players.class);
		Predicate predicate = getPredicate(playerSearchCriteria, playerRoot);
		criteriaQuery.where(predicate);
		TypedQuery<Players> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(playerPage.getPageNumber() * playerPage.getPageSize());
		typedQuery.setMaxResults(playerPage.getPageSize());
		Pageable pageable = getPageble(playerPage);
		long playersCount = getPlayersCount(predicate);
		return new PageImpl<Players>(typedQuery.getResultList(), pageable, playersCount);

	}

	private Predicate getPredicate(PlayerSearchCriteria playerSearchCriteria, Root<Players> playerRoot) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(Objects.nonNull(playerSearchCriteria.getPlayer()) && !playerSearchCriteria.getPlayer().isEmpty()) {
			In<String> inClause = criteriaBuilder.in(playerRoot.get("player"));
			for (String player : playerSearchCriteria.getPlayer()) {
			    inClause.value(player.toLowerCase());
			}
			predicates.add(inClause);
		}
		if(Objects.nonNull(playerSearchCriteria.getAfter()) && Objects.nonNull(playerSearchCriteria.getBefore())) {
			
			predicates.add(criteriaBuilder.between(playerRoot.get("time"), playerSearchCriteria.getAfter(), playerSearchCriteria.getBefore()));
		}
		else if(Objects.isNull(playerSearchCriteria.getAfter()) && Objects.nonNull(playerSearchCriteria.getBefore())) {
			predicates.add(criteriaBuilder.lessThan(playerRoot.<Date>get("time"), playerSearchCriteria.getBefore()));
		}
		else if(Objects.nonNull(playerSearchCriteria.getAfter()) && Objects.isNull(playerSearchCriteria.getBefore())) {
			predicates.add(criteriaBuilder.greaterThan(playerRoot.<Date>get("time"), playerSearchCriteria.getAfter()));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
	
	private Pageable getPageble(PlayerPage playerPage) {
		Sort sort = Sort.by(playerPage.getSort(),playerPage.getSortBy());
		return PageRequest.of(playerPage.getPageNumber(), playerPage.getPageSize(),sort);
	}
	
	private long getPlayersCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Players> countRoot = countQuery.from(Players.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
		return entityManager.createQuery(countQuery).getSingleResult();
	}

}
