package sofit.demo.service.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Comment;
import sofit.demo.dto.comment.CommentSaveDto;
import sofit.demo.dto.comment.CommentUpdateDto;
import sofit.demo.repository.BoardRepository;
import sofit.demo.repository.CommentRepository;
import sofit.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void save(Long boardId, CommentSaveDto commentSaveDto) {

        Comment comment = commentSaveDto.toEntity();

        commentRepository.save(comment);
    }

    public void saveReComment(Long boardId, Long parentId, CommentSaveDto commentSaveDto) {

        Comment comment = commentSaveDto.toEntity();

        commentRepository.save(comment);
    }

    public void update(Long id, CommentUpdateDto commentUpdateDto) {
        
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        commentUpdateDto.content().ifPresent(comment::updateContent);
    }

    public void delete(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        comment.remove();
    }
}
