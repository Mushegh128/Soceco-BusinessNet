package am.hovall.common.service.impl;

import am.hovall.common.entity.Company;
import am.hovall.common.entity.User;
import am.hovall.common.exception.*;
import am.hovall.common.mapper.UserMapper;
import am.hovall.common.repository.CompanyRepository;
import am.hovall.common.repository.UserRepository;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final MailService mailService;

    @Value("${file.htmlTemplate.name}")
    private String HTML_NAME;
    @Value("${file.htmlTemplate.path}")
    private String HTML_PATH;
    @Value("${file.htmlTemplate.message}")
    private String MESSAGE;

    @Override
    public UserResponse registration(UserRequest userRequest) {
        if (!userRequest.isContractVerified()){ throw new UnVerifiedContractException();}
        Optional<Company> byRegisterNumber = companyRepository.findByRegisterNumber(userRequest.getCompanyRequest().getRegisterNumber());
        if (byRegisterNumber.isEmpty()) {
            mailService.send(userRequest.getEmail(), "Ծանուցում", MESSAGE);
            throw new CompanyNotFoundException();
        }
        Optional<User> byEmail = userRepository.findByEmail(userRequest.getEmail());
        if (byEmail.isPresent()){ throw new EmailRepeatingException()};
        User user = userMapper.toEntity(userRequest);
        user.setActive(false);
        user.setCreatedDateTime(LocalDateTime.now());
        user.setMailVerifyToken(UUID.randomUUID());
        userRepository.save(user);
        try {
            mailService.sendHtmlEmail(user.getEmail(), "Welcome", user, HTML_PATH, HTML_NAME);
        } catch (MessagingException e) {
            log.error("email message to" + user.getEmail() + "didn't sent");
        }
        return userMapper.toResponse(user);
    }

    @Override
    public boolean verifyUser(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        if (user.getMailVerifyToken().equals(UUID.fromString(token))) {
            user.setMailVerified(true);
            user.setMailVerifyToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserResponse> findAllByCompanyId(Long id) {
        List<User> users = userRepository.findALlByCompany_Id(id);
        return users.stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void update(UserRequest userRequest, long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        Optional<User> byEmail = userRepository.findByEmail(userRequest.getEmail());
        if (byEmail.isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }
        userRepository.save(user);
    }
}
