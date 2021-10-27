package am.hovall.common.service;

import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registration(UserRequest userRequest);

    boolean verifyUser(String email, String token);

    List<UserResponse> findAllByCompanyId(Long id);

    void update(UserRequest userRequest, long id);
}
