package com.sparta.week_iv_homework.service;

import com.sparta.week_iv_homework.domain.User;
import com.sparta.week_iv_homework.dto.SignupRequestDto;
import com.sparta.week_iv_homework.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 정상상태")
    void createUserRecord_Normal() {
//given
        String username = "username";
        String password = "password";
        String passwordCheck = "Password";

        SignupRequestDto requestDto = new SignupRequestDto(
                username,
                password,
                passwordCheck
        );

         UserService userService = new UserService(userRepository,passwordEncoder);
//when
         User result = userService.createUserRecord(requestDto);
//then
         assertEquals(username,result.getUsername());
         assertEquals(passwordEncoder.encode(password),result.getPassword());
    }
}

// 테스트코드를 시간내에 만드는게 지금 제 실력으로는 불가능할 거 같습니다

//필요한 것들, 혹은 지금은 불가능한 것들  1 : 유효성검사를 위해 컨트롤러에서 매개변수로 @Valid의 값을 받는  Errors 타입과
//값을 돌려주기 위한 Model타입을  사용하는데 도대체 이걸 어떻게 테스트코드 안에서 매개변수로 넣어야할지 감이 안옵니다...
//그리고 이걸 안한다고 Service 단에서 검증을 시작하면 검증할게 없습니다...?

//필요한 것들 2 : 패스워드를 암호화해서 넣는데 암호화를 테스트 코드 내에 적용하는 것을 어떻게 해야할지 모르겠습니다
//테스트용 서버에 시큐리티를 적용해야 하는 것인가요? 강제로 암호화하는 부분만 구현할수는 있지만 그러면 코드에 대한 검증이 아닙니다