package carrot.app.mapper;

import carrot.app.User.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserVo getUserAccount(String username);
    void saveUser(UserVo userVo);
    int countUserByNickname(String unick);
    int countUserByEmail(String uemail);

    void updateProfileNickname(@Param("new_nick") String new_nick, @Param("past_nick") String past_nick);

}
