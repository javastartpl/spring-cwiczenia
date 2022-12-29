package pl.javastart.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.javastart.todo.dto.NewTaskDto;
import pl.javastart.todo.exception.TaskNotStartedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TodoApplicationTest {
    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldFindTaskInfo() {
        NewTaskDto task = new NewTaskDto("sample task", "sample description", 10);
        taskService.saveTask(task);
        Optional<String> optionalInfo = taskService.getTaskInfo(1L);
        assertTrue(optionalInfo.isPresent());
    }

    @Test
    void shouldNotCompleteTask() {
        NewTaskDto task = new NewTaskDto("sample task", "sample description", 10);
        taskService.saveTask(task);
        assertThrowsExactly(TaskNotStartedException.class, () -> taskService.completeTask(1L));
    }
}
