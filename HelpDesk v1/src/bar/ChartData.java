package bar;

import java.util.Calendar;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {

	public static CategoryModel getModel(){
		int year = Calendar.getInstance().get(Calendar.YEAR) + 1900;
		CategoryModel model = new SimpleCategoryModel();
		model.setValue(Integer.toString(year - 2), "Q1", new Integer(17));
		model.setValue(Integer.toString(year - 2), "Q2", new Integer(36));
		model.setValue(Integer.toString(year - 2), "Q3", new Integer(39));
		model.setValue(Integer.toString(year - 2), "Q4", new Integer(49));
		model.setValue(Integer.toString(year - 1), "Q1", new Integer(20));
		model.setValue(Integer.toString(year - 1), "Q2", new Integer(35));
		model.setValue(Integer.toString(year - 1), "Q3", new Integer(40));
		model.setValue(Integer.toString(year - 1), "Q4", new Integer(55));
		model.setValue(Integer.toString(year), "Q1", new Integer(40));
		model.setValue(Integer.toString(year), "Q2", new Integer(60));
		model.setValue(Integer.toString(year), "Q3", new Integer(70));
		model.setValue(Integer.toString(year), "Q4", new Integer(90));
		return model;
	}
}
