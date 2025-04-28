package lending.splatform.process.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.dmn.api.DmnDecisionService;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("loanOnboardingService")
@RequiredArgsConstructor
public class LoanOnboardingService {

    private final DmnDecisionService dmnDecisionService;

    public void allocate(DelegateExecution execution) {
        String customerName = (String) execution.getVariable("customerName");
        log.info("[LoanOnboarding] Allocating user for customer: {}", customerName);
    }

    public void evaluate(DelegateExecution execution) {
        Integer amount = (Integer) execution.getVariable("amount");
        log.info("[LoanOnboardingService] Evaluating DMN decision for amount: {}", amount);
        Map<String, Object> inputVariables = new HashMap<>();
        inputVariables.put("amount", amount);


        Map<String, Object> decisionResult = dmnDecisionService.createExecuteDecisionBuilder()
                .decisionKey("approvalDecision")
                .variables(inputVariables)
                .executeWithSingleResult();

        Boolean approved = false;
        if (decisionResult == null) {
            log.warn("[DecisionEvaluationService] No decision result for amount = {}", amount);
            execution.setVariable("approved", approved);
        } else {
            approved = Boolean.valueOf(decisionResult.get("approved").toString());
            log.info("[DecisionEvaluationService] amount = {}, approved = {}", amount, approved);
//          Boolean approved = amount != null && amount < 1000;
            execution.setVariable("approved", approved);

            log.info("[LoanOnboardingService] Set 'approved' = {}", approved);
        }

    }
}
