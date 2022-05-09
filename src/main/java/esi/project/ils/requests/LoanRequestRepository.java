package esi.project.ils.requests;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
    Optional<LoanRequest> findById(int id);
}
