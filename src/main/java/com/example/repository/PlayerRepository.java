package com.example.repository;

import com.example.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

import java.util.List;

/**
 * @author prokade
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    public Set<Player> findByParentId(Integer parentId);

}
