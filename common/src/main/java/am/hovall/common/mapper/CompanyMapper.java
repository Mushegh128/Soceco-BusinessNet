package am.hovall.common.mapper;

import am.hovall.common.entity.Company;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.CompanyRequest;
import am.hovall.common.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMapper implements BaseMapper<Company, CompanyRequest, CompanyResponse> {
    private final ModelMapper mapper;

    @Override
    public Company toEntity(CompanyRequest companyRequest) {
        return mapper.map(companyRequest, Company.class);
    }

    @Override
    public CompanyResponse toResponse(Company company) {
        return mapper.map(company, CompanyResponse.class);
    }
}
