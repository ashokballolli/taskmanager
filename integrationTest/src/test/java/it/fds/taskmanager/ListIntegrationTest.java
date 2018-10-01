package it.fds.taskmanager;

import it.fds.dto.ListDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class ListIntegrationTest extends Assert {
    Endpoints endPoint = new Endpoints();
    DataSource dataSource = new DataSource();

//    @BeforeClass
//    public void health() {
//        // TO Do
//        // Perform healthCheck of the application before running the test suite, if success then run the tests.
//    }

    @Test
    public void verifyTheRecordsFromListAPIAreMatchingWithDatabaseRecord() {

        HashMap<String, ListDto> fromList = endPoint.getList();
        List<ListDto> fromDatabase = dataSource.getAllRecordsExceptPostponed();

        for (ListDto db : fromDatabase) {
            String uuid = db.getUuid();
            ListDto api = fromList.get(uuid);
            assertEquals("uuid is not matching for taskId " + uuid, db.getUuid(), api.getUuid());
            assertEquals("createdat is not matching for taskId " + uuid, db.getCreatedat(), (api.getCreatedat()));
            assertEquals("updatedat is not matching for taskId " + uuid, db.getUpdatedat(), api.getUpdatedat());
            assertEquals("duedate is not matching for taskId " + uuid, db.getDuedate(), api.getDuedate());
            assertEquals("resolvedat is not matching for taskId " + uuid, db.getResolvedat(), api.getResolvedat());
            assertEquals("postponedat is not matching for taskId " + uuid, db.getPostponedat(), api.getPostponedat());
            assertEquals("postponedtime is not matching for taskId " + uuid, db.getPostponedtime(), api.getPostponedtime());
            assertEquals("title is not matching for taskId " + uuid, db.getTitle(), api.getTitle());
            assertEquals("description is not matching for taskId " + uuid, db.getDescription(), api.getDescription());
            assertEquals("priority is not matching for taskId " + uuid, db.getPriority(), api.getPriority());
            assertEquals("status is not matching for taskId " + uuid, db.getStatus(), api.getStatus());
        }
    }
}

