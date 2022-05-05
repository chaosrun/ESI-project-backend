package esi.project.ils.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUserWithId(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(int id, User updatedUserInformation) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUserInformation.getName());
            user.setEmail(updatedUserInformation.getEmail());
            user.setPassword(updatedUserInformation.getPassword());
            user.setRole(updatedUserInformation.getRole());
            return userRepository.save(user);
        });
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
