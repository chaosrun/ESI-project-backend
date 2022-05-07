package esi.project.ils.reservations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;


    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }



    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservationRepository.findAll().forEach(reservations::add);
        return reservations;
    }

    public Optional<Reservation> getReservationById(int id) {
        return reservationRepository.findById(id);
    }
    
    public Optional<Reservation> updateReservation(int id, Reservation updatedInfo) {

        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStart_date(updatedInfo.getStart_date());
            reservation.setEnd_date(updatedInfo.getEnd_date());
            reservation.setMaterial(updatedInfo.getMaterial());
            reservation.setUser(updatedInfo.getUser());
            reservation.setStatus(updatedInfo.getStatus());
            return reservationRepository.save(reservation);
        });

    }
    
    public void deleteReservationById(int id) {
        reservationRepository.deleteById(id);
    }


    public List<Reservation> getReservationByStatus(String status) {
        
        return reservationRepository.findByStatus(status);
    }
    
    public List<Reservation> getReservationByMatrialId(int material_id) {
        return reservationRepository.findByMaterialId(material_id);
    }
    
    public List<Reservation> getReservationByLoanPeriod(String start_date,String end_date) {
        return reservationRepository.findByLoanPeriod(start_date,end_date);
    }

}
