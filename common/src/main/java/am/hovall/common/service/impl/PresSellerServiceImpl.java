package am.hovall.common.service.impl;

import am.hovall.common.entity.PresSeller;
import am.hovall.common.exception.EmailNotFoundException;
import am.hovall.common.exception.PhoneNumberNotFoundException;
import am.hovall.common.exception.PreSellerNotFoundException;
import am.hovall.common.mapper.PresSellerMapper;
import am.hovall.common.repository.PresSellerRepository;
import am.hovall.common.request.PresSellerRequest;
import am.hovall.common.response.PresSellerResponse;
import am.hovall.common.service.PresSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresSellerServiceImpl implements PresSellerService {

    private final PresSellerRepository presSellerRepository;
    private final PresSellerMapper mapper;

    @Override
    public List<PresSellerResponse> findAll() {
        List<PresSeller> presSellers = presSellerRepository.findAll();
        return presSellers.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void update(PresSellerRequest presSellerRequest) throws PreSellerNotFoundException {
        PresSeller byId = presSellerRepository.getById(presSellerRequest.getId());
        if (byId == null) {
            throw new PreSellerNotFoundException();
        }
        presSellerRepository.save(mapper.toEntity(presSellerRequest));

    }

    @Override
    public void save(PresSellerRequest presSellerRequest) {
        if (presSellerRepository.findByEmail(presSellerRequest.getEmail()).isEmpty() &&
                presSellerRepository.findByPhoneNumber(presSellerRequest.getPhoneNumber()).isEmpty()) {
            presSellerRepository.save(mapper.toEntity(presSellerRequest));
        }
    }

    @Override
    public PresSellerResponse findByEmail(String email) {
        PresSeller presSeller = presSellerRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
        return mapper.toResponse(presSeller);
    }

    @Override
    public PresSellerResponse findByName(String name) {
        PresSeller presSeller = null;
        try {
            presSeller = presSellerRepository.findByName(name).orElseThrow(NameNotFoundException::new);
        } catch (NameNotFoundException e) {
            log.error("PresSeller by name:" + name + "NOT FOUND");
        }
        return mapper.toResponse(presSeller);
    }

    @Override
    public PresSellerResponse findByPhoneNumber(String phoneNumber) {
        PresSeller presSeller = presSellerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(PhoneNumberNotFoundException::new);
        return mapper.toResponse(presSeller);
    }

    @Override
    public PresSellerResponse findById(Long id) {
        PresSeller presSeller = presSellerRepository.findById(id).orElseThrow();
        return mapper.toResponse(presSeller);
    }
}
