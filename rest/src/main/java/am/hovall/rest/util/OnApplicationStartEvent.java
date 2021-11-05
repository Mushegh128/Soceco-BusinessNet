package am.hovall.rest.util;


import am.hovall.common.entity.Company;
import am.hovall.common.entity.Role;
import am.hovall.common.entity.User;
import am.hovall.common.repository.CompanyRepository;
import am.hovall.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OnApplicationStartEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (userRepository.findByEmail("test.for.project.java.2021@gmail.com").isEmpty()) {
            Company company = Company.builder()
                    .name("Distributor LLC")
                    .registerNumber(055050505)
                    .isActive(true)
                    .address("anywhere")
                    .barcode(505)
                    .isVerified(true)
                    .build();
            companyRepository.save(company);
            userRepository.save(User.builder()
                    .name("admin")
                    .surname("adminyan")
                    .company(company)
                    .createdDateTime(LocalDateTime.now())
                    .isActive(true)
                    .isContractVerified(true)
                    .email("test.for.project.java.2021@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.COMPANY_ADMIN)
                    .build());
        }
    }
}
