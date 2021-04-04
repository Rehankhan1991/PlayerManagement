/**
 * 
 */
package com.example.game.GameManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.game.GameManagement.model.Players;

/**
 * @author Rehan Khan
 *
 */
@Component
@Repository
public interface PlayerRepository extends CrudRepository<Players, Long> {
	
	@Query(value = "SELECT * FROM Players p1 where  SCORE=(SELECT MIN(p.score) FROM Players p where p.player LIKE ?1)  AND player LIKE ?1 LIMIT 1", nativeQuery = true)
	public Players min(String name);

	@Query(value = "SELECT * FROM Players p1 where  SCORE=(SELECT MAX(p.score) FROM Players p where p.player LIKE ?1)  AND player LIKE ?1 LIMIT 1", nativeQuery = true)
	public Players max(String name);
	
	@Query(value = "SELECT avg(p.score) FROM Players p where p.player LIKE ?1")
	public Double avg(String name);
	
	@Query(value = "SELECT p FROM Players p where player LIKE ?1")
	public List<Players> playerAllScores(String name);
}
