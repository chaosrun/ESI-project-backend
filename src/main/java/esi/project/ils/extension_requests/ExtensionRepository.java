package esi.project.ils.extension_requests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtensionRepository extends JpaRepository<ExtensionRequest, Integer> {
    Optional<ExtensionRequest> findById(int id);

    List<ExtensionRequest> findByUserId(int userId);

    List<ExtensionRequest> findByMaterialId(int materialId);

    List<ExtensionRequest> findByStatus(String status);

}
