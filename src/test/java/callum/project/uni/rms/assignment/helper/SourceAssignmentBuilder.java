package callum.project.uni.rms.assignment.helper;

import callum.project.uni.rms.assignment.model.source.Assignment;

import java.sql.Date;
import java.time.LocalDate;

public class SourceAssignmentBuilder {

    public static final Long ROLE_ID = 1L;
    public static final Date SOURCE_ROLE_START_DATE = Date.valueOf(LocalDate.now());
    public static final Date SOURCE_ROLE_END_DATE = Date.valueOf(LocalDate.now());
    public static final Long USER_ID = 2L;

    public static Assignment buildSourceAssignment(){
        return Assignment.builder()
                .userId(USER_ID)
                .roleId(ROLE_ID)
                .startDate(SOURCE_ROLE_START_DATE)
                .endDate(SOURCE_ROLE_END_DATE)
                .build();
    }
}
