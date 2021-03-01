package callum.project.uni.rms.assignment.mapper;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.*;
import static callum.project.uni.rms.assignment.helper.TargetAssignmentBuilder.*;
import static callum.project.uni.rms.parent.mapper.MapperUtils.convertLocalDateToSqlDate;
import static callum.project.uni.rms.parent.mapper.MapperUtils.convertSqlDateToLocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentMapperTest {

    private AssignmentMapper assignmentMapper;

    @BeforeEach
    public void setup() {
        assignmentMapper = new AssignmentMapper();
    }

    @Test
    void mapSourceToTarget() {
        TargetAssignment as = assignmentMapper.mapSourceToTarget(buildSourceAssignment());

        assertEquals(TARGET_ROLE_START_DATE, as.getStartDate());
        assertEquals(TARGET_ROLE_END_DATE, as.getEndDate());
        assertEquals(ROLE_ID, as.getRoleId());
        assertEquals(USER_ID, as.getUserId());
    }

    @Test
    void mapTargetToSource() {
        Assignment as = assignmentMapper.mapTargetToSource(buildTargetAssignment());

        assertEquals(SOURCE_ROLE_START_DATE, as.getStartDate());
        assertEquals(SOURCE_ROLE_END_DATE, as.getEndDate());
        assertEquals(ROLE_ID, as.getRoleId());
        assertEquals(USER_ID, as.getUserId());
    }

    @Test
    void mapRequestToSource() {
        Assignment as = assignmentMapper.mapRequestToSource(buildAssignmentCreateReq());
        assertEquals(SOURCE_ROLE_START_DATE, as.getStartDate());
        assertEquals(SOURCE_ROLE_END_DATE, as.getEndDate());
        assertEquals(ROLE_ID, as.getRoleId());
        assertEquals(USER_ID, as.getUserId());
    }
}