package sofit.demo.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sofit.demo.domain.User;

@Data
@NoArgsConstructor
public class UserInfoDto {
    
    private String email;
    private String nickname;

    @Builder
    public UserInfoDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
