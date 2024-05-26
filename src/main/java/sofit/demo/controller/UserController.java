package sofit.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sofit.demo.dto.email.EmailCheckDto;
import sofit.demo.dto.email.EmailRequestDto;
import sofit.demo.dto.user.UserSignUpDto;
import sofit.demo.service.email.EmailService;
import sofit.demo.service.user.UserService;

@RestController
@RequiredArgsConstructor
// @RequestMapping("/api/member")
// @CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @PostMapping("/a")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        System.out.println("이메일 인증 이메일: " + emailRequestDto.getEmail());
        return emailService.joinEmail(emailRequestDto.getEmail());
    }

    @PostMapping("/b")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        Boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        if(result){
                    return "ok";
                }
                else{
                    throw new NullPointerException("뭔가 잘못!");
                }
    }

    // @PostMapping("/emails/verification-requests")
    // public String mailSend(@RequestBody @Valid EmailRequestDto emailRequest) {
    //     System.out.println("이메일 인증 이메일: " + emailRequest.getEmail());
    //     return emailService.joinEmail(emailRequest.getEmail());
    // }

    // @PostMapping("/emails/verfications")
    // public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
    //     Boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
    //     if(result){
    //         System.out.println("인증 성공이요");
    //         return "ok";
    //     }
    //     else{
    //         throw new NullPointerException("뭔가 잘못!");
    //     }
    // }

    // @PostMapping("/emaills/verfications")
    // public boolean verifiedCode(@RequestBody EmailCheckDto emailCheckDto){
    //     boolean result = emailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
    //     return result;
    // }

    // @GetMapping("/jwt-test")
    // public String jwtTest() {
    //     return "jwtTest 요청 성공";
    // }
    

}