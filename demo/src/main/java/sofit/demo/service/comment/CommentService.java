package sofit.demo.service.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Comment;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.comment.CommentInfoDto;
import sofit.demo.dto.comment.CommentSaveDto;
import sofit.demo.dto.comment.CommentUpdateDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.BoardRepository;
import sofit.demo.repository.CommentRepository;
import sofit.demo.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void save(Long boardId, CommentSaveDto commentSaveDto) {

        Comment comment = commentSaveDto.toEntity();

        comment.confirmUser(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("X")));

        comment.confirmBoard(boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("X")));

        commentRepository.save(comment);
    }

    public void saveReComment(Long boardId, Long parentId, CommentSaveDto commentSaveDto) {

        Comment comment = commentSaveDto.toEntity();

        comment.confirmUser(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("X")));

        comment.confirmBoard(boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("X")));

        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("X")));


        commentRepository.save(comment);
    }

    public void update(Long id, CommentUpdateDto commentUpdateDto) {
        
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if(!comment.getUser().getEmail().equals(SecurityUtil.getLoginUsername())){
            throw new IllegalArgumentException("X");
        }

        commentUpdateDto.content().ifPresent(comment::updateContent);
    }

    public void delete(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if(!comment.getUser().getEmail().equals(SecurityUtil.getLoginUsername())){
            throw new IllegalArgumentException("X");
        }

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }

}
