package esi.project.ils.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @RequestMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "user_id") String user_id) {

        Optional<User> user = null;
        if (!StringUtils.isEmpty(email)) {
            user = userService.getUserWithEmail(email);
        }
        if (!StringUtils.isEmpty(user_id)) {
            user = userService.getUserWithId(Integer.parseInt(user_id));
        }

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String id) {
        Optional<User> result = userService.updateUser(Integer.parseInt(id), user);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user")
    public ResponseEntity<User> deleteUser(@RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "user_id") String user_id) {

        if (!StringUtils.isEmpty(email)) {
            userService.deleteUserWithEmail(email);
        }
        if (!StringUtils.isEmpty(user_id)) {
            userService.deleteUserWithId(Integer.parseInt(user_id));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
