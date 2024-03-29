package carrot.app.Controller;

import carrot.app.Exception.UserException;
import carrot.app.Profile.ProfileService;
import carrot.app.Profile.ProfileVo;
import carrot.app.User.User;
import carrot.app.User.UserService;
import carrot.app.User.UserVo;
import carrot.app.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    private UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/mypage")
//   @ResponseBody
    public String myPage(Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserVo) {
            UserVo userVo = (UserVo) authentication.getPrincipal();
            ProfileVo profileVo = (profileService.getProfileByUserNum(userVo.getUnum()));
            System.out.println(profileVo);

            String profileImageUrl = profileService.getProfileImageUrl(profileVo.getUser_num());
            System.out.println(profileImageUrl);

            model.addAttribute("nickname", userVo.getUnick() + "님"); //유저 아이디
            model.addAttribute("goal", profileVo.getGoal());
            model.addAttribute("profileImageUrl", profileImageUrl);
            if(profileVo.getImage()==null){
                model.addAttribute("accountNumber","default-profile");
            }else{
                model.addAttribute("accountNumber",profileVo.getUser_num());
            }
            return "/mypage";
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/mypage/editPro")
    public String editProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVo userVo = (UserVo) authentication.getPrincipal();
        ProfileVo profileVo = (profileService.getProfileByUserNum(userVo.getUnum()));

        model.addAttribute("nickname", userVo.getUnick());
        model.addAttribute("goal", profileVo.getGoal());
        if(profileVo.getImage()==null){
            model.addAttribute("accountNumber","default-profile");
        }else{
            model.addAttribute("accountNumber",profileVo.getUser_num());
        }

        return "/mypage/editPro";
    }


    @PostMapping("/mypage/editPro")
    public String editPro(
            @RequestParam(value="newNick", required = false) String nickname,
            @RequestParam(value = "inputGoal", required = false) String goal,
            @RequestPart(value="file",required = false) MultipartFile file,
            Authentication authentication
    ) {
        UserVo userVo = (UserVo) authentication.getPrincipal();
        userMapper.updateProfileNickname(userVo.getUnick(),nickname);
        profileService.saveUserGoal(goal, nickname); // 목표 저장 또는 업데이트
        MediaType contentType = MediaType.parseMediaType(file.getContentType());
        System.out.println(contentType);
        if (file != null) {
            profileService.saveImage(file); // 이미지 업로드
        }
        System.out.println(nickname);
        System.out.println(goal);
        System.out.println(file);

        return "redirect:/mypage";
        // UserVo 객체를 JSON 형태로 반환하지 않음
    }
}


