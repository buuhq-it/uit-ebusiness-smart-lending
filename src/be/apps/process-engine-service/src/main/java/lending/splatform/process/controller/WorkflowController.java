package lending.splatform.process.controller;

import lending.splatform.process.constants.LoanOnboardTaskConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
//import org.flowable.task.service.TaskService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class WorkflowController {

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    // API 1: workflow/start?processDefinitionKey=simpleLoanOnboardingProcess
    // 1. Start Process
    @GetMapping("/start")
    public String startProcess(@RequestParam String processDefinitionKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        log.info("[Workflow] Started process key={} with instanceId={}", processDefinitionKey, instance.getId());
        return "Started processDefinitionKey = " + processDefinitionKey + ", processInstanceId = " + instance.getId();
    }

    // 2. Get Tasks by Process Instance
    @GetMapping("/tasks")
    public List<Map<String, String>> getTasks(@RequestParam String processInstanceId) {
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        log.info("[Workflow] Found {} tasks for processInstanceId={}", tasks.size(), processInstanceId);

        return tasks.stream()
                .map(task -> {
                    Map<String, String> m = new HashMap<>();
                    m.put("id", task.getId());
                    m.put("name", task.getName());
                    m.put("taskDefinitionKey", task.getTaskDefinitionKey());
                    return m;
                })
                .collect(Collectors.toList());
    }

    // 3. Complete Data Acquisition Task
    @PostMapping("/complete-data-acquisition")
    public String completeDataAcquisition(@RequestParam String processInstanceId,
                                          @RequestParam String customerName,
                                          @RequestParam Integer amount) {

        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey(LoanOnboardTaskConstants.DATA_ACQUISITION)
                .singleResult();

        if (task == null) {
            log.warn("[Workflow] No task '{}' found for processInstanceId={}", LoanOnboardTaskConstants.DATA_ACQUISITION, processInstanceId);
            return "No Data Acquisition task found for processInstanceId = " + processInstanceId;
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);

        taskService.complete(task.getId(), variables);
        log.info("[Workflow] Completed Data Acquisition task for processInstanceId={}", processInstanceId);

        return "Completed Data Acquisition for processInstanceId = " + processInstanceId;
    }

    // 4. Complete Generic Task
    @PostMapping("/complete")
    public String completeTask(@RequestParam String taskId) {
        taskService.complete(taskId);
        log.info("[Workflow] Completed task with id={}", taskId);
        return "Completed task id = " + taskId;
    }


}
