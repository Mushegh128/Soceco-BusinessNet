package am.hovall.common.service.impl;

import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.repository.MadeInCountryRepository;
import am.hovall.common.service.MadeInCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MadeInCountryServiceImpl implements MadeInCountryService {

    private final MadeInCountryRepository madeInCountryRepository;

    @Override
    public List<MadeInCountry> findAll() {
        return madeInCountryRepository.findAll();
    }
}
