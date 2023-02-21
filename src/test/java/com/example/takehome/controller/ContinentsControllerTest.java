package com.example.takehome.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.takehome.dto.ContinentDetails;
import com.example.takehome.dto.ContinentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Unit tests for the Countries controller.
 */
@SpringBootTest
public class ContinentsControllerTest
{
  protected final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @Autowired
  private WebApplicationContext webApplicationContext;

  protected MockMvc mockMvc;

  @BeforeEach
  public void init() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void testNorthAmerica() throws Exception {
    MvcResult result = this.mockMvc
        .perform(get("/api/continents")
            .param("countries", String.join(",", List.of("US", "CA"))))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    ContinentResponse response = mapper.readValue(result.getResponse().getContentAsString(), ContinentResponse.class);
    ContinentDetails continentDetails = response.getContinent().get(0);
    assertEquals(1, response.getContinent().size());

    assertEquals("North America", continentDetails.getName());
    assertEquals(2, continentDetails.getCountries().size());
    assertEquals(39, continentDetails.getOtherCountries().size());
  }

  @Test
  public void testNorthAmericaAndEU() throws Exception {
    MvcResult result = this.mockMvc
        .perform(get("/api/continents")
            .param("countries", String.join(",", List.of("US", "CA", "UA"))))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    ContinentResponse response = mapper.readValue(result.getResponse().getContentAsString(), ContinentResponse.class);

    assertEquals(2, response.getContinent().size());
  }

  @Test
  public void testFake() throws Exception {
    MvcResult result = this.mockMvc
        .perform(get("/api/continents")
            .param("countries", String.join(",", List.of("FAKE"))))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    ContinentResponse response = mapper.readValue(result.getResponse().getContentAsString(), ContinentResponse.class);

    assertEquals(0, response.getContinent().size());
  }
}
