package am.hovall.common.mapper;

import am.hovall.common.entity.Company;
import am.hovall.common.entity.User;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.CompanyResponse;
import am.hovall.common.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper implements BaseMapper<User, UserRequest, UserResponse> {
    private final ModelMapper mapper;

    @Override
    public User toEntity(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        if (userRequest.getCompanyRequest() != null) {
            user.setCompany(mapper.map(userRequest.getCompanyRequest(), Company.class));
        }
        return user;
    }

    @Override
    public UserResponse toResponse(User user) {
        UserResponse userResponse = mapper.map(user, UserResponse.class);
        userResponse.setCompanyResponse(mapper.map(user.getCompany(), CompanyResponse.class));
        return userResponse;
    }
}
