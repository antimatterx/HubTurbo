package backend.interfaces;

import backend.resource.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Only implemented by MultiModel.
 * This interface's purpose is to hide the public methods of MultiModel from
 * the UI.
  */
public interface IModel extends IBaseModel {
	public String getDefaultRepo();
	public void setDefaultRepo(String repoId);
	public Optional<Model> getModelById(String repoId);
	public Optional<TurboUser> getAssigneeOfIssue(TurboIssue issue);
	public List<TurboLabel> getLabelsOfIssue(TurboIssue issue);
	public List<TurboLabel> getLabelsOfIssue(TurboIssue issue, Predicate<TurboLabel> predicate);
	public Optional<TurboMilestone> getMilestoneOfIssue(TurboIssue issue);
}
