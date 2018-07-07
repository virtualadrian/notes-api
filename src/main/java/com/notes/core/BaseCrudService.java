package com.notes.core;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * The type Base crud service.
 *
 * @param <M>  the type parameter
 * @param <E>  the type parameter
 * @param <ID> the type parameter
 */
public class BaseCrudService<M, E extends BaseEntity, ID extends Serializable> implements IBaseCrudService<M, ID> {

    // @formatter:off

    /**
     * The Type parameter class.
     */
    private final Class<M> modelClass;
    private final Class<E> entityClass;
    /**
     * The Model collection type token.
     */
    protected final Type modelCollectionTypeToken = new TypeToken<Iterable<M>>() {}.getType();
    /**
     * The Entity collection type token.
     */
    protected final Type entityCollectionTypeToken = new TypeToken<Iterable<E>>() {}.getType();

    /**
     * The Repository.
     */
    @Autowired
    private JpaRepository<E, ID> repository;

    /**
     * The Mapper.
     */
    @Autowired
    protected ModelMapper mapper;

    /**
     * Instantiates a new Base dao.
     *
     * @param modelClass  the model class
     * @param entityClass the repository class
     */
    public BaseCrudService(Class<M> modelClass, Class<E> entityClass) {
        this.modelClass = modelClass;
        this.entityClass = entityClass;
    }

    /**
     * find one by the id
     * @param id the id
     * @return Model of M
     */
    @Override
    public M find(ID id) {
        E entity = this.repository.findOne(id);
        return toModel(entity);
    }

    /**
     * All iterable.
     *
     * @return the iterable
     */
    @Override
    public Iterable<M> findall() {
        Iterable<E> entities = this.repository.findAll();
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    /**
     * All iterable.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the iterable
     */
    @Override
    public Page<M> findall(int page, int pageSize) {
        Iterable<E> entities = this.repository.findAll(new PageRequest(page, pageSize));
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    /**
     * All iterable.
     *
     * @param example  the example
     * @param page     the page
     * @param pageSize the page size
     * @return the iterable
     */
    @Override
    public Page<M> findall(M example, int page, int pageSize) {
        // create example repository, search and page
        E sampleEntity = this.mapper.map(example, this.entityClass);
        Iterable<E> entities = this.repository.findAll(Example.of(sampleEntity),new PageRequest(page, pageSize));
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    /**
     * Model to instert
     * @param creating the creating
     * @return created Model of M
     */
    @Override
    public M create(M creating) {
        E entity = this.mapper.map(creating, this.entityClass);
        E created = this.repository.save(entity);
        return this.mapper.map(created, this.modelClass);
    }

    /**
     * Create a new collection
     * @param creating the creating
     * @return created Model Collection of M
     */
    @Override
    public Iterable<M> create(Iterable<M> creating) {
        Iterable<E> entities = this.mapper.map(creating, this.entityCollectionTypeToken);
        Iterable<E> createdEntities = this.repository.save(entities);
        return this.mapper.map(createdEntities, this.modelCollectionTypeToken);
    }

    /**
     * Update one
     * @param updating the updating
     * @return updated Model of M
     */
    @Override
    public M update(M updating) {
        E entity = this.mapper.map(updating, this.entityClass);
        E created = this.repository.save(entity);
        return this.mapper.map(created, this.modelClass);
    }

    /**
     * Update the collection
     * @param updating the updating
     * @return updated Collection Model of M
     */
    @Override
    public Iterable<M> update(Iterable<M> updating) {
        Iterable<E> entitiesUpdating = this.mapper.map(updating, this.entityCollectionTypeToken);
        return this.mapper.map(this.repository.save(entitiesUpdating), this.modelCollectionTypeToken);
    }

    /**
     * Delete one
     * @param id the id
     */
    @Override
    public void delete(ID id) {
        // delete by ID
        this.repository.delete(id);
    }

    public M toModel(E entity) {
        if (entity == null) return null;
        return this.mapper.map(entity, this.modelClass);
    }

    public E toEntity(M model) {
        if (model == null) return null;
        return this.mapper.map(model, this.entityClass);
    }

    public Iterable<M> toModels(Iterable<E> entities) {
        if (entities == null) return null;
        return this.mapper.map(entities, this.modelCollectionTypeToken);
    }

    public Iterable<E> toEntities(Iterable<M> models) {
        if (models == null) return null;
        return this.mapper.map(models, this.entityCollectionTypeToken);
    }
    // @formatter:on
}
