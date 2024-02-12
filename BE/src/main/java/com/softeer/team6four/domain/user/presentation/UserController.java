package com.softeer.team6four.domain.user.presentation;


import com.softeer.team6four.domain.user.application.EmailService;
import com.softeer.team6four.domain.user.application.NicknameService;
import com.softeer.team6four.domain.user.application.UserJoinService;
import com.softeer.team6four.domain.user.application.response.EmailCheck;
import com.softeer.team6four.domain.user.application.request.SignUpRequest;
import com.softeer.team6four.domain.user.application.request.SignInRequest;
import com.softeer.team6four.domain.user.application.response.NicknameCheck;
import com.softeer.team6four.domain.user.application.response.SignInJwtResponse;
import com.softeer.team6four.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final EmailService emailService;
    private final NicknameService nicknameService;
    private final UserJoinService userJoinService;

    @GetMapping(value = "/email/check")
    public ResponseDto<EmailCheck> checkEmail(@RequestParam String email) {
        return emailService.findEmail(email);
    }

    @GetMapping(value = "/nickname/check")
    public ResponseDto<NicknameCheck> checkNickname(@RequestParam String nickname) {
        return nicknameService.findNickname(nickname);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody SignUpRequest signupRequest) {userJoinService.signup(signupRequest);}

    @PostMapping("/auth/signin")
    public ResponseDto<SignInJwtResponse> login(@RequestBody SignInRequest signinRequest) {
        return userJoinService.signin(signinRequest);
    }

}

