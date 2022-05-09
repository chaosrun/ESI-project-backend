package esi.project.ils.users;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Integer deleteByEmail(String email);

    Optional<User> findById(int id);

    List<User> findByRole(String role);
}
