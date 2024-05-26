package sofit.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.comment.CommentSaveDto;
import sofit.demo.dto.comment.CommentUpdateDto;
import sofit.demo.service.comment.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping("/comment/{boardId}")
    public void commentSave(@PathVariable("boardId") Long boardId, @RequestBody CommentSaveDto commentSaveDto) {
        commentService.save(boardId, commentSaveDto);
    }

    @PostMapping("/comment/{boardId}/{commentId}")
    public void reCommentSave(@PathVariable("boardId") Long boardId, @PathVariable("commentId") Long commentId, @RequestBody CommentSaveDto commentSaveDto) {
        commentService.saveReComment(boardId, commentId, commentSaveDto);
    }

    @PutMapping("/comment/{commentId}")
    public void update(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.update(commentId, commentUpdateDto);
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
