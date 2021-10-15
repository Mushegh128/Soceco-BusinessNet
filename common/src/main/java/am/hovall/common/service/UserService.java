package am.hovall.common.service;

import am.hovall.common.entity.User;

import java.util.List;

public interface UserService {
    User registration(User user);

    List<User> findAllByCompanyId(Long id);
}
