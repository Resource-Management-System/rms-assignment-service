package callum.project.uni.rms.assignment.model.target;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TargetAssignment extends AbstractServiceResponse {

    private Long userId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
