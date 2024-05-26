package sofit.demo.service.board;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sofit.demo.domain.Board;
import sofit.demo.dto.board.BoardInfoDto;
import sofit.demo.dto.board.BoardSaveDto;
import sofit.demo.dto.board.BoardUpdateDto;
import sofit.demo.global.util.SecurityUtil;
import sofit.demo.repository.BoardRepository;
import sofit.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void save(BoardSaveDto boardSaveDto) {
        Board board = boardSaveDto.toEntity();

        board.confirmWriter(userRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다.")));

        boardRepository.save(board);
    }

    public void update(Long id, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        checkAuthority(board);

        boardUpdateDto.title().ifPresent(board::updateTitle);
        boardUpdateDto.content().ifPresent(board::updateContent);
        boardUpdateDto.hashtag().ifPresent(board::updateHashtag);
    }

    public void delete(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        checkAuthority(board);

        boardRepository.delete(board);
    }

    public BoardInfoDto getBoardInfo(Long id) {
        return new BoardInfoDto(boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다.")));
    }

    public void checkAuthority(Board board) {
        if(!board.getUser().getEmail().equals(SecurityUtil.getLoginUsername())) 
            throw new IllegalArgumentException("x");
    }
}
