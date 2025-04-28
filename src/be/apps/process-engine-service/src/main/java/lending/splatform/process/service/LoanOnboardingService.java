package lending.splatform.process.service;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Slf4j
@Service("loanOnboardingService")
public class LoanOnboardingService {
    public void allocate(DelegateExecution execution) {
        String customerName = (String) execution.getVariable("customerName");
        log.info("[LoanOnboarding] Allocating user for customer: {}", customerName);
    }

    public void evaluate(DelegateExecution execution) {
        Integer amount = (Integer) execution.getVariable("amount");
        log.info("[LoanOnboardingService] Evaluating DMN decision for amount: {}", amount);

        Boolean approved = amount != null && amount < 1000;
        execution.setVariable("approved", approved);

        log.info("[LoanOnboardingService] Set 'approved' = {}", approved);
    }
}
