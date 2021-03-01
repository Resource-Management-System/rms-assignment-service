package callum.project.uni.rms.assignment.model.source;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssignmentId implements Serializable {

    private Long userId;
    private Long roleId;
}
