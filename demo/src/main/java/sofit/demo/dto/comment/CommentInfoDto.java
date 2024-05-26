package sofit.demo.dto.comment;

import lombok.Data;
import sofit.demo.domain.Comment;
import sofit.demo.dto.user.UserInfoDto;
import java.util.*;

@Data
public class CommentInfoDto {
    
    private final static String DEFAULT_DELETE = "삭제된 댓글입니다.";

    /*
     * 대댓글 이슈로 헷갈려서 작성;; 코드 가독성 up st
     */
    private Long boardId; //음.. 댓글이 달린 게시글 id!

    private Long commentId; //해당 댓글 ID

    private String content; // 댓글 내용임

    private boolean isRemove; //->삭제되었는지 확인 용도.
    
    private UserInfoDto userInfoDto; //댓글 작성자 정보 용도임

    private List<ReCommentInfoDto> reCommentInfoDtoList;

    /**
     * 대댓글 -> 부모 댓글 삭제 -> 그래도 남아 있음. 그래서 부모 댓글 삭제 해도 삭제된 댓글입니다 출력할거임.
     * @param comment
     * @param reCommentList
     */
    public CommentInfoDto(Comment comment, List<Comment> reCommentList) {

        this.boardId = comment.getBoard().getId();
        this.commentId = comment.getId();

        this.content = comment.getContent();

        if(comment.isRemoved()) {
            this.content = DEFAULT_DELETE;
        }

        this.isRemove = comment.isRemoved();

        this.userInfoDto = new UserInfoDto(comment.getUser());

        this.reCommentInfoDtoList = reCommentList.stream().map(ReCommentInfoDto::new).toList();
    }
}
