package esi.project.ils.loan_requests;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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
import org.springframework.security.access.AccessDeniedException;
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

    public List<LoanRequestDto> getAvailableLoanRequests(List<LoanRequest> loanRequests, User user) {
        if (loanRequests.isEmpty()) {
            throw new ResourceNotFoundException("No loan requests found");
        }

        List<LoanRequest> filteredRequests = loanRequests.stream().filter(
                        r -> Objects.equals(r.getMaterial().getHomeLibrary(), user.getHomeLibrary())
                                || Objects.equals(r.getUser().getHomeLibrary(), user.getHomeLibrary()))
                .collect(Collectors.toList());

        if (filteredRequests.isEmpty()) {
            throw new ResourceNotFoundException("No loan requests found for your library");
        }

        return filteredRequests.stream().map(r -> modelMapper.map(r, LoanRequestDto.class))
                .collect(Collectors.toList());
    }

    public void checkAuthorization(int the_id, LoanRequest loanRequest, LibUserDetails user, String type) {
        Optional<User> createdBy = userService.getUserWithId(user.getId());

        if (createdBy.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        }

        String role = createdBy.get().getRole();

        if (Objects.equals(type, "GET_BY_BORROWER")
                && !Objects.equals(role, "LIBRARIAN")
                && !(the_id == user.getId())) {
            throw new AccessDeniedException("You are not allowed to view this loan request");
        }

        if (Objects.equals(type, "GET_BY_BORROWER") && Objects.equals(role, "LIBRARIAN")) {
            return;
        }

        Optional<LoanRequest> oldLoanRequest = loanRequestService.getLoanRequest(the_id);

        if (oldLoanRequest.isEmpty()) {
            throw new ResourceNotFoundException("Loan request not found with id " + the_id);
        }

        if (Objects.equals(type, "GET")
                && Objects.equals(role, "LIBRARIAN")
                && Objects.equals(oldLoanRequest.get().getUser().getHomeLibrary(), user.getHomeLibrary())) {
            return;
        }

        if ((Objects.equals(type, "UPDATE")
                || Objects.equals(type, "DELETE")
                || Objects.equals(type, "GET"))
                && Objects.equals(role, "LIBRARIAN")
                && !Objects.equals(oldLoanRequest.get().getMaterial().getHomeLibrary(), user.getHomeLibrary())) {
            throw new AccessDeniedException("You are not allowed to update this loan request");
        }

        if (Objects.equals(type, "UPDATE")
                && !Objects.equals(role, "LIBRARIAN")
                && !(oldLoanRequest.get().getUser().getId() == user.getId()
                && oldLoanRequest.get().getStatus().equals("REQUESTED")
                && loanRequest.getStatus().equals("CANCELLED"))) {
            throw new AccessDeniedException("You are not allowed to update this loan request");
        }
    }

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
                                                            @RequestBody LoanRequest loanRequest,
                                                            @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, loanRequest, user, "UPDATE");
        Optional<LoanRequest> newLoanRequest = loanRequestService.updateLoanRequest(request_id, loanRequest);
        return new ResponseEntity<>(modelMapper.map(newLoanRequest, LoanRequestDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequest> deleteLoanRequest(@PathVariable int request_id,
                                                         @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, null, user, "UPDATE");
        loanRequestService.deleteLoanRequest(request_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/request/loan/{request_id}")
    public ResponseEntity<LoanRequestDto> getLoanRequest(@PathVariable int request_id,
                                                         @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, null, user, "GET");
        Optional<LoanRequest> result = loanRequestService.getLoanRequest(request_id);
        return result.map(
                loanRequest -> new ResponseEntity<>(modelMapper.map(loanRequest, LoanRequestDto.class), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Loan request not found with id " + request_id));
    }

    @GetMapping("/requests/loan")
    public ResponseEntity<List<LoanRequestDto>> getAllLoanRequests(@AuthenticationPrincipal LibUserDetails user) {
        List<LoanRequest> loanRequests = loanRequestService.getAllLoanRequests();
        List<LoanRequestDto> loanRequestDtoList = getAvailableLoanRequests(loanRequests, user);
        return new ResponseEntity<>(loanRequestDtoList,HttpStatus.OK);
    }

    @GetMapping("/requests/loan/borrower/{user_id}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByBorrower(@PathVariable int user_id,
                                                                          @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(user_id, null, user, "GET_BY_BORROWER");
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithUserId(user_id);
        List<LoanRequestDto> loanRequestDtoList = getAvailableLoanRequests(loanRequests, user);
        return new ResponseEntity<>(loanRequestDtoList,HttpStatus.OK);
    }

    @GetMapping("/requests/loan/material/{material_id}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByMaterial(@PathVariable int material_id,
                                                                          @AuthenticationPrincipal LibUserDetails user) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithMaterialId(material_id);
        List<LoanRequestDto> loanRequestDtoList = getAvailableLoanRequests(loanRequests, user);
        return new ResponseEntity<>(loanRequestDtoList,HttpStatus.OK);
    }

    @GetMapping("/requests/loan/status/{status}")
    public ResponseEntity<List<LoanRequestDto>> getLoanRequestsByBorrower(@PathVariable String status,
                                                                          @AuthenticationPrincipal LibUserDetails user) {
        List<LoanRequest> loanRequests = loanRequestService.getLoanRequestsWithStatus(status);
        List<LoanRequestDto> loanRequestDtoList = getAvailableLoanRequests(loanRequests, user);
        return new ResponseEntity<>(loanRequestDtoList,HttpStatus.OK);
    }
}
