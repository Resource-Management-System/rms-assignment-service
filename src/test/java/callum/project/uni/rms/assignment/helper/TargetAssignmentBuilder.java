package callum.project.uni.rms.assignment.helper;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;

import java.time.LocalDate;

import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.ROLE_ID;
import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.USER_ID;

public class TargetAssignmentBuilder {


    public static final LocalDate TARGET_ROLE_START_DATE = LocalDate.now();
    public static final LocalDate TARGET_ROLE_END_DATE = LocalDate.now();

    public static TargetAssignment buildTargetAssignment(){
        return TargetAssignment.builder()
                .roleId(ROLE_ID)
                .endDate(TARGET_ROLE_END_DATE)
                .startDate(TARGET_ROLE_START_DATE)
                .userId(USER_ID)
                .build();
    }

    public static AssignmentCreateReq buildAssignmentCreateReq(){
        return AssignmentCreateReq.builder()
                .roleId(ROLE_ID)
                .userId(USER_ID)
                .startDate(TARGET_ROLE_END_DATE)
                .endDate(TARGET_ROLE_START_DATE)
                .build();
    }
}
