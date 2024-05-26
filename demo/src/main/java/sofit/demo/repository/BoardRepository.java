package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Board;

import java.util.*;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    Optional<Board> findWithById(Long id);
}
