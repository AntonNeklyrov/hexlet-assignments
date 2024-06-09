package exercise.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private static Task actual;

    @BeforeAll
    public static void init() {
        actual = new Task();
        actual.setTitle("testTitle");
        actual.setDescription("testDescription");
    }

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //given                                                     r
        taskRepository.save(actual);

        //when
        var request = mockMvc.perform(get("/tasks/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThatJson(om.writeValueAsString(actual)).isEqualTo(request.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //when
        var request = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(actual)))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        Task expected = om.readValue(request.getResponse().getContentAsString(), Task.class);

        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //given
        taskRepository.save(actual);
        var updatedTask = new Task();
        updatedTask.setDescription("newDescription");
        updatedTask.setTitle("newTitle");

        //when
        var request = mockMvc.perform(put("/tasks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Task expected = om.readValue(request.getResponse().getContentAsString(), Task.class);
        assertThat(updatedTask.getDescription()).isEqualTo(expected.getDescription());
        assertThat(updatedTask.getTitle()).isEqualTo(expected.getTitle());

    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //given
        taskRepository.save(actual);

        //when
        var deleteRequest = mockMvc.perform(delete("/tasks/{id}", 1))
                .andExpect(status().isOk());

        var selectRequest = mockMvc.perform(get("/tasks/{id}", 1))
                .andExpect(status().isNotFound());
    }


}
