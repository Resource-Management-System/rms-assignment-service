package callum.project.uni.rms.assignment.mapper;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import org.springframework.stereotype.Service;

import static callum.project.uni.rms.parent.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.parent.mapper.MapperUtils.convertSqlDateToLocalDate;

@Service
public class AssignmentMapper implements Mapper<TargetAssignment, Assignment, AssignmentCreateReq> {
    
    public TargetAssignment mapSourceToTarget(Assignment source) {
        return new TargetAssignment(source.getUserId(), 
                source.getRoleId(),
                convertSqlDateToLocalDate(source.getStartDate()),
                convertSqlDateToLocalDate(source.getEndDate()));
    }

    public Assignment mapTargetToSource(TargetAssignment target) {
        return Assignment.builder()
                .startDate(convertLocalDateToSqlDate(target.getStartDate()))
                .endDate(convertLocalDateToSqlDate(target.getEndDate()))
                .roleId(target.getRoleId())
                .userId(target.getUserId())
                .build();
    }

    public Assignment mapRequestToSource(AssignmentCreateReq request) {
        return Assignment.builder()
                .endDate(convertLocalDateToSqlDate(request.getEndDate()))
                .startDate(convertLocalDateToSqlDate(request.getStartDate()))
                .roleId(request.getRoleId())
                .userId(request.getUserId())
                .build();
    }
}
