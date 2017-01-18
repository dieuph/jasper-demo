package jasper.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
	private String userName;
	private String lastAccessed;
	private String isActive;
	private String points;
}
