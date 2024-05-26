package sofit.demo.dto.board;

import sofit.demo.domain.Board;

public record BoardSaveDto(String title, String content) {

    public Board toEntity() {
        return Board.builder()
                    .title(title)
                    .content(content)
                    .build();
    }
}
