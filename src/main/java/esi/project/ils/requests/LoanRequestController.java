package esi.project.ils.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
