package mainPackage;

import mainPackage.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class MainController {
    private HashMap<String, User> users;

    MainController() {
        users = new HashMap<>();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (users.get(user.getLogin()) != null &&
                users.get(user.getLogin()).getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body("Successful login!\n");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid login or password\n");
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (users.get(user.getLogin()) == null) {
            users.put(user.getLogin(), user);
            return ResponseEntity.status(HttpStatus.OK).body("Successful register!\n");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Login already exists\n");
    }

    @RequestMapping(path = "/getusers", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no users!\n");
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


}
