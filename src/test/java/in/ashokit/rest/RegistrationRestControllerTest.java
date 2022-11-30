package in.ashokit.rest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.bindings.User;
import in.ashokit.constants.AppConstants;
import in.ashokit.service.RegistrationService;

@WebMvcTest(value=RegistrationRestController.class)
public class RegistrationRestControllerTest
{
	@MockBean
	private RegistrationService serviceMock;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void checkUniqueEmailTestUnique() throws Exception
	{
		when(serviceMock.uniqueEmail("ashokit@gmail.com")).thenReturn(true);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/emailCheck/ashokit@gmail.com");
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse response =mvcResult.getResponse();
		String actualOutput = response.getContentAsString();
		assertEquals(AppConstants.UNIQUE, actualOutput);
		
		
	}
	
	@Test
	public void checkUniqueEmailTestDuplicate() throws Exception
	{
		when(serviceMock.uniqueEmail("abc123abc@gmail.com")).thenReturn(false);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/emailCheck/abc@gmail.com");
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		MockHttpServletResponse response =mvcResult.getResponse();
		String actualOutput = response.getContentAsString();
		assertEquals(AppConstants.DUPLICATE, actualOutput);
		
		
	}
	
	@Test
	public void getCountriesTest() throws Exception
	{
		Map<Integer, String> countriesMap = new HashMap<Integer, String>();
		countriesMap.put(1, "India");
		countriesMap.put(2, "England");

		
		when(serviceMock.getCountries()).thenReturn(countriesMap);
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/countries");
		MvcResult mvcResult =mockMvc.perform(reqBuilder).andReturn();
		Integer actualStatus =mvcResult.getResponse().getStatus();
		assertEquals(200, actualStatus);

	}
	
	
	@Test
	public void getStatesTest() throws Exception
	{
		Map<Integer, String> statesMap = new HashMap<Integer, String>();
		statesMap.put(1, "Karnataka");
		statesMap.put(1, "AndraPradesh");
		when(serviceMock.getStates(1)).thenReturn(statesMap);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/states/1");
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		Integer actualStatus =mvcResult.getResponse().getStatus();
		assertEquals(200, actualStatus);

	}
	
	@Test
	public void getCitiesTest() throws Exception
	{
		Map<Integer, String> citiesMap = new HashMap<Integer, String>();
		citiesMap.put(1, "Bangalore");
		citiesMap.put(2, "Hydrabad");
		when(serviceMock.getCities(1)).thenReturn(citiesMap);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/cities/1");
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		Integer actualStatus =mvcResult.getResponse().getStatus();
		assertEquals(200, actualStatus);

	}
	
	@Test
	public void saveUserTestSucess() throws Exception
	{
		User user = new User();
		
		when(serviceMock.registerUser(user)).thenReturn(true);
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/saveuser").contentType("application/json").content(userJson);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		String resBody =mvcResult.getResponse().getContentAsString();
		assertEquals(AppConstants.SUCESS, resBody);

	}
	
	@Test
	public void saveUserTestFailure() throws Exception
	{
		User user1 = new User();
		
		when(serviceMock.registerUser(user1)).thenReturn(false);
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user1);
		
		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/saveuser").contentType("application/json").content(userJson);
		MvcResult mvcResult = mockMvc.perform(reqBuilder).andReturn();
		String resBody =mvcResult.getResponse().getContentAsString();
		assertEquals(AppConstants.FAILURE, resBody);

	}
	
	
	
	
	

}


