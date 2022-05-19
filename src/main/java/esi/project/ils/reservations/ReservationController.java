package esi.project.ils.reservations;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> addResponseEntity(@Valid @RequestBody Reservation reservation) {
        // System.out.println(reservation.getUser());
        // System.out.println(reservation.getMaterial());
        Reservation newReservation = reservationService.addReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @RequestMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {

        List<Reservation> reservationList = reservationService.getAllReservations();

        if (reservationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationList, HttpStatus.OK);

    }

    @RequestMapping("/reservations/{reservation_id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String reservation_id) {
        Optional<Reservation> reservation = reservationService.getReservationById(Integer.parseInt(reservation_id));

        if (reservation.isPresent()) {
            return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/reservation/{reservation_id}")
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation,
            @PathVariable String reservation_id) {
        Optional<Reservation> result = reservationService.updateReservation(Integer.parseInt(
                reservation_id), reservation);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/reservation/{reservation_id}")
    public ResponseEntity<Reservation> deleteUserById(@PathVariable String resrvation_id) {
        reservationService.deleteReservationById(Integer.parseInt(resrvation_id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/reservations/status/{status}")
    public ResponseEntity<List<Reservation>> getReservationByStatus(@PathVariable String status) {
        List<Reservation> reservationList = reservationService.getReservationByStatus(status);

        if (reservationList.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }

    @RequestMapping("/reservations/material/{material_id}")
    public ResponseEntity<List<Reservation>> getReservationByMaterialId(@PathVariable String material_id) {
        List<Reservation> reservationList = reservationService
                .getReservationByMaterialId(Integer.parseInt(material_id));

        if (reservationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }

    @RequestMapping("/reservations/start/{loan_start_date}/end/{loan_end_date}")
    public ResponseEntity<List<Reservation>> getReservationByLoanPeriod(@PathVariable String loan_start_date,
            @PathVariable String loan_end_date) {
        List<Reservation> reservationList = reservationService.getReservationByStartDateAndEndDate(
                loan_start_date, loan_end_date);

        if (reservationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }
}
