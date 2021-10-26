package am.hovall.common.service.impl;

import am.hovall.common.entity.User;
import am.hovall.common.exception.CompanyNotFoundException;
import am.hovall.common.exception.CompanyWithoutUserException;
import am.hovall.common.exception.EmailRepeatingException;
import am.hovall.common.exception.UnVerifiedContractException;
import am.hovall.common.mapper.UserMapper;
import am.hovall.common.repository.CompanyRepository;
import am.hovall.common.repository.UserRepository;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserResponse registration(UserRequest userRequest) {
        if (!userRequest.isContractVerified()) throw new UnVerifiedContractException();
        if (companyRepository.findByRegisterNumber(userRequest.getCompanyRequest().getRegisterNumber()).isEmpty())
            throw new CompanyNotFoundException();
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) throw new EmailRepeatingException();
        User user = userMapper.toEntity(userRequest);
        user.setActive(false);
        user.setCreatedDateTime(LocalDateTime.now());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> findAllByCompanyId(Long id) {
        List<User> users = userRepository.findALlByCompany_Id(id);
        if (users.isEmpty()) {
            throw new CompanyWithoutUserException();
        }
        return users.stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void update(UserRequest userRequest, long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return;
        }
        User user = userOptional.get();
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            user.setEmail(userRequest.getEmail());
        }
        userRepository.save(user);
    }
}
