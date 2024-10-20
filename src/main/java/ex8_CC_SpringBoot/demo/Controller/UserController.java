package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.UserDto;
import ex8_CC_SpringBoot.demo.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Operations related to users: get all users, get users by ID, add a user")
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Add a new user",
            responses = {
                    @ApiResponse(
                    description = "User successfully added",
                    responseCode = "200"
            ),
                    @ApiResponse(
                    description = "Invalid user data provided",
                    responseCode = "400"
                    )
            })
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok().body("User added successfully");
    }


    @Operation(
            summary = "Get all users",
            responses = {
                    @ApiResponse(
                            description = "List of all users retrieved successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No users found",
                            responseCode = "404"
                    )
            })
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Get a specific user by their id",
            responses = {
                    @ApiResponse(
                            description = "User found and retrieved successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User with the specified ID not found",
                            responseCode = "404"
                    )
            })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
