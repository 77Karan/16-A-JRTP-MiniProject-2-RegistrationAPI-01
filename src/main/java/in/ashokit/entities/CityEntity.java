package in.ashokit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CITY_DTLS")
@Data
public class CityEntity 
{
	@Column(name="CITY_ID")
	@Id
	private Integer cityId;
	
	@Column(name="CITY_NAME")
	private String cityName;
	
	@Column(name="STATE_ID")
	private Integer stateId;
	

}
