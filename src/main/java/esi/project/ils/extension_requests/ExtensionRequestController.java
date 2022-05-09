package esi.project.ils.extension_requests;

import esi.project.ils.ErrorHandling.ResourceNotFoundException;
import esi.project.ils.materials.Material;
import esi.project.ils.materials.MaterialService;
import esi.project.ils.reservations.Reservation;
import esi.project.ils.reservations.ReservationService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class ExtensionRequestController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExtensionRequestService extensionRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ReservationService reservationService;

    public void checkAuthorization(int the_id, ExtensionRequest extensionRequest, LibUserDetails user, String type) {
        Optional<User> createdBy = userService.getUserWithId(user.getId());

        if (createdBy.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        }

        String role = createdBy.get().getRole();

        if (Objects.equals(type, "GET_BY_BORROWER")
                && !Objects.equals(role, "LIBRARIAN")
                && !(the_id == user.getId())) {
            throw new AccessDeniedException("You are not allowed to view this extension request");
        }

        if (Objects.equals(type, "GET_BY_BORROWER") && Objects.equals(role, "LIBRARIAN")) {
            return;
        }

        Optional<ExtensionRequest> oldExtensionRequest = extensionRequestService.getExtensionRequest(the_id);

        if (oldExtensionRequest.isEmpty()) {
            throw new ResourceNotFoundException("Extension request not found with id " + the_id);
        }

        if (Objects.equals(type, "GET")
                && Objects.equals(role, "LIBRARIAN")
                && Objects.equals(oldExtensionRequest.get().getUser().getHomeLibrary(), user.getHomeLibrary())) {
            return;
        }

        if ((Objects.equals(type, "UPDATE")
                || Objects.equals(type, "DELETE")
                || Objects.equals(type, "GET"))
                && Objects.equals(role, "LIBRARIAN")
                && !Objects.equals(oldExtensionRequest.get().getMaterial().getHomeLibrary(), user.getHomeLibrary())) {
            throw new AccessDeniedException("You are not allowed to update this loan request");
        }

        if (Objects.equals(type, "UPDATE")
                && !Objects.equals(role, "LIBRARIAN")
                && !(oldExtensionRequest.get().getUser().getId() == user.getId()
                && oldExtensionRequest.get().getStatus().equals("REQUESTED")
                && extensionRequest.getStatus().equals("CANCELLED"))) {
            throw new AccessDeniedException("You are not allowed to update this extension request");
        }
    }

    public List<ExtensionRequestDto> getAvailableExtensionRequests(List<ExtensionRequest> extensionRequests, User user) {
        if (extensionRequests.isEmpty()) {
            throw new ResourceNotFoundException("No extension requests found");
        }

        List<ExtensionRequest> filteredRequests = extensionRequests.stream().filter(
                        r -> Objects.equals(r.getMaterial().getHomeLibrary(), user.getHomeLibrary())
                                || Objects.equals(r.getUser().getHomeLibrary(), user.getHomeLibrary()))
                .collect(Collectors.toList());

        if (filteredRequests.isEmpty()) {
            throw new ResourceNotFoundException("No extension requests found for your library");
        }

        return filteredRequests.stream().map(r -> modelMapper.map(r, ExtensionRequestDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/request/extension")
    public ResponseEntity<ExtensionRequestDto> addExtensionRequest(@Valid @RequestBody ExtensionRequestForm extensionRequestForm,
                                                                   @AuthenticationPrincipal LibUserDetails user) {
        Optional<User> createdBy = userService.getUserWithId(user.getId());

        if (createdBy.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + user.getId());
        }

        Optional<Material> material = materialService.getMaterialWithId(extensionRequestForm.getMaterialId());

        if (material.isEmpty()) {
            throw new ResourceNotFoundException("Material not found with id " + extensionRequestForm.getMaterialId());
        }

        List<Reservation> materialReservations = reservationService.getReservationByMaterialId(material.get().getId());

        if (materialReservations.isEmpty()) {
            throw new ResourceNotFoundException("Material reservation not found with id " + extensionRequestForm.getMaterialId());
        }

        Reservation oldReservation = materialReservations.get(materialReservations.size() - 1);

        if (oldReservation.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("You are not allowed to request an extension for this material");
        }

        if (oldReservation.getStatus().equals("CANCELLED")
                || oldReservation.getStatus().equals("RETURNED")
                || oldReservation.getStatus().equals("EXPIRED")) {
            throw new ResourceNotFoundException("Material reservation cannot be extended");
        }

        ExtensionRequest newExtensionRequest = new ExtensionRequest();
        newExtensionRequest.setStartDate(extensionRequestForm.getStartDate());
        newExtensionRequest.setEndDate(extensionRequestForm.getEndDate());
        newExtensionRequest.setStatus("REQUESTED");
        newExtensionRequest.setUser(createdBy.get());
        newExtensionRequest.setMaterial(material.get());

        return new ResponseEntity<>(
                modelMapper.map(extensionRequestService.addExtensionRequest(newExtensionRequest), ExtensionRequestDto.class),
                HttpStatus.CREATED);
    }

    @PutMapping("/request/extension/{request_id}")
    public ResponseEntity<ExtensionRequestDto> updateExtensionRequest(@PathVariable int request_id,
                                                                      @RequestBody ExtensionRequest extensionRequest,
                                                                      @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, extensionRequest, user, "UPDATE");
        Optional<ExtensionRequest> newExtensionRequest = extensionRequestService.updateExtensionRequest(request_id, extensionRequest);
        return new ResponseEntity<>(modelMapper.map(newExtensionRequest, ExtensionRequestDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/request/extension/{request_id}")
    public ResponseEntity<ExtensionRequest> deleteExtensionRequest(@PathVariable int request_id,
                                                                   @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, null, user, "UPDATE");
        extensionRequestService.deleteExtensionRequest(request_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/request/extension/{request_id}")
    public ResponseEntity<ExtensionRequestDto> getExtensionRequest(@PathVariable int request_id,
                                                                   @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(request_id, null, user, "GET");
        Optional<ExtensionRequest> result = extensionRequestService.getExtensionRequest(request_id);
        return result.map(
                extensionRequest -> new ResponseEntity<>(modelMapper.map(extensionRequest, ExtensionRequestDto.class), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Extension request not found with id " + request_id));
    }

    @GetMapping("/requests/extension")
    public ResponseEntity<List<ExtensionRequestDto>> getAllExtensionRequests(@AuthenticationPrincipal LibUserDetails user) {
        List<ExtensionRequest> extensionRequests = extensionRequestService.getAllExtensionRequests();
        List<ExtensionRequestDto> extensionRequestDtoList = getAvailableExtensionRequests(extensionRequests, user);
        return new ResponseEntity<>(extensionRequestDtoList, HttpStatus.OK);
    }

    @GetMapping("/requests/extension/borrower/{user_id}")
    public ResponseEntity<List<ExtensionRequestDto>> getExtensionRequestsByBorrower(@PathVariable int user_id,
                                                                                    @AuthenticationPrincipal LibUserDetails user) {
        checkAuthorization(user_id, null, user, "GET_BY_BORROWER");
        List<ExtensionRequest> extensionRequests = extensionRequestService.getExtensionRequestsWithUserId(user_id);
        List<ExtensionRequestDto> extensionRequestDtoList = getAvailableExtensionRequests(extensionRequests, user);
        return new ResponseEntity<>(extensionRequestDtoList, HttpStatus.OK);
    }

    @GetMapping("/requests/extension/material/{material_id}")
    public ResponseEntity<List<ExtensionRequestDto>> getExtensionRequestsByMaterial(@PathVariable int material_id,
                                                                                    @AuthenticationPrincipal LibUserDetails user) {
        List<ExtensionRequest> extensionRequests = extensionRequestService.getExtensionRequestsWithMaterialId(material_id);
        List<ExtensionRequestDto> extensionRequestDtoList = getAvailableExtensionRequests(extensionRequests, user);
        return new ResponseEntity<>(extensionRequestDtoList, HttpStatus.OK);
    }

    @GetMapping("/requests/extension/status/{status}")
    public ResponseEntity<List<ExtensionRequestDto>> getExtensionRequestsByBorrower(@PathVariable String status,
                                                                                    @AuthenticationPrincipal LibUserDetails user) {
        List<ExtensionRequest> extensionRequests = extensionRequestService.getExtensionRequestsWithStatus(status);
        List<ExtensionRequestDto> extensionRequestDtoList = getAvailableExtensionRequests(extensionRequests, user);
        return new ResponseEntity<>(extensionRequestDtoList, HttpStatus.OK);
    }
}
