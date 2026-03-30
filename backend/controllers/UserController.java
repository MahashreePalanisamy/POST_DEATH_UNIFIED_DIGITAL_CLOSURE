import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

@Autowired  
private UserService userService;  

@PostMapping("/register")  
public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO dto) {  
    User user = userService.registerUser(dto);  
    return new ResponseEntity<>(userService.convertToDTO(user), HttpStatus.CREATED);  
}  

@GetMapping("/{userId}")  
public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {  
    User user = userService.getUserById(userId);  
    return new ResponseEntity<>(userService.convertToDTO(user), HttpStatus.OK);  
}  

@GetMapping("/aadhar/{aadhar}")  
public ResponseEntity<UserDTO> getUserByAadhar(@PathVariable String aadhar) {  
    User user = userService.getUserByAadhar(aadhar);  
    return new ResponseEntity<>(userService.convertToDTO(user), HttpStatus.OK);  
}  

@PostMapping("/death-notification")  
public ResponseEntity<String> notifyDeathStatus(@RequestBody UserDeathNotificationDTO dto) {  
    userService.markUserAsDeceased(dto.getUserId(), dto.getDateOfDeath());  
    return new ResponseEntity<>("Death notification recorded successfully", HttpStatus.OK);  
}  

@GetMapping("/active")  
public ResponseEntity<List<UserDTO>> getAllActiveUsers() {  
    List<UserDTO> users = userService.getAllActiveUsers();  
    return new ResponseEntity<>(users, HttpStatus.OK);  
} 

@PostMapping("/behavior-analysis")  
public ResponseEntity<String> analyzeBehavior(@RequestParam("file") MultipartFile file) {  
    String result = userService.analyzeBehavior(file);  
    return new ResponseEntity<>(result, HttpStatus.OK);  
}  
