package esi.project.ils.requests;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestService {
    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public LoanRequest addLoanRequest(LoanRequest loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }

    public Optional<LoanRequest> updateLoanRequest(int id, LoanRequest updatedLoanRequest) {
        return loanRequestRepository.findById(id).map(loanRequest -> {
            loanRequest.setStatus(updatedLoanRequest.getStatus());
            return loanRequest;
        });
    }

    public Optional<LoanRequest> getLoanRequest(int id) {
        return loanRequestRepository.findById(id);
    }

    public void deleteLoanRequest(int id) {
        loanRequestRepository.deleteById(id);
    }

    public List<LoanRequest> getLoanRequestsWithUserId(int userId) {
        return loanRequestRepository.findByUserId(userId);
    }

    public List<LoanRequest> getLoanRequestsWithMaterialId(int materialId) {
        return loanRequestRepository.findByMaterialId(materialId);
    }

    public List<LoanRequest> getLoanRequestsWithStatus(String status) {
        return loanRequestRepository.findByStatus(status);
    }

}
