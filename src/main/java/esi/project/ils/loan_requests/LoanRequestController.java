package esi.project.ils.loan_requests;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import esi.project.ils.ErrorHandling.ResourceNotFoundException;
import esi.project.ils.materials.Material;
import esi.project.ils.materials.MaterialService;
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

    @Autowired
    private MaterialService materialService;

    @PostMapping("/request/loan")
    public ResponseEntity<LoanRequestDto> addLoanRequest(@Valid @RequestBody LoanRequestForm loanRequestForm,
                                                      @AuthenticationPrincipal LibUserDetails user) {
        Location newLocation = new Location();
        newLocation.setAddress(loanRequestForm.getAddress());
        newLocation.setCity(loanRequestForm.getCity());
        newLocation.setZipCode(loanRequestForm.getZipCode());

        Optional<User> createdBy = userService.getUserWithId(user.getId());

        if (createdBy.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        }

        Optional<Material> material = materialService.getMaterialWithId(loanRequestForm.getMaterialId());

        if (material.isEmpty()) {
            throw new ResourceNotFoundException("Material not found with id " + loanRequestForm.getMaterialId());
        }

        LoanRequest newLoanRequest = new LoanRequest();
        newLoanRequest.setStartDate(loanRequestForm.getStartDate());
        newLoanRequest.setEndDate(loanRequestForm.getEndDate());
        newLoanRequest.setStatus("REQUESTED");
        newLoanRequest.setLocation(newLocation);
        newLoanRequest.setUser(createdBy.get());
        newLoanRequest.setMaterial(material.get());

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

    @GetMapping("/requests/loan/borrower/{user_id}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByBorrower(@PathVariable int user_id) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithUserId(user_id);

        if (loanRequests.isEmpty()) {
            throw new ResourceNotFoundException("No loan requests found for user with id " + user_id);
        }

        return new ResponseEntity<>(
                loanRequests.stream().map(r -> modelMapper.map(r, LoanRequestDto.class)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/requests/loan/material/{material_id}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByMaterial(@PathVariable int material_id) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithMaterialId(material_id);

        if (loanRequests.isEmpty()) {
            throw new ResourceNotFoundException("No loan requests found for material with id " + material_id);
        }

        return new ResponseEntity<>(
                loanRequests.stream().map(r -> modelMapper.map(r, LoanRequestDto.class)).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/requests/loan/status/{status}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByBorrower(@PathVariable String status) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithStatus(status);

        if (loanRequests.isEmpty()) {
            throw new ResourceNotFoundException("No loan requests found for status " + status);
        }

        return new ResponseEntity<>(
                loanRequests.stream().map(r -> modelMapper.map(r, LoanRequestDto.class)).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
