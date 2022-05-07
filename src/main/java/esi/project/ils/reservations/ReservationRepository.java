package esi.project.ils.reservations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    public List<Reservation> findByStatus(String status);
    public List<Reservation> findByMaterialId(int material_id);

    public List<Reservation> findByLoanPeriod(String start_date,String end_date);

    
}
