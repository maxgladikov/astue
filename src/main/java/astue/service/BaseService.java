package astue.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseService <T>{

    public Collection<T> getAll();
    public Optional<T> getById(Long id);
    public Optional<T> getByName(String name);
    public T add(T t);

}
