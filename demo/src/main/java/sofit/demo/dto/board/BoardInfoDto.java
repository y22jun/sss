package sofit.demo.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import sofit.demo.domain.Board;
import sofit.demo.dto.user.UserInfoDto;

@Data
@NoArgsConstructor
public class BoardInfoDto {
    
    private Long boardId;
    private String title;
    private String content;
    private String hashtag;
    private UserInfoDto userInfoDto;
    
    public BoardInfoDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.hashtag = board.getHashtag();
        this.userInfoDto = new UserInfoDto(board.getUser());
    }
}
