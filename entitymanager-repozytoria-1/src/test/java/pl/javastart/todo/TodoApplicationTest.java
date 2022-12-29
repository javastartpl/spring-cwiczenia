package pl.javastart.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class TodoApplicationTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldPersistSingleTask() {
        Task task = new Task("sample task", "sample description", 10);
        taskRepository.save(task);
        Optional<Task> optionalTask = taskRepository.findById(1L);
        assertTrue(optionalTask.isPresent());
        Task taskFromDb = optionalTask.get();
        assertEquals("sample task", taskFromDb.getTitle());
        assertEquals("sample description", taskFromDb.getDescription());
        assertEquals(10, taskFromDb.getPriority());
    }
}
