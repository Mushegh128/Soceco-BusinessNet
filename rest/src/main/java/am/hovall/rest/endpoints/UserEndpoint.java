package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.CompanyDto;
import am.hovall.common.model.dto.UserDto;
import am.hovall.common.model.dto.UserRegisterDto;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.User;
import am.hovall.common.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @PutMapping("/users")
    public ResponseEntity<UserDto> registration(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.registration(modelMapper.map(userRegisterDto, User.class));
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }

    @PostMapping("users/company/{id}")
    public ResponseEntity<List<UserDto>> getByCompany(@PathVariable("id") Long id){
        List<User> users = userService.findAllByCompanyId(id);
        if (users == null){
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(parseToUserDto(users));
    }

    private List<UserDto> parseToUserDto(List<User> users){
        List<UserDto> userDtoList = new LinkedList<>();
        for (User user : users) {
            Company company = user.getCompany();
            CompanyDto companyDto =  modelMapper.map(company, CompanyDto.class);
            UserDto userDto = modelMapper.map(user, UserDto.class);
//            userDto.setCompany(companyDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

}
