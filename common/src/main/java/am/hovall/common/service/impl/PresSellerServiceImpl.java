package am.hovall.common.service.impl;

import am.hovall.common.entity.PresSeller;
import am.hovall.common.repository.PresSellerRepository;
import am.hovall.common.service.PresSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresSellerServiceImpl implements PresSellerService {

    private final PresSellerRepository presSellerRepository;

    @Override
    public List<PresSeller> findAll() {
        return presSellerRepository.findAll();
    }

    @Override
    public void save(PresSeller presSeller) {
        if (findByEmail(presSeller.getEmail()).isEmpty() &&
                findByPhoneNumber(presSeller.getPhoneNumber()).isEmpty()) {
            presSellerRepository.save(presSeller);
        }
    }

    @Override
    public Optional<PresSeller> findByEmail(String email) {
        return presSellerRepository.findByEmail(email);
    }

    @Override
    public Optional<PresSeller> findByName(String name) {
        return presSellerRepository.findByName(name);
    }

    @Override
    public Optional<PresSeller> findByPhoneNumber(String phoneNumber) {
        return presSellerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<PresSeller> findById(Long id) {
        return presSellerRepository.findById(id);
    }
}
