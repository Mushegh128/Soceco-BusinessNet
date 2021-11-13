package am.hovall.common.service;

import am.hovall.common.request.PresSellerRequest;
import am.hovall.common.response.PresSellerResponse;

import java.util.List;

public interface PresSellerService {

    List<PresSellerResponse> findAll();

    void save(PresSellerRequest presSellerRequest);

    PresSellerResponse findByEmail(String email);

    PresSellerResponse findByName(String name);

    PresSellerResponse findByPhoneNumber(String phoneNumber);

    PresSellerResponse findById(Long id);
}
