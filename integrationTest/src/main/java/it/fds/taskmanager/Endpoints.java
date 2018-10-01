package it.fds.taskmanager;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import it.fds.dto.ListDto;
import utils.ReadConfig;

import java.util.*;

public class Endpoints {

    static {
        RestAssured.baseURI = ReadConfig.getProperty("hostname");
        RestAssured.port = Integer.valueOf(ReadConfig.getProperty("port"));
    }

    public HashMap<String, ListDto> getList() {
        List<ListDto> list = Arrays.asList(RestAssured
                .given().contentType(ContentType.JSON)
                .get(ReadConfig.getProperty("endpoint_list"))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract().as(ListDto[].class));

        HashMap<String, ListDto> map = new HashMap<String, ListDto>();

        for (ListDto dtoItem : list) {
            map.put(dtoItem.getUuid(), dtoItem);
        }
        return map;
    }
}
