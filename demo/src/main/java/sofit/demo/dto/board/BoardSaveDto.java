package sofit.demo.dto.board;

import sofit.demo.domain.Board;

public record BoardSaveDto(String title, String content, String hashtag) {

    public Board toEntity() {
        return Board.builder()
                    .title(title)
                    .content(content)
                    .hashtag(hashtag)
                    .build();
    }
}
