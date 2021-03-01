package callum.project.uni.rms.assignment.service;

import callum.project.uni.rms.assignment.service.repository.AssignmentRepository;
import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignmentRetrieveUserServiceTest {

    private AssignmentRetrieveUserService retrieveUserService;

    private AssignmentRepository assignmentRepository;

    @BeforeEach
    public void setup(){
        assignmentRepository = mock(AssignmentRepository.class);
        retrieveUserService = new AssignmentRetrieveUserService(assignmentRepository);
    }

    @Test
    void retrieveActiveUserIdByRoleId_happyPath(){
        when(assignmentRepository.findByRoleIdAndEndDateIsNull(eq(ROLE_ID)))
                .thenReturn(Optional.of(buildSourceAssignment()));

        long userId = retrieveUserService.retrieveActiveUserIdByRoleId(ROLE_ID);
        assertEquals(USER_ID, userId);
    }

    @Test
    void retrieveActiveUserIdByRoleId_notFound(){
        when(assignmentRepository.findByRoleIdAndEndDateIsNull(eq(ROLE_ID)))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> retrieveUserService.retrieveActiveUserIdByRoleId(ROLE_ID));
    }

    @Test
    void retrieveActiveUserIdByRoleId_hibernateException(){
        when(assignmentRepository.findByRoleIdAndEndDateIsNull(eq(ROLE_ID)))
                .thenThrow(HibernateException.class);
        assertThrows(InternalServiceException.class,
                () -> retrieveUserService.retrieveActiveUserIdByRoleId(ROLE_ID));
    }
}