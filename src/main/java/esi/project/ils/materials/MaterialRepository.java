package esi.project.ils.materials;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Optional<Material> findById(int id);

    List<Material> findByTitleContaining(@Param("title") String title);

    List<Material> findByAuthorContaining(@Param("author") String author);

    List<Material> findByTypeContaining(@Param("type") String type);

    Optional<Material> findByCallNumber(String call_number);
}
