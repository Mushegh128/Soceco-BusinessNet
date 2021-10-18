package am.hovall.rest.endpoint;

import am.hovall.rest.response.MainResponse;
import am.hovall.rest.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainEndpoint {
    private final MainService mainService;

    @GetMapping("/main")
    public ResponseEntity<MainResponse> homePageData(@RequestBody long registerNumber) {
        return ResponseEntity.ok(mainService.collectMainResponse(registerNumber));
    }
}
