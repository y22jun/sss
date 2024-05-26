package sofit.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sofit.demo.dto.board.BoardSaveDto;
import sofit.demo.dto.board.BoardUpdateDto;
import sofit.demo.service.board.BoardService;

@RestController
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    @PostMapping("/board/save")
    public void save(@RequestBody BoardSaveDto boardSaveDto) {
        boardService.save(boardSaveDto);
    }

    @PostMapping("/board/{boardId}")
    public void update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateDto boardUpdateDto) {
        boardService.update(boardId, boardUpdateDto);
    }

    @DeleteMapping("/board/{boardId}")
    public void delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
    }

    @GetMapping("board/{boardId}")
    public ResponseEntity getInfo(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoardInfo(boardId));
    }
}
