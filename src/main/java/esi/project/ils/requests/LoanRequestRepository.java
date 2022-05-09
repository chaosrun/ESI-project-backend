package esi.project.ils.requests;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
    Optional<LoanRequest> findById(int id);

    List<LoanRequest> findByUserId(int userId);

    List<LoanRequest> findByMaterialId(int materialId);

    List<LoanRequest> findByStatus(String status);

}
