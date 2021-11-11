package am.hovall.common.mapper;

import am.hovall.common.entity.PresSeller;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.PresSellerRequest;
import am.hovall.common.response.PresSellerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PresSellerMapper implements BaseMapper<PresSeller, PresSellerRequest, PresSellerResponse> {

    private final ModelMapper mapper;

    @Override
    public PresSeller toEntity(PresSellerRequest presSellerRequest) {
        return mapper.map(presSellerRequest, PresSeller.class);
    }

    @Override
    public PresSellerResponse toResponse(PresSeller presSeller) {
        return mapper.map(presSeller, PresSellerResponse.class);
    }
}
