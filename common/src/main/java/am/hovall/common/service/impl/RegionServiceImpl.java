package am.hovall.common.service.impl;

import am.hovall.common.entity.Region;
import am.hovall.common.repository.RegionRepository;
import am.hovall.common.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<Region> findAll() {
        return regionRepository.findAll();
    }
}
