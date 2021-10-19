package am.hovall.rest.endpoint;

import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserService userService;

    @PutMapping("/users")
    public ResponseEntity<UserResponse> registration(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.registration(userRequest));
    }

    @PostMapping("users/company/{id}")
    public ResponseEntity<List<UserResponse>> getByCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findAllByCompanyId(id));
    }

}
