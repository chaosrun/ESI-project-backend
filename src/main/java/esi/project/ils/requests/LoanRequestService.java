package esi.project.ils.requests;

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

    public void deleteLoanRequest(int id) {
        loanRequestRepository.deleteById(id);
    }

}
