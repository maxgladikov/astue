package astue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.core.io.InputStreamResource;

public abstract class AbstractReport implements TreeNode<AbstractReport>{
	
	private AbstractReport parent;
	private Set<AbstractReport> children=new HashSet<>();
	
	@Override
	public AbstractReport getParent() {
		return parent;
	}
	
	@Override
	public void setParent(AbstractReport parent) {
		this.parent=parent;
	}
	
	@Override
	public List<AbstractReport> getChildren(){
		return children.stream().toList();
	}
	
	@Override
	public void addChild(AbstractReport child) {
		children.add(child);
	}
	
	@Override
	public void addChildren(Collection<AbstractReport> children) {
		Optional.ofNullable(children).stream().flatMap(i -> i.stream()).forEach(children::add);
	}
	
	@Override
	public boolean isRoot() {
		return Optional.ofNullable(parent).isEmpty();
	}
	
	@Override
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	public abstract InputStreamResource create(LocalDateTime from, LocalDateTime to);
	
	
	
}
