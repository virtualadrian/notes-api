package com.notes.core;
import java.io.Serializable;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseCrudService<M extends BaseType, E extends BaseType, ID extends Serializable> {

    @Autowired
    private JpaRepository<E, ID> repository;
    @Autowired
    private ModelMapper mapper;

    private final Type modelCollectionTypeToken = new TypeToken<Iterable<M>>() {}.getType()
        .getClass();
    private final Type entityCollectionTypeToken = new TypeToken<Iterable<E>>() {}.getType();

    public M find(ID id) {
        return repository.findById(id).map(this::toModel).orElse(null);
    }

    public Iterable<M> findall() {
        Iterable<E> entities = this.repository.findAll();
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    public Page<M> findall(int page, int pageSize) {
        Iterable<E> entities = this.repository.findAll(PageRequest.of(page, pageSize));
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    public Page<M> findall(M example, int page, int pageSize) {
        E sampleEntity = this.mapper.map(example, example.getMapping());
        Example<E> searchEntity = Example.of(sampleEntity);
        Iterable<E> entities = this.repository
            .findAll(searchEntity, PageRequest.of(page, pageSize));
        return toPageModels(entities);
    }

    public Page<M> findSortAll(M example, int page, int pageSize, Direction direction,
        String... properties) {
        E sampleEntity = this.mapper.map(example, example.getMapping());
        Example<E> searchEntity = Example.of(sampleEntity);
        Iterable<E> entities = this.repository.findAll(searchEntity,
            PageRequest.of(page, pageSize, new Sort(direction, properties)));
        return toPageModels(entities);
    }

    public Iterable<M> findall(M example) {
        E sampleEntity = this.mapper.map(example, example.getMapping());
        Example<E> searchEntity = Example.of(sampleEntity);
        Iterable<E> entities = this.repository.findAll(searchEntity);
        return toModels(entities);
    }

    public M create(M creating) {
        E entity = this.mapper.map(creating, creating.getMapping());
        E created = this.repository.save(entity);
        return this.mapper.map(created, creating.getMapping());
    }

    public Iterable<M> create(Iterable<M> creating) {
        Iterable<E> entities = this.mapper.map(creating, this.entityCollectionTypeToken);
        Iterable<E> createdEntities = this.repository.saveAll(entities);
        return this.mapper.map(createdEntities, this.modelCollectionTypeToken);
    }

    public M update(M updating) {
        E entity = this.mapper.map(updating, updating.getMapping());
        E created = this.repository.save(entity);
        return this.mapper.map(created, created.getMapping());
    }

    public Iterable<M> update(Iterable<M> updating) {
        Iterable<E> entitiesUpdating = this.mapper.map(updating, this.entityCollectionTypeToken);
        return this.mapper
            .map(this.repository.saveAll(entitiesUpdating), this.modelCollectionTypeToken);
    }

    public void delete(ID id) {
        // delete by ID
        this.repository.deleteById(id);
    }

    protected M toModel(E entity) {
        if (entity == null) { return null; }
        return this.mapper.map(entity, entity.getMapping());
    }

    protected E toEntity(M model) {
        if (model == null) { return null; }
        return this.mapper.map(model, model.getMapping());
    }

    protected Page<M> toPageModels(Iterable<E> entities) {
        if (entities == null) { return null; }
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    protected Iterable<M> toModels(Iterable<E> entities) {
        if (entities == null) { return null; }
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    protected Iterable<E> toEntities(Iterable<M> models) {
        if (models == null) { return null; }
        return this.mapper.map(models, this.entityCollectionTypeToken);
    }
}
