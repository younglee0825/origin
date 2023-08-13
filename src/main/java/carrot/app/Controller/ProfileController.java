package carrot.app.Controller;

import carrot.app.Exception.UserException;
import carrot.app.Profile.ProfileService;
import carrot.app.Profile.ProfileVo;
import carrot.app.User.User;
import carrot.app.User.UserService;
import carrot.app.User.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    private UserService userService;


    @GetMapping("/mypage")
//   @ResponseBody
    public String myPage(Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserVo) {
            UserVo userVo = (UserVo) authentication.getPrincipal();
            ProfileVo profileVo = (profileService.getProfileByUserNum(userVo.getUnum()));
            System.out.println(profileVo);

            if (profileVo == null) {
                profileService.createNewProfile(userVo.getUnum());
                profileVo = profileService.getProfileByUserNum(userVo.getUnum());
            }

            model.addAttribute("nickname", userVo.getUnick() + "님"); //유저 아이디
            model.addAttribute("goal", profileVo.getGoal());
            return "/mypage";
        } else {
//            // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트 또는 다른 처리를 하도록 합니다.
//            // 예: return "redirect:/login"
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
        return "/mypage/editPro";
    }


    @PostMapping ("/mypage/editPro")
    public String editPro(@RequestParam(value="newNick", required = false) String nickname,
                          @RequestParam(value = "inputGoal", required = false) String goal,
                          Authentication authentication) {
        UserVo userVo = (UserVo) authentication.getPrincipal();
        userVo.setUnick(nickname); // 닉네임 업데이트
        profileService.saveUserGoal(goal, nickname);// 목표 저장 또는 업데이트
        System.out.println(nickname);
        System.out.println(goal);
        return "redirect:/mypage";
        // UserVo 객체를 JSON 형태로 반환하지 않음
    }

}


