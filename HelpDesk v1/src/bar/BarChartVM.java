package bar;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;

public class BarChartVM {

	BarChartEngine engine;
	CategoryModel model;
	boolean threeD = false;
	String orient = "horizontal";

	@Init
	public void init() throws Exception {
		// prepare chart data
		engine = new BarChartEngine();
		model = ChartData.getModel();
	}

	public BarChartEngine getEngine() {
		return engine;
	}

	public CategoryModel getModel() {
		return model;
	}

	public String getOrient() {
		return orient;
	}

	public boolean isThreeD() {
		return threeD;
	}
	
	@GlobalCommand("configChanged") 
	@NotifyChange({"threeD","orient"})
	public void onConfigChanged(
			@BindingParam("threeD") Boolean threeD,
			@BindingParam("orient") String orient){
		if(threeD != null){
			this.threeD = threeD;
		}
		if(orient != null){
			this.orient = orient;
		}
	}
}
