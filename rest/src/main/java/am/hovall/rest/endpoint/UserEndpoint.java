package am.hovall.rest.endpoint;

import am.hovall.common.entity.User;
import am.hovall.common.mapper.UserMapper;
import am.hovall.common.request.UserRequest;
import am.hovall.common.response.UserResponse;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final UserMapper userMapper;
    private final UserService userService;

    @PutMapping("/users")
    public ResponseEntity<UserResponse> registration(@RequestBody UserRequest userRequest) {
        User user = userService.registration(userMapper.toEntity(userRequest));
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PostMapping("users/company/{id}")
    public ResponseEntity<List<UserResponse>> getByCompany(@PathVariable("id") Long id) {
        List<User> users = userService.findAllByCompanyId(id);
        if (users == null) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<UserResponse> userResponseList = new LinkedList<>();
        for (User user : users) {
            userResponseList.add(userMapper.toResponse(user));
        }
        return ResponseEntity.ok(userResponseList);
    }

}
