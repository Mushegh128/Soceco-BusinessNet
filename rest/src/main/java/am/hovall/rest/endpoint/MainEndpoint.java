package am.hovall.rest.endpoint;

import am.hovall.common.response.MainResponse;
import am.hovall.common.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainEndpoint {
    private final MainService mainService;

    @PostMapping("/main")
    public ResponseEntity<MainResponse> homePageData(@RequestBody Long registerNumber) {
        return ResponseEntity.ok(mainService.collectMainResponse(registerNumber));
    }
}
