package callum.project.uni.rms.assignment.service;

import callum.project.uni.rms.assignment.mapper.AssignmentMapper;
import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import callum.project.uni.rms.assignment.service.repository.AssignmentRepository;
import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.*;
import static callum.project.uni.rms.assignment.helper.TargetAssignmentBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    private AssignmentService assignmentService;

    private AssignmentRepository assignmentRepository;


    @BeforeEach
    public void setup(){
        assignmentRepository = mock(AssignmentRepository.class);
        assignmentService = new AssignmentService(assignmentRepository,
                new AssignmentMapper());
    }


    @Test
    void retrieveAssignmentByRoleId_happyPath(){
        when(assignmentRepository.findByRoleId(eq(ROLE_ID)))
                .thenReturn(Optional.of(buildSourceAssignment()));
        TargetAssignment res = assignmentService.retrieveAssignmentByRoleId(ROLE_ID);
        assertEquals(TARGET_ROLE_END_DATE, res.getEndDate());
        assertEquals(TARGET_ROLE_START_DATE, res.getEndDate());
        assertEquals(USER_ID, res.getUserId());
        assertEquals(ROLE_ID, res.getRoleId());
    }

    @Test
    void retrieveAssignmentByRoleId_notFound(){
        when(assignmentRepository.findByRoleId(eq(ROLE_ID)))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> assignmentService.retrieveAssignmentByRoleId(ROLE_ID));
    }

    @Test
    void retrieveAssignmentByRoleId_internalServiceException(){
        when(assignmentRepository.findByRoleId(eq(ROLE_ID)))
                .thenThrow(HibernateException.class);
        assertThrows(InternalServiceException.class,
                () -> assignmentService.retrieveAssignmentByRoleId(ROLE_ID));
    }

    @Test
    void addNewAssignment_happyPath(){

        AssignmentCreateReq assignmentCreateReq = buildAssignmentCreateReq();
        Assignment assignment = buildSourceAssignment();
        when(assignmentRepository.save(eq(assignment))).thenReturn(assignment);


        TargetAssignment res = assignmentService.addNewAssignment(assignmentCreateReq);
        assertEquals(ROLE_ID, res.getRoleId());
        assertEquals(TARGET_ROLE_START_DATE, res.getStartDate());
        assertEquals(TARGET_ROLE_END_DATE, res.getEndDate());
        assertEquals(USER_ID, res.getUserId());
        verify(assignmentRepository, times(1)).save(eq(assignment));
    }

    @Test
    void addNewAssignment_hibernateException(){

        AssignmentCreateReq assignmentCreateReq = buildAssignmentCreateReq();
        Assignment assignment = buildSourceAssignment();
        when(assignmentRepository.save(eq(assignment))).thenThrow(HibernateException.class);
        assertThrows(InternalServiceException.class,
                () -> assignmentService.addNewAssignment(assignmentCreateReq));
        verify(assignmentRepository, times(1)).save(eq(assignment));
    }


    @Test
    void updateEndDate_happyPath(){

        LocalDate newEndDate = LocalDate.of(2023, 1, 1);

        assignmentService.updateEndDate(USER_ID, ROLE_ID, newEndDate);

        verify(assignmentRepository, times(1))
                .updateAssignmentEnded(eq(USER_ID),
                        eq(ROLE_ID),
                        eq(Date.valueOf(newEndDate)));
    }

    @Test
    void updateEndDate_hibernateException(){
        LocalDate newEndDate = LocalDate.of(2023, 1, 1);

        doThrow(new HibernateException("ex")).when(assignmentRepository)
                .updateAssignmentEnded(eq(USER_ID),
                        eq(ROLE_ID),
                        eq(Date.valueOf(newEndDate)));

        assertThrows(InternalServiceException.class,
                () -> assignmentService.updateEndDate(USER_ID, ROLE_ID, newEndDate));
    }
}