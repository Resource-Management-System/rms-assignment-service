package callum.project.uni.rms.assignment;

import callum.project.uni.rms.assignment.model.request.AssignmentCreateReq;
import callum.project.uni.rms.assignment.model.target.TargetAssignment;
import callum.project.uni.rms.assignment.service.AssignmentRetrieveUserService;
import callum.project.uni.rms.assignment.service.AssignmentService;
import callum.project.uni.rms.parent.exception.InternalServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.ROLE_ID;
import static callum.project.uni.rms.assignment.helper.SourceAssignmentBuilder.USER_ID;
import static callum.project.uni.rms.assignment.helper.TargetAssignmentBuilder.*;
import static callum.project.uni.rms.assignment.helper.TargetAssignmentBuilder.buildAssignmentCreateReq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleAssignmentController.class)
class RoleAssignmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AssignmentService service;

    @MockBean
    private AssignmentRetrieveUserService retrieveUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAssignmentByRole_happyPath() throws Exception {
        TargetAssignment ass = buildTargetAssignment();
        when(service.retrieveAssignmentByRoleId(eq(ROLE_ID)))
                .thenReturn(ass);

        ResultActions resultActions = this.mvc.perform(get("/role/assignment/" + ROLE_ID))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        TargetAssignment assignment = objectMapper.readValue(contentAsString, TargetAssignment.class);
        assertEquals(ROLE_ID, assignment.getRoleId());
        assertEquals(TARGET_ROLE_END_DATE, assignment.getEndDate());
        assertEquals(TARGET_ROLE_START_DATE, assignment.getStartDate());
        assertEquals(USER_ID, assignment.getUserId());
    }

    @Test
    void getAssignmentByRole_internalError() throws Exception {
        when(service.retrieveAssignmentByRoleId(eq(ROLE_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(get("/role/assignment/" + ROLE_ID))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void postNewAssignment_internalServiceException() throws Exception {
        doThrow(new InternalServiceException("ex", new HibernateException("ex"))).when(service)
                .addNewAssignment(eq(buildAssignmentCreateReq()));

        this.mvc.perform(
                post("/role/assignment/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 2, \"roleId\": 1, \"startDate\": \"2021-03-01\", \"endDate\": \"2021-03-01\"}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    void postNewAssignment_happyPath() throws Exception {

        AssignmentCreateReq ass = buildAssignmentCreateReq();

        this.mvc.perform(post("/role/assignment/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 2, \"roleId\": 1, \"startDate\": \"2021-03-01\", \"endDate\": \"2021-03-01\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(service, times(1)).addNewAssignment(eq(ass));
    }
}