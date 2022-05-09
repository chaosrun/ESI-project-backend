package esi.project.ils.requests;

import javax.validation.Valid;
import java.util.Optional;

import esi.project.ils.ErrorHandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class LoanRequestController {
    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping("/request/loan")
    public ResponseEntity<LoanRequest> addLoanRequest(@Valid @RequestBody LoanRequestForm loanRequestForm) {
        Location newLocation = new Location();
        newLocation.setAddress(loanRequestForm.getAddress());
        newLocation.setCity(loanRequestForm.getCity());
        newLocation.setZipCode(loanRequestForm.getZipCode());

        LoanRequest newLoanRequest = new LoanRequest();
        newLoanRequest.setStartDate(loanRequestForm.getStartDate());
        newLoanRequest.setEndDate(loanRequestForm.getEndDate());
        newLoanRequest.setStatus("REQUESTED");
        newLoanRequest.setLocation(newLocation);

        return new ResponseEntity<>(loanRequestService.addLoanRequest(newLoanRequest), HttpStatus.CREATED);
    }

    @PutMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequest> updateLoanRequest(@PathVariable int request_id,
                                                         @RequestBody LoanRequest loanRequest) {
        Optional<LoanRequest> result = loanRequestService.updateLoanRequest(request_id, loanRequest);
        return result.map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id " + request_id));
    }

    @DeleteMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequest> deleteLoanRequest(@PathVariable int request_id) {
        loanRequestService.deleteLoanRequest(request_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequest> getLoanRequest(@PathVariable int request_id) {
        Optional<LoanRequest> result = loanRequestService.getLoanRequest(request_id);
        return result.map(loanRequest -> new ResponseEntity<>(loanRequest, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id " + request_id));
    }
}
