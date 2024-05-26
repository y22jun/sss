package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sofit.demo.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>  {
    
}
