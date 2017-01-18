/**=================================================================================================
 * System name : JFE Project
 * Version     : 1.0.0
 * Create date : 2017-01-18
 * Update date : 2017-01-18
 * Description : Create by DoLV
 =================================================================================================*/
package jasper.test.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <b>ObjectComparator</b>.<br>
 * The Object comparator<br>
 * ------------------------<br>
 * History:<br>
 * 2017-01-18 - DoLV - Initial<br>
 *
 * @author DoLV
 */
public class ObjectComparator implements Comparator<Object>{
	
	/** The sorters. */
	private List<SortUtils> sorters = new ArrayList<SortUtils>();
	
	/**
	 * Adds the key.<br>
	 * ------------------------<br>
	 * History:<br>
	 * 2017-01-18 - DoLV - Initial<br>
	 *
	 * @param nameMethod
	 *            is the name method
	 * @param isAsc
	 *            is the is asc
	 */
	public void addKey(String nameMethod, boolean isAsc) {
		SortUtils sorter = new SortUtils();
		sorter.methodName = nameMethod;
		sorter.isAsc = isAsc;
		sorters.add(sorter);
	}

	/**
	 * compare.<br>
	 * ------------------------<br>
	 * History:<br>
	 * 2017-01-18 - DoLV - Initial<br>
	 *
	 * @param nameMethod
	 *            is the name method
	 * @param isAsc
	 *            is the is asc
	 */
	@Override
	public final int compare(final Object a, final Object b) {
		for (SortUtils obj: sorters) {
			int result = 0;
			try {
				result = obj.compare(a, b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result != 0) return result;
		}
		return 0;
	}
}

