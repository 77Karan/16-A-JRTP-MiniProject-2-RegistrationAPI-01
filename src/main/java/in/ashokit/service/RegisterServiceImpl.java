package in.ashokit.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.User;
import in.ashokit.constants.AppConstants;
import in.ashokit.entities.CityEntity;
import in.ashokit.entities.CountryEntity;
import in.ashokit.entities.StateEntity;
import in.ashokit.entities.UserEntity;
import in.ashokit.props.AppProperties;
import in.ashokit.repositories.CityRepository;
import in.ashokit.repositories.CountryRepository;
import in.ashokit.repositories.StateRepository;
import in.ashokit.repositories.UserRepository;
import in.ashokit.utils.EmailUtils;
@Service
public class RegisterServiceImpl implements RegistrationService
{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CountryRepository countryReop;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private AppProperties appProps;
	
	@Override
	public boolean uniqueEmail(String email)
	{
		UserEntity entity = userRepo.findByUserEmail(email);
		if(entity !=null)
		{
			return false;
		}
		return true;
	}	
	
	@Override
	public Map<Integer, String> getCountries()
	{
		List<CountryEntity> countryEntites =countryReop.findAll();
		Map<Integer,String> countryMap = new HashMap<>();
		
		for(CountryEntity entity : countryEntites)
		{
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		
		return countryMap;
	}
	
	@Override
	public Map<Integer, String> getStates(Integer countryId)
	{
		List<StateEntity> stateEntities =stateRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap = new HashMap<>();
		
		for(StateEntity entity : stateEntities)
		{
			stateMap.put(entity.getStateId(), entity.getStateName());
		}
		
		return stateMap;
	}
	
	@Override
	public Map<Integer,String> getCities(Integer stateId)
	{
		List<CityEntity> entities = cityRepo.findByStateId(stateId);
		Map<Integer,String> cityMap = new HashMap<>();
		
		for(CityEntity entity : entities)
		{
			cityMap.put(entity.getCityId(), entity.getCityName());
		}
		return cityMap;
	}
	
	@Override
	public boolean registerUser(User user)
	{
		user.setUserPwd(generateTempPwd());
		user.setUserAccStatus(AppConstants.LOCKED);
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user,entity);
		UserEntity save =userRepo.save(entity);
		if(null != save.getUserId())
		{
			return sendRegEmail(user);
		}
		
		return false;
	}
	
	private String generateTempPwd()
	{
		String tempPwd = null;
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 6;
	    Random random = new Random();
	    
	    tempPwd = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
		
		return tempPwd;
	}
	
	private boolean sendRegEmail(User user)
	{
		boolean emailSent = false;
		
			Map<String,String> messages = appProps.getMessages();
			String subject =messages.get("regMailSubject");
			String bodyFileName = messages.get("regMailBodyTemplateFile");
			String body=readMailBody(bodyFileName, user);
			if(emailUtils.sendEmail(subject, body, user.getUserEmail()))
			{
				emailSent = true;
			}
		    return emailSent;
		
	}
	
	public String readMailBody(String fileName,User user)
	{
		String mailBody = null;
		StringBuffer buffer = new StringBuffer();
		Path path = Paths.get(fileName);	
		try (Stream<String> stream = Files.lines(path))
		{
			stream.forEach(line -> { buffer.append(line); });
			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.FNAME,user.getUserFirstName());
			mailBody = mailBody.replace(AppConstants.EMAIL,user.getUserEmail());
			mailBody = mailBody.replace(AppConstants.TEMPPWD,user.getUserPwd());

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return mailBody;
	}
	
	
	

}
