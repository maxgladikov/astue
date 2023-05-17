package astue.service;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("rawtypes")
public interface TreeNode<T extends TreeNode> {
	
	public void setParent(T parent);
	public T  getParent();
	
	public List<T> getChildren();
	public void addChild(T child);
	public void addChildren(Collection<T> children);
	
	public boolean isRoot();
	public boolean isLeaf();
}
