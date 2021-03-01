package callum.project.uni.rms.assignment.service;

import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.service.repository.AssignmentRepository;
import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AssignmentRetrieveUserService {

    private final AssignmentRepository assignmentRepository;

    public long retrieveActiveUserIdByRoleId(long roleId) {

        try {

            Optional<Assignment> assignment = assignmentRepository.findByRoleIdAndEndDateIsNull(roleId);

            return assignment.orElseThrow(() ->
                    new NotFoundException("Not found")).getUserId();

        } catch (HibernateException e) {
            throw new InternalServiceException("Issue adding new role", e);
        }
    }
}
