package callum.project.uni.rms.assignment.service.repository;

import callum.project.uni.rms.assignment.model.source.Assignment;
import callum.project.uni.rms.assignment.model.source.AssignmentId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface AssignmentRepository extends CrudRepository<Assignment, AssignmentId> {


    Optional<Assignment> findByRoleIdAndEndDateIsNull(Long roleId);
    Optional<Assignment> findByRoleId(Long roleId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update Assignment a set a.end_date =:newEndDate where a.role_id =:roleId and a.user_id =:userId", nativeQuery = true)
    void updateAssignmentEnded(@Param("userId") Long userId,
                               @Param("roleId") Long roleId,
                               @Param("newEndDate") Date newEndDate);
}
