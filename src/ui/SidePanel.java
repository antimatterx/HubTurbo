package ui;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import model.TurboIssue;

public class SidePanel extends VBox {

	public enum Layout {
		TABS, ISSUE, HISTORY
	}

	private Layout layout;
	private Stage parentStage;
	private Model model;
	
	public SidePanel(Stage parentStage, Model model) {
		this.parentStage = parentStage;
		this.model = model;
		setLayout(Layout.TABS);
	}

	public Layout getLayout() {
		return layout;
	}

	private void setLayout(Layout layout) {
		this.layout = layout;
		changeLayout();
	}

	public void refresh() {
		changeLayout();
	}
	
	private TurboIssue displayedIssue;
	public void displayIssue(TurboIssue issue) {
		displayedIssue = issue;
		setLayout(Layout.ISSUE);
	}
	
	private void changeLayout() {
		getChildren().clear();
		switch (layout) {
		case TABS:
			getChildren().add(tabLayout());
			break;
		case HISTORY:
			getChildren().add(historyLayout());
			break;
		case ISSUE:
			getChildren().add(issueLayout());
			break;
		default:
			assert false;
			break;
		}
	}

	private Node tabLayout() {
		
		VBox everything = new VBox();
		
		TabPane tabs = new TabPane();
		Tab labelsTab = createLabelsTab();
		Tab milestonesTab = createMilestonesTab();
		Tab assigneesTab = createAssgineesTab();
		Tab feedTab = createFeedTab();
		
		tabs.getTabs().addAll(labelsTab, milestonesTab, assigneesTab, feedTab);
		
		HBox repoFields = new HBox();
		
		everything.getChildren().addAll(repoFields, tabs);
		
		return everything;
	}

	private Tab createFeedTab() {
		Tab tab = new Tab();
		tab.setClosable(false);
		tab.setText("Feed");
		return tab;
	}

	private Tab createAssgineesTab() {
		Tab tab = new Tab();
		tab.setClosable(false);
		tab.setText("A");
		tab.setContent(new AssigneeManagementComponent(model).initialise());
		return tab;
	}

	private Tab createMilestonesTab() {
		Tab tab = new Tab();
		tab.setClosable(false);
		tab.setText("M");
		tab.setContent(new MilestoneManagementComponent(model).initialise());
		return tab;
	}

	private Tab createLabelsTab() {
		Tab tab = new Tab();
		tab.setClosable(false);
		tab.setText("L");
		tab.setContent(new LabelManagementComponent(parentStage, model).initialise());
		return tab;
	}

	private Node historyLayout() {
		return new VBox();
	}

	private Node issueLayout() {
		return new IssueEditComponent(displayedIssue, parentStage, model);
	}
	
}
