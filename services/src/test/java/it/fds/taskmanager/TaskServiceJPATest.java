package it.fds.taskmanager;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.fds.taskmanager.dto.TaskDTO;

/**
 * Basic test suite to test the service layer, it uses an in-memory H2 database.
 *
 * TODO Add more and meaningful tests! :)
 *
 * @author <a href="mailto:damiano@searchink.com">Damiano Giampaoli</a>
 * @since 10 Jan. 2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration
public class TaskServiceJPATest extends Assert{

    @Autowired
    TaskService taskService;

    @Test
    public void writeAndReadOnDB() {
        TaskDTO t = new TaskDTO();
        t.setTitle("Test task1");
        t.setStatus(TaskState.NEW.toString().toUpperCase());
        TaskDTO t1 = taskService.saveTask(t);
        TaskDTO tOut = taskService.findOne(t1.getUuid());
        assertEquals("Test task1", tOut.getTitle());
        List<TaskDTO> list = taskService.showList();
        assertEquals(1, list.size());
    }

    @Test
    public void saveTask_shouldSuccessfullySaveTheTask() {
        TaskDTO task = new TaskDTO();
        String title = "Test Save Task NEW";
        String desc = "Saving task";
        String priority = "3";
        Calendar currentDate = Calendar.getInstance();
        Calendar createdAt = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar dueDate = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar postponeddate = currentDate;
        Calendar updatedAt = createdAt;
        String status = TaskState.NEW.toString().toUpperCase();

        task.setTitle(title);
        task.setDescription(desc);
        task.setCreatedat(createdAt);
        task.setDuedate(dueDate);
        task.setPostponedat(postponeddate);
        task.setPriority(priority);
        task.setUpdatedat(updatedAt);
        task.setPostponedtime(null);
        task.setResolvedat(null);
        task.setStatus(status);

        TaskDTO tdtoSave = taskService.saveTask(task);

        assertTrue("Uuid is not set", tdtoSave.getUuid() != null);
        assertEquals("Title is not matching", title, tdtoSave.getTitle());
        assertEquals("Description is not matching", desc, tdtoSave.getDescription());
        assertEquals("Createdat is not matching", createdAt, tdtoSave.getCreatedat());
        assertEquals("Duedate is not matching", dueDate, tdtoSave.getDuedate());
        assertEquals("Postponedat is not matching", postponeddate, tdtoSave.getPostponedat());
        assertEquals("Priority is not matching", priority, tdtoSave.getPriority());
        assertEquals("Updatedat is not matching", updatedAt, tdtoSave.getUpdatedat());
        assertEquals("Postponedtime is not matching", null, tdtoSave.getPostponedtime());
        assertEquals("Resolvedat is not matching", null, tdtoSave.getResolvedat());
        assertEquals("Status is not matching", status, tdtoSave.getStatus());

        title = "Test Save Task RESOLVED";
        status = TaskState.RESOLVED.toString().toUpperCase();
        task.setTitle(title);
        task.setStatus(status);
        assertEquals("Status is not matching after save task with status RESOLVED", status, taskService.saveTask(task).getStatus());

        title = "Test Save Task POSTPONED";
        status = TaskState.POSTPONED.toString().toUpperCase();
        task.setTitle(title);
        task.setStatus(status);
        assertEquals("Status is not matching after save task with status RESOLVED", status, taskService.saveTask(task).getStatus());

        title = "Test Save Task RESOLVED";
        status = TaskState.RESOLVED.toString().toUpperCase();
        task.setTitle(title);
        task.setStatus(status);
        assertEquals("Status is not matching after save task with status RESOLVED", status, taskService.saveTask(task).getStatus());
    }

    @Test
    public void saveTask_shouldSaveTheTaskWithRandomUuid() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test Save Task NEW");
        UUID uuid = UUID.randomUUID();
        task.setUuid(UUID.randomUUID());
        TaskDTO tdtoSave = taskService.saveTask(task);
        assertTrue("Save should override the uuid passed in taskDto", tdtoSave.getUuid() != uuid);
    }

    @Test
    public void showList_shouldReturnEmptyList_whenNoTasks() {
        List<TaskDTO> list = taskService.showList();
        assertEquals(0, list.size());
    }

    @Test
    public void showList_shouldReturnEmptyList_whenThereAreOnlyPostponedTasks() {
        TaskDTO t = new TaskDTO();
        t.setTitle("Test showList");
        t.setStatus(TaskState.POSTPONED.toString().toUpperCase());
        taskService.saveTask(t);
        assertEquals(0, taskService.showList().size());
    }

    @Test
    public void showList_shouldReturnTheValuesProvidedDuringSave() {
        TaskDTO task = new TaskDTO();
        String title = "Test Save Task - NEW";
        String desc = "Saving task";
        String priority = "3";
        Calendar currentDate = Calendar.getInstance();
        Calendar createdAt = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar dueDate = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar postponeddate = currentDate;
        Calendar updatedAt = createdAt;
        String status = TaskState.NEW.toString().toUpperCase();

        task.setTitle(title);
        task.setDescription(desc);
        task.setCreatedat(createdAt);
        task.setDuedate(dueDate);
        task.setPostponedat(postponeddate);
        task.setPriority(priority);
        task.setUpdatedat(updatedAt);
        task.setPostponedtime(null);
        task.setResolvedat(null);
        task.setStatus(status);

        taskService.saveTask(task);
        List<TaskDTO> showlist = taskService.showList();

        assertEquals("showlist should return 1 record", 1, showlist.size());

        TaskDTO tdtoShowList = showlist.get(0);
        assertTrue("Uuid is null", tdtoShowList.getUuid() != null);
        assertEquals("Title is not matching", title, tdtoShowList.getTitle());
        assertEquals("Description is not matching", desc, tdtoShowList.getDescription());
        assertEquals("Createdat is not matching", createdAt, tdtoShowList.getCreatedat());
        assertEquals("Duedate is not matching", dueDate, tdtoShowList.getDuedate());
        assertEquals("Postponedat is not matching", postponeddate, tdtoShowList.getPostponedat());
        assertEquals("Priority is not matching", priority, tdtoShowList.getPriority());
        assertEquals("Updatedat is not matching", updatedAt, tdtoShowList.getUpdatedat());
        assertEquals("Postponedtime is not matching", null, tdtoShowList.getPostponedtime());
        assertEquals("Resolvedat is not matching", null, tdtoShowList.getResolvedat());
        assertEquals("Status is not matching", status, tdtoShowList.getStatus());
    }

    @Test
    public void showList_shouldReturnAllTasksExceptPostponed() {
        TaskDTO t = new TaskDTO();
        t.setTitle("Test New Task");
        t.setStatus(TaskState.NEW.toString().toUpperCase());
        taskService.saveTask(t);
        t.setTitle("Test Postponed Task");
        t.setStatus(TaskState.POSTPONED.toString().toUpperCase());
        taskService.saveTask(t);
        t.setTitle("Test Resolved Task");
        t.setStatus(TaskState.RESOLVED.toString().toUpperCase());
        taskService.saveTask(t);
        t.setTitle("Test Restored Task");
        t.setStatus(TaskState.RESTORED.toString().toUpperCase());
        taskService.saveTask(t);

        List<TaskDTO> list = taskService.showList();
        assertEquals(3, list.size());
    }

    @Test
    public void findOne_shouldReturnTheValuesProvidedDuringSave() {
        TaskDTO task = new TaskDTO();
        String title = "Test Save Task - NEW";
        String desc = "Saving task";
        String priority = "3";
        Calendar currentDate = Calendar.getInstance();
        Calendar createdAt = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar dueDate = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar postponeddate = currentDate;
        Calendar updatedAt = createdAt;
        String status = TaskState.NEW.toString().toUpperCase();

        task.setTitle(title);
        task.setDescription(desc);
        task.setCreatedat(createdAt);
        task.setDuedate(dueDate);
        task.setPostponedat(postponeddate);
        task.setPriority(priority);
        task.setUpdatedat(updatedAt);
        task.setPostponedtime(null);
        task.setResolvedat(null);
        task.setStatus(status);

        TaskDTO tdtoSave = taskService.saveTask(task);
        TaskDTO tdtoFindOne = taskService.findOne(tdtoSave.getUuid());

        assertEquals("Uuid is not matching", tdtoSave.getUuid(), tdtoFindOne.getUuid());
        assertEquals("Title is not matching", title, tdtoFindOne.getTitle());
        assertEquals("Description is not matching", desc, tdtoFindOne.getDescription());
        assertEquals("Createdat is not matching", createdAt, tdtoFindOne.getCreatedat());
        assertEquals("Duedate is not matching", dueDate, tdtoFindOne.getDuedate());
        assertEquals("Postponedat is not matching", postponeddate, tdtoFindOne.getPostponedat());
        assertEquals("Priority is not matching", priority, tdtoFindOne.getPriority());
        assertEquals("Updatedat is not matching", updatedAt, tdtoFindOne.getUpdatedat());
        assertEquals("Postponedtime is not matching", null, tdtoFindOne.getPostponedtime());
        assertEquals("Resolvedat is not matching", null, tdtoFindOne.getResolvedat());
        assertEquals("Status is not matching", status, tdtoFindOne.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findOne_shouldReturnIllegalArgumentExceptionForInvalidOrNotExistTaskId() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test findOne");
        TaskDTO tdtoSave = taskService.saveTask(task);
        UUID invalidTaskId = UUID.randomUUID();
        invalidTaskId = (tdtoSave.getUuid() != invalidTaskId) ? invalidTaskId : UUID.randomUUID();
        taskService.findOne(invalidTaskId);
    }

    @Test
    public void updateTask_shouldSaveTheValuesProvidedForUpdate() {
        TaskDTO task = new TaskDTO();
        task.setTitle("New Task");
        TaskDTO tdtoSave = taskService.saveTask(task);

        TaskDTO taskToUpdate = new TaskDTO();
        String title = "Test updateTask";
        String desc = "Test updateTask description";
        String priority = "3";
        Calendar currentDate = Calendar.getInstance();
        Calendar createdAt = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar dueDate = currentDate;
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        Calendar postponeddate = currentDate;
        Calendar updatedAt = createdAt;
        String status = TaskState.NEW.toString().toUpperCase();

        taskToUpdate.setUuid(tdtoSave.getUuid());
        taskToUpdate.setTitle(title);
        taskToUpdate.setDescription(desc);
        taskToUpdate.setCreatedat(createdAt);
        taskToUpdate.setDuedate(dueDate);
        taskToUpdate.setPostponedat(postponeddate);
        taskToUpdate.setPriority(priority);
        taskToUpdate.setPostponedtime(null);
        taskToUpdate.setResolvedat(null);
        taskToUpdate.setStatus(status);

        TaskDTO tdtoUpdate = taskService.updateTask(taskToUpdate);

        assertEquals("Uuid is not matching", tdtoSave.getUuid(), tdtoUpdate.getUuid());
        assertEquals("Title is not matching", title, tdtoUpdate.getTitle());
        assertEquals("Description is not matching", desc, tdtoUpdate.getDescription());
        assertEquals("Createdat is not matching", createdAt, tdtoUpdate.getCreatedat());
        assertEquals("Duedate is not matching", dueDate, tdtoUpdate.getDuedate());
        assertEquals("Postponedat is not matching", postponeddate, tdtoUpdate.getPostponedat());
        assertEquals("Priority is not matching", priority, tdtoUpdate.getPriority());
        assertEquals("Postponedtime is not matching", null, tdtoUpdate.getPostponedtime());
        assertEquals("Resolvedat is not matching", null, tdtoUpdate.getResolvedat());
        assertEquals("Status is not matching", status, tdtoUpdate.getStatus());
    }

    // Right now update with invalid taskId creates the new task
    @Test
    public void updateTask_shouldFailForInvalidOrNotExistTaskId() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test updateTask");
        TaskDTO tdtoSave = taskService.saveTask(task);
        UUID invalidTaskId = UUID.randomUUID();
        invalidTaskId = (tdtoSave.getUuid() != invalidTaskId) ? invalidTaskId : UUID.randomUUID();
        task.setUuid(invalidTaskId);
        TaskDTO inv = taskService.updateTask(task);
        assertEquals("UpdateTask on invalid uuid is creating the Task", inv.getUuid() != invalidTaskId);
    }

    @Test
    public void resolveTask_shouldUpdatetheResolveAttributes() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test resolveTask");
        TaskDTO tdtoSave = taskService.saveTask(task);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        assertTrue("ResolveTask should return true", taskService.resolveTask(tdtoSave.getUuid()));
        TaskDTO getTask = taskService.findOne(tdtoSave.getUuid());
        Long resolvedAt = getTask.getResolvedat().getTimeInMillis();
        assertTrue("resolveTask, either updated wrong value to resolvedAt or took more than 5 seconds", (resolvedAt - currentTime) < 5000);
        assertEquals("Status is not updated to RESOLVED", TaskState.RESOLVED.toString(), getTask.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void resolveTask_shouldFailForInvalidOrNotExistTaskId() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test resolveTask");
        TaskDTO tdtoSave = taskService.saveTask(task);
        UUID invalidTaskId = UUID.randomUUID();
        invalidTaskId = (tdtoSave.getUuid() != invalidTaskId) ? invalidTaskId : UUID.randomUUID();
        taskService.resolveTask(invalidTaskId);
    }

    @Test
    public void postponeTask_shouldPostponeTheTaskByMentionedMinutes() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test postponeTask");
        TaskDTO tdtoSave = taskService.saveTask(task);
        UUID taskId = tdtoSave.getUuid();
        Integer timeMinutePostpone = 1;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        assertTrue("postponeTask should return true", taskService.postponeTask(taskId, timeMinutePostpone));
        TaskDTO getTask = taskService.findOne(taskId);
        Long postponedAt = getTask.getPostponedat().getTimeInMillis();
        assertTrue("postponeTask, either updated wrong value to postponedAt or took more than 5 seconds", (postponedAt - (currentTime + timeMinutePostpone * 60000)) < 5000);
        assertEquals("Status is not updated to POSTPONED", TaskState.POSTPONED.toString(), getTask.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void postponeTask_shouldFailForInvalidTaskId() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test postponeTask");
        TaskDTO tdtoSave = taskService.saveTask(task);
        UUID invalidTaskId = UUID.randomUUID();
        invalidTaskId = (tdtoSave.getUuid() != invalidTaskId) ? invalidTaskId : UUID.randomUUID();
        taskService.postponeTask(invalidTaskId, 1);
    }

    @Test
    public void unmarkPostoned_shouldUnmarkThePostponeTask() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test unmarkPostoned");
        task.setStatus(TaskState.POSTPONED.toString());
        task.setPostponedat(Calendar.getInstance());
        TaskDTO tdtoSave = taskService.saveTask(task);
        taskService.unmarkPostoned();
        TaskDTO getTask = taskService.findOne(tdtoSave.getUuid());
        assertNull("unmarkPostponed failed to unset the postponedat value", getTask.getPostponedat());
        assertEquals("unmarkPostponed failed to mark the status as RESTORED", TaskState.RESTORED.toString(), getTask.getStatus());
    }

    @Test
    public void unmarkPostoned_shouldNotUnmarkthePostponedTaskOfPostponedatGreaterThanCurrentTime() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test unmarkPostoned");
        task.setStatus(TaskState.NEW.toString());
        TaskDTO tdtoSave = taskService.saveTask(task);
        taskService.postponeTask(tdtoSave.getUuid(), 1);
        taskService.unmarkPostoned();
        TaskDTO getTask = taskService.findOne(tdtoSave.getUuid());
        assertNotNull("unmarkPostponed shouldnot unset the postponedat value", getTask.getPostponedat());
        assertEquals("unmarkPostponed shouldnot mark the status as RESTORED", TaskState.POSTPONED.toString(), getTask.getStatus());
    }

    @Test
    public void unmarkPostoned_shouldNotUnmarktheTaskStatusOtherThanPostponed() {
        TaskDTO task = new TaskDTO();
        task.setTitle("Test unmarkPostoned");
        task.setPostponedat(Calendar.getInstance());
        task.setStatus(TaskState.NEW.toString());
        TaskDTO tdtoSave = taskService.saveTask(task);
        taskService.unmarkPostoned();
        TaskDTO getTask = taskService.findOne(tdtoSave.getUuid());
        assertNotNull("unmarkPostponed shouldnot unset the postponedat value", getTask.getPostponedat());
        assertEquals("unmarkPostponed shouldnot mark the status as RESTORED", tdtoSave.getStatus(), getTask.getStatus());
    }

    @EnableJpaRepositories
    @Configuration
    @SpringBootApplication
    public static class EndpointsMain{}
}
