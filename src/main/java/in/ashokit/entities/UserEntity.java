package in.ashokit.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="User_DTLS")
@Data
public class UserEntity 
{
	@Id
	@SequenceGenerator(name="Hibernet_seq", sequenceName="HIBERNATE_SEQUENCE",allocationSize=1)
	@GeneratedValue(generator="Hibernet_seq", strategy=GenerationType.SEQUENCE)
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "USER_FIRST_NAME")
	private String userFirstName;
	
	@Column(name = "USER_LAST_NAME")
	private String userLastName;
	
	
	@Column(name = "USER_EMAIL",unique=true)
	private String userEmail;
	
	@Column(name = "USER_PHNO")
	private Long userPhno;
	
	@Column(name = "USER_DOB")
	private LocalDate userDob;
	
	@Column(name = "USER_GENDER")
	private String userGender;
	
	@Column(name = "USER_COUNTRY")
	private Integer userCountry;
	
	@Column(name = "USER_STATE")
	private Integer userState;
	
	@Column(name = "USER_CITY")
	private Integer userCity;
	
	@Column(name = "USER_PWD")
	private String userPwd;
	
	@Column(name = "USER_ACC_STATUS")
	private String userAccStatus;
	
	@Column(name = "CREATED_DATE",updatable=false)
	@CreationTimestamp
	private LocalDate createdDate;
	
	@Column(name = "UPDATE_DATE",insertable=false)
	@UpdateTimestamp
	private LocalDate updatedDate;

}
