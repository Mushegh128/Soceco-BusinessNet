package am.hovall.common.service;

import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registration(UserRequest userRequest);

    List<UserResponse> findAllByCompanyId(Long id);
}
