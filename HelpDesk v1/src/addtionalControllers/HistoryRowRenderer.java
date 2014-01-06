package addtionalControllers;

import model.Comment;

import org.zkoss.zul.Box;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Vbox;

public class HistoryRowRenderer implements RowRenderer<Comment>  {
	
	@Override
	public void render(Row row, Comment comment, int index) throws Exception {
		
		Vbox vbox = new Vbox();
		Box horizontalBox = new Box();
		horizontalBox.setOrient("horizontal");
		Label date = new Label(comment.getDate().toString());
		date.setParent(horizontalBox);
		Label employeeName = new Label(comment.getEmployeeName());
		employeeName.setParent(horizontalBox);
		Label writesLabel = new Label("rašo:");
		writesLabel.setParent(horizontalBox);
		horizontalBox.setParent(vbox);
		Label commentLabel = new Label(comment.getComment());
		commentLabel.setParent(vbox);
		vbox.setParent(row);

	}

}
