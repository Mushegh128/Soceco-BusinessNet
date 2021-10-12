package am.hovall.common.services;

import am.hovall.common.model.entities.User;

import java.util.List;

public interface UserService {
    User registration(User user);

    List<User> findAllByCompanyId(Long id);
}
