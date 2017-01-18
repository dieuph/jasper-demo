package jasper.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private Integer id;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
}
