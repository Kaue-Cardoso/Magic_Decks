package repository;

public interface BasicCrud<T> {
    T create(T object);

    T findById(Long id);

    T update(T object);

    void delete(Long id);
}
