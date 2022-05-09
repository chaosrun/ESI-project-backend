package esi.project.ils.requests;

import javax.validation.Valid;
import java.util.Optional;

import esi.project.ils.ErrorHandling.ResourceNotFoundException;
import esi.project.ils.users.LibUserDetails;
import esi.project.ils.users.User;
import esi.project.ils.users.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class LoanRequestController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoanRequestService loanRequestService;

    @Autowired
    private UserService userService;

    @PostMapping("/request/loan")
    public ResponseEntity<LoanRequestDto> addLoanRequest(@Valid @RequestBody LoanRequestForm loanRequestForm,
                                                      @AuthenticationPrincipal LibUserDetails user) {
        Optional<User> createdBy = userService.getUserWithId(user.getId());

        if (createdBy.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        }

        Location newLocation = new Location();
        newLocation.setAddress(loanRequestForm.getAddress());
        newLocation.setCity(loanRequestForm.getCity());
        newLocation.setZipCode(loanRequestForm.getZipCode());

        LoanRequest newLoanRequest = new LoanRequest();
        newLoanRequest.setStartDate(loanRequestForm.getStartDate());
        newLoanRequest.setEndDate(loanRequestForm.getEndDate());
        newLoanRequest.setStatus("REQUESTED");
        newLoanRequest.setLocation(newLocation);
        newLoanRequest.setUser(createdBy.get());

        return new ResponseEntity<>(
                modelMapper.map(loanRequestService.addLoanRequest(newLoanRequest), LoanRequestDto.class),
                HttpStatus.CREATED);
    }

    @PutMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequestDto> updateLoanRequest(@PathVariable int request_id,
                                                         @RequestBody LoanRequest loanRequest) {
        Optional<LoanRequest> result = loanRequestService.updateLoanRequest(request_id, loanRequest);
        return result.map(
                request -> new ResponseEntity<>(modelMapper.map(request, LoanRequestDto.class), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id " + request_id));
    }

    @DeleteMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequest> deleteLoanRequest(@PathVariable int request_id) {
        loanRequestService.deleteLoanRequest(request_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequestDto> getLoanRequest(@PathVariable int request_id) {
        Optional<LoanRequest> result = loanRequestService.getLoanRequest(request_id);
        return result.map(
                loanRequest -> new ResponseEntity<>(modelMapper.map(loanRequest, LoanRequestDto.class), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id " + request_id));
    }

}
