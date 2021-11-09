package am.hovall.common.service;

import am.hovall.common.entity.PresSeller;

import java.util.List;
import java.util.Optional;

public interface PresSellerService {

    List<PresSeller> findAll();

    void save(PresSeller presSeller);

    Optional<PresSeller> findByEmail(String email);

    Optional<PresSeller> findByName(String name);

    Optional<PresSeller> findByPhoneNumber(String phoneNumber);

    Optional<PresSeller> findById(Long id);
}
