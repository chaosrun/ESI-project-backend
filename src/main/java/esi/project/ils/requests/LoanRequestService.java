package esi.project.ils.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestService {
    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public LoanRequest addLoanRequest(LoanRequest loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }
}
