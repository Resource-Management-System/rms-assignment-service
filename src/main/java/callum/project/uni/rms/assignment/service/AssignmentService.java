package callum.project.uni.rms.assignment.service;

import callum.project.uni.rms.assignment.mapper.AssignmentMapper;
import callum.project.uni.rms.assignment.service.repository.AssignmentRepository;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static callum.project.uni.rms.parent.mapper.MapperUtils.convertLocalDateToSqlDate;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public TargetAssignment addNewAssignment(AssignmentCreateReq req) {
        try {
            Assignment assignment = assignmentMapper.mapRequestToSource(req);

            return assignmentMapper.mapSourceToTarget(assignmentRepository.save(assignment));
        } catch (HibernateException e) {
            throw new InternalServiceException("Issue adding new role", e);
        }
    }

    public void updateEndDate(Long userId, Long roleId, LocalDate newEndDate) {
        try {
            assignmentRepository.updateAssignmentEnded(
                    userId, roleId, convertLocalDateToSqlDate(newEndDate));
        } catch (HibernateException e) {
            throw new InternalServiceException("Issue adding new role", e);
        }
    }

    public TargetAssignment retrieveAssignmentByRoleId(Long roleId) {
        try {

            Optional<Assignment> assignment = assignmentRepository.findByRoleId(roleId);

            return assignmentMapper.mapSourceToTarget(assignment.orElseThrow(() ->
                    new NotFoundException("Not found")));
        } catch (HibernateException e) {
            throw new InternalServiceException("Issue adding new role", e);
        }
    }
}
