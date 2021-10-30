package am.hovall.common.service.impl;

import am.hovall.common.entity.User;
import am.hovall.common.exception.UserNotFoundException;
import am.hovall.common.mapper.UserMapper;
import am.hovall.common.repository.UserRepository;
import am.hovall.common.request.UserAuthRequest;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserAuthResponse;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import am.hovall.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper mapper;

    @Override
    public UserResponse registration(UserRequest userRequest) {
        return null;
    }

    @Override
    public List<UserResponse> findAllByCompanyId(Long id) {
        return null;
    }

    @Override
    public UserAuthResponse auth(UserAuthRequest userAuthRequest) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(userAuthRequest.getEmail());
        if (byEmail.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = byEmail.get();
        if (passwordEncoder.matches(userAuthRequest.getPassword(), user.getPassword())) {
            return new UserAuthResponse(jwtTokenUtil.generateToken(user.getEmail()),
                    mapper.toResponse(user));
        }
        throw new UserNotFoundException();
    }

}
