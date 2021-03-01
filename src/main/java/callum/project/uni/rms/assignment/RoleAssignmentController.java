package callum.project.uni.rms.assignment;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import callum.project.uni.rms.assignment.service.AssignmentRetrieveUserService;
import callum.project.uni.rms.assignment.service.AssignmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
class RoleAssignmentController {

    private final AssignmentService assignmentService;
    private final AssignmentRetrieveUserService retrieveUserService;

    @PostMapping(value = "/role/assignment")
    @ResponseStatus(HttpStatus.CREATED)
    public void postNewAssignment(@RequestBody @NonNull AssignmentCreateReq req) {
        
        log.debug("Adding a new assignment for the role and user");
        assignmentService.addNewAssignment(req);
    }


    @GetMapping(value = "/role/assignment/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public TargetAssignment getAssignmentByRole(@PathVariable("roleId") @NonNull Long roleId) {
        return assignmentService.retrieveAssignmentByRoleId(roleId);
    }
}
