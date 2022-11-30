package in.ashokit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="COUNTRY_DTLS")
@Data
public class CountryEntity 
{
	@Column(name="COUNTRY_ID")
	@Id
	private Integer countryId;
	
	@Column(name="COUNTRY_NAME")
	private String countryName;

}
