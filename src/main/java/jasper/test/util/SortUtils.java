/**=================================================================================================
 * System name : JFE Project
 * Version     : 1.0.0
 * Create date : 2016-06-14
 * Update date : 2016-06-14
 * Description : Create by DoLV
 =================================================================================================*/
package jasper.test.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>SortUtils</b>.<br>
 * The Sort utils<br>
 * ------------------------<br>
 * History:<br>
 * 2017-01-01 - DoLV - Initial<br>
 *
 * @author DoLV
 */
public class SortUtils {
	
	/** The is asc. */
	public boolean isAsc;
	
	/** The method name. */
	public String methodName;
	
	/** The Constant NEGATIVE_UNIT. */
	public static final int NEGATIVE_UNIT = -1;
	
	/** The Constant POSITIVE_UNIT. */
	public static final int POSITIVE_UNIT = 1;

	/** The Constant DATATYPE_STRING. */
	public static final String DATATYPE_STRING = "java.lang.String";
	
	/** The Constant DATATYPE_INTEGER. */
	public static final String DATATYPE_INTEGER = "java.lang.Integer";
	
	/** The Constant DATATYPE_BIGDECIMAL. */
	public static final String DATATYPE_BIGDECIMAL = "java.math.BigDecimal";
	
	/** The Constant DATATYPE_LONG. */
	public static final String DATATYPE_LONG = "java.lang.Long";
	
	/** The Constant DATATYPE_DOUBLE. */
	public static final String DATATYPE_DOUBLE = "java.lang.Double";
	
	/** The Constant DATATYPE_DATE. */
	public static final String DATATYPE_DATE = "java.util.Date";
	
	public static final String DATATYPE_CHAR = "java.lang.Character";
	/**
	 * Compare.<br>
	 * ------------------------<br>
	 * History:<br>
	 * 2017-01-01 - DoLV - Initial<br>
	 *
	 * @param a
	 *            is the a
	 * @param b
	 *            is the b
	 * @return the int object
	 * @throws Exception
	 *             the exception description
	 */
	public final int compare(final Object a, final Object b) throws Exception {
		int result = 0;
		Class<?> noparam[] = {};
		// Get value from method of object A
		Method methodA =  a.getClass().getMethod(methodName, noparam);
		Object valueA = methodA.invoke(a);
		// Get value from method of object B
		Method methodB =  b.getClass().getMethod(methodName, noparam);
		Object valueB = methodB.invoke(b);
		// Consider data type to compare
		String dataType = valueA.getClass().getName();
		switch (dataType) {
			case DATATYPE_STRING:
				result = ((String) valueA).compareTo((String) valueB);
				break;
			case DATATYPE_INTEGER:
				result = ((Integer) valueA).compareTo((Integer) valueB);
				break;
			case DATATYPE_LONG:
				result = ((Long) valueA).compareTo((Long) valueB);
				break;
			case DATATYPE_DOUBLE:
				result = ((Double) valueA).compareTo((Double) valueB);
				break;
			case DATATYPE_BIGDECIMAL:
				result = ((BigDecimal) valueA).compareTo((BigDecimal) valueB);
				break;
			case DATATYPE_DATE:
				result = ((Date) valueA).compareTo((Date) valueB);
				break;
			case DATATYPE_CHAR:
				result = ((Character) valueA).compareTo((Character) valueB);
				break;
			default:
				if (valueA instanceof byte[]) {
					result = valueA.toString().compareTo(valueB.toString());
					break;
				}
		}
		if (!isAsc) result *= NEGATIVE_UNIT;
		return result;
	}
}

