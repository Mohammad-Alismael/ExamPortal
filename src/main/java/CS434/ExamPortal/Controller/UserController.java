package CS434.ExamPortal.Controller;

import CS434.ExamPortal.DAO.Users;
import CS434.ExamPortal.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/authenticate") // Map ONLY POST Requests
    public @ResponseBody
    List<Users> authenticateUser (@RequestBody Users userInfo) {
        List<Users> user = userRepository.
                findUsersByUsernameAndAndPassword(
                        userInfo.getUsername(),
                        userInfo.getPassword());
        if (user.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect username or password");
        }

        return user;

    }

    @GetMapping(path="/list-users")
    public @ResponseBody Iterable<Users>test() {
        return userRepository.findAll();
    }

    @PostMapping(path="/add-user") // Map ONLY POST Requests
    public @ResponseBody
    Users addNewUser (@RequestBody Users userInfo) {
        Users users = userRepository.findByUsername(userInfo.getUsername());

        if (users == null){
            userRepository.save(userInfo);
            return userInfo;
        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already taken");
        }

    }

}
