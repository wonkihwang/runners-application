package dev.runners.runners.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(UserRestClient.class)
class UserRestClientTest {

  @Autowired
  MockRestServiceServer server;

  @Autowired
  UserRestClient client;

  @Autowired
  ObjectMapper objectMapper;

  // @Test
  // void shouldFindAllUsers() throws JsonProcessingException {
    
  // }

}