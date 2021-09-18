package com.donlaiq.api.controller;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.donlaiq.api.controllers.AnalyzeController;
import com.donlaiq.api.exceptions.RestApiErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class AnalyzeControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private MessageSource msgSource;
	@InjectMocks
	private AnalyzeController controller;
	
	@BeforeEach
	public void setup()
	{
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestApiErrorHandler(msgSource)).build();
		
		final Instant now = Instant.now();
	}
	
	
	@Test
	@DisplayName("test that it doesn't work with the GET method")
	public void testGetErrorMessage() throws Exception 
	{
		MockHttpServletResponse response  = mockMvc.perform(get("/analyze").
													contentType(MediaType.APPLICATION_JSON)
													.content("{\"text\":\"some string\"}")
													.accept(MediaType.APPLICATION_JSON))
													.andDo(print())
													.andReturn().getResponse();
		
		assertThat(response.getStatus()).isNotEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	@DisplayName("test right use of curl")
	public void testGetOkStatus() throws Exception 
	{
		MockHttpServletResponse response  = mockMvc.perform(post("/analyze").
													contentType(MediaType.APPLICATION_JSON)
													.content("{\"text\":\"some string\"}")
													.accept(MediaType.APPLICATION_JSON))
													.andDo(print())
													.andExpect(jsonPath("$.textLength.withSpaces").value("11"))
													.andExpect(jsonPath("$.textLength.withoutSpaces").value("10"))
													.andExpect(jsonPath("$.wordCount").value("2"))
													.andExpect(jsonPath("$.characterCount[7].s").value("2"))
													.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	@Test
	@DisplayName("test original exercise")
	public void testOriginalExerciseIsWorkingOK() throws Exception 
	{
		MockHttpServletResponse response  = mockMvc.perform(post("/analyze").
													contentType(MediaType.APPLICATION_JSON)
													.content("{\"text\":\"hello 2 times  \"}")
													.accept(MediaType.APPLICATION_JSON))
													.andDo(print())
													.andExpect(jsonPath("$.textLength.withSpaces").value("15"))
													.andExpect(jsonPath("$.textLength.withoutSpaces").value("11"))
													.andExpect(jsonPath("$.wordCount").value("3"))
													.andExpect(jsonPath("$.characterCount[7].t").value("1"))
													.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
