package esi.project.ils.loan_requests;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findById(int id);
}
