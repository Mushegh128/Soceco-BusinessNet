package am.hovall.rest.endpoint;

import am.hovall.common.exception.UserNotFoundException;
import am.hovall.common.request.UserAuthRequest;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserAuthResponse;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponse> registration(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.registration(userRequest));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<UserResponse>> getByCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findAllByCompanyId(id));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponse> auth(@RequestBody UserAuthRequest userAuthRequest) {
        try {
            return ResponseEntity.ok(userService.auth(userAuthRequest));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
