package in.ashokit.bindings;
import java.time.LocalDate;

import lombok.Data;

@Data
public class User 
{
    private Integer userId;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private Long userPhno;
	private LocalDate userDob;
	private String userGender;
	private Integer userCountry;
	private Integer userState;
	private Integer userCity;
	private String userPwd;
	private String userAccStatus;
	private LocalDate createdDate;
	private LocalDate updatedDate;

}
