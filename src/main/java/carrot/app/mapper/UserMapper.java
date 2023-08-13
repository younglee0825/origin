package carrot.app.mapper;

import carrot.app.User.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVo getUserAccount(String username);
    void saveUser(UserVo userVo);
    int countUserByNickname(String unick);
    int countUserByEmail(String uemail);

    void updateProfileNickname(String new_nick, String past_nick);

    Integer getUserNumByUserNick(String nickname);
}
