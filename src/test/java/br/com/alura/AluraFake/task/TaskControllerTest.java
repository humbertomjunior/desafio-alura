package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.CourseTestUtils;
import br.com.alura.AluraFake.task.request.MultipleChoiceTask;
import br.com.alura.AluraFake.task.request.OpenTextTask;
import br.com.alura.AluraFake.task.request.SingleChoiceTask;
import br.com.alura.AluraFake.util.exception.DuplicateStatementException;
import br.com.alura.AluraFake.util.exception.NotFoundException;
import br.com.alura.AluraFake.util.exception.TaskOrderOutOfSequenceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void createMultipleChoiceTask__should_create_new_multiple_choice_task() throws Exception {

        final var taskStatement = "Novo exercício multiple choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 3);

        final var newTask = TaskTestUtils.createMultipleChoiceTask(1, taskStatement, course, options);

        final var response = TaskTestUtils.createMockTestResponse(newTask);

        when(taskService.createNewTask(any(MultipleChoiceTask.class), any(TaskType.class))).thenReturn(response);

        mockMvc.perform(post("/task/new/multiplechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.statement").value(taskStatement))
                .andExpect(jsonPath("$.order").value(1))
                .andExpect(jsonPath("$.type").value(TaskType.MULTIPLE_CHOICE.name()))
                .andExpect(jsonPath("$.options[0].id").value(1))
                .andExpect(jsonPath("$.options[0].option").value("Opção 0"))
                .andExpect(jsonPath("$.options[0].isCorrect").value(true))
                .andExpect(jsonPath("$.options[1].id").value(2))
                .andExpect(jsonPath("$.options[1].option").value("Opção 1"))
                .andExpect(jsonPath("$.options[1].isCorrect").value(true))
                .andExpect(jsonPath("$.options[2].id").value(3))
                .andExpect(jsonPath("$.options[2].option").value("Opção 2"))
                .andExpect(jsonPath("$.options[2].isCorrect").value(true))
                .andExpect(jsonPath("$.options[3].id").value(4))
                .andExpect(jsonPath("$.options[3].option").value("Opção 3"))
                .andExpect(jsonPath("$.options[3].isCorrect").value(false))
                .andExpect(jsonPath("$.options[4].id").value(5))
                .andExpect(jsonPath("$.options[4].option").value("Opção 4"))
                .andExpect(jsonPath("$.options[4].isCorrect").value(false));

    }

    @Test
    void createMultipleChoiceTask__should_create_new_single_choice_task() throws Exception {

        final var taskStatement = "Novo exercício single choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 1);

        final var newTask = TaskTestUtils.createSingleChoiceTask(1, taskStatement, course, options);

        final var response = TaskTestUtils.createMockTestResponse(newTask);

        when(taskService.createNewTask(any(SingleChoiceTask.class), any(TaskType.class))).thenReturn(response);

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.statement").value(taskStatement))
                .andExpect(jsonPath("$.order").value(1))
                .andExpect(jsonPath("$.type").value(TaskType.SINGLE_CHOICE.name()))
                .andExpect(jsonPath("$.options[0].id").value(1))
                .andExpect(jsonPath("$.options[0].option").value("Opção 0"))
                .andExpect(jsonPath("$.options[0].isCorrect").value(true))
                .andExpect(jsonPath("$.options[1].id").value(2))
                .andExpect(jsonPath("$.options[1].option").value("Opção 1"))
                .andExpect(jsonPath("$.options[1].isCorrect").value(false))
                .andExpect(jsonPath("$.options[2].id").value(3))
                .andExpect(jsonPath("$.options[2].option").value("Opção 2"))
                .andExpect(jsonPath("$.options[2].isCorrect").value(false))
                .andExpect(jsonPath("$.options[3].id").value(4))
                .andExpect(jsonPath("$.options[3].option").value("Opção 3"))
                .andExpect(jsonPath("$.options[3].isCorrect").value(false))
                .andExpect(jsonPath("$.options[4].id").value(5))
                .andExpect(jsonPath("$.options[4].option").value("Opção 4"))
                .andExpect(jsonPath("$.options[4].isCorrect").value(false));
    }

    @Test
    void createOpenTextTask__should_create_new_open_text_task() throws Exception {

        final var taskStatement = "Novo exercício open text";

        final var course = CourseTestUtils.createMockedCourse();

        final var newTask = TaskTestUtils.createOpenTextTask(1, taskStatement, course);

        final var response = TaskTestUtils.createMockTestResponse(newTask);

        when(taskService.createNewTask(any(OpenTextTask.class), any(TaskType.class))).thenReturn(response);

        mockMvc.perform(post("/task/new/opentext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.statement").value(taskStatement))
                .andExpect(jsonPath("$.order").value(1))
                .andExpect(jsonPath("$.type").value(TaskType.OPEN_TEXT.name()));
    }

    @Test
    void createMultipleChoiceTask__should_return_bad_request_number_of_correct_answers() throws Exception {

        final var taskStatement = "Novo exercício multiple choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 1);

        final var newTask = TaskTestUtils.createMultipleChoiceTask(1, taskStatement, course, options);

        mockMvc.perform(post("/task/new/multiplechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message").value("A atividade deve ter duas ou mais alternativas corretas."))
                .andExpect(jsonPath("$[0].field").value("options"));
    }

    @Test
    void createSingleChoiceTask__should_return_bad_request_number_of_correct_answers() throws Exception {

        final var taskStatement = "Novo exercício single choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 2);

        final var newTask = TaskTestUtils.createSingleChoiceTask(1, taskStatement, course, options);

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message").value("A atividade deve ter uma única alternativa correta."))
                .andExpect(jsonPath("$[0].field").value("options"));
    }

    @Test
    void createMultipleChoiceTask__should_return_bad_request_not_repeatable_options() throws Exception {

        final var taskStatement = "Novo exercício multiple choice";
        final var repeatedOptionName = "Opção 10";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(3, 1);

        // Forcing adding 2 other options with the same (repeated) option statements:
        options.add(OptionTestUtils.createOptionRequest(repeatedOptionName, true));
        options.add(OptionTestUtils.createOptionRequest(repeatedOptionName, false));

        final var newTask = TaskTestUtils.createMultipleChoiceTask(1, taskStatement, course, options);

        mockMvc.perform(post("/task/new/multiplechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message").value("As alternativas não podem ser iguais entre si."))
                .andExpect(jsonPath("$[0].field").value("options"));
    }

    @Test
    void createSingleChoiceTask__should_return_bad_request_no_statement_as_option() throws Exception {

        final var taskStatement = "Novo exercício multiple choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(4, 1);

        // Forcing adding another option with the same (repeated) option statement as the course statement:
        options.add(OptionTestUtils.createOptionRequest(taskStatement, false));

        final var newTask = TaskTestUtils.createSingleChoiceTask(1, taskStatement, course, options);

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].message").value("As alternativas não podem ser iguais ao enunciado da atividade."))
                .andExpect(jsonPath("$[0].field").value("options.option"));
    }

    @Test
    void createSingleChoiceTask__should_return_not_found() throws Exception {

        final var taskStatement = "Novo exercício multiple choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 1);

        final var newTask = TaskTestUtils.createMultipleChoiceTask(1, taskStatement, course, options);

        doThrow(new NotFoundException("Não foi encontrado um curso com status BUILDING e id: %s".formatted(1))).when(taskService).createNewTask(any(SingleChoiceTask.class), any(TaskType.class));

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Não foi encontrado um curso com status BUILDING e id: 1"));
    }

    @Test
    void createSingleChoiceTask__should_return_bad_request_when_statements_are_duplicated() throws Exception {

        final var taskStatement = "Novo exercício single choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 1);

        final var newTask = TaskTestUtils.createSingleChoiceTask(1, taskStatement, course, options);

        doThrow(new DuplicateStatementException("O curso não pode ter duas questões com o mesmo enunciado.", "statement")).when(taskService).createNewTask(any(SingleChoiceTask.class), any(TaskType.class));

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O curso não pode ter duas questões com o mesmo enunciado."))
                .andExpect(jsonPath("$.field").value("statement"));
    }

    @Test
    void createSingleChoiceTask__should_return_bad_request_when_task_order_is_out_of_sequence() throws Exception {

        final var taskStatement = "Novo exercício single choice";

        final var course = CourseTestUtils.createMockedCourse();

        final var options = OptionTestUtils.createMockedOptionRequests(5, 1);

        final var newTask = TaskTestUtils.createSingleChoiceTask(1, taskStatement, course, options);

        doThrow(new TaskOrderOutOfSequenceException("A order da atividade não pode ser adicionada pois deixará espaço(s) vazio(s).", "task.order")).when(taskService).createNewTask(any(SingleChoiceTask.class), any(TaskType.class));

        mockMvc.perform(post("/task/new/singlechoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A order da atividade não pode ser adicionada pois deixará espaço(s) vazio(s)."))
                .andExpect(jsonPath("$.field").value("task.order"));
    }




}