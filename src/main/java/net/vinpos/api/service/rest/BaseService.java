package net.vinpos.api.service.rest;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.exception.NotFoundException;
import net.vinpos.api.model.BaseModel;
import net.vinpos.api.repository.BaseRepository;
import net.vinpos.api.utils.HelperUtils;
import net.vinpos.api.utils.PredicateUtils;
import net.vinpos.api.utils.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public abstract class BaseService<M extends BaseModel, R extends BaseRepository<M, UUID>> {

  protected final Class<M> modelClass =
      (Class<M>)
          ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  protected final R repository;

  public M getById(UUID id, boolean noException) {
    M model = repository.findById(id).orElse(null);
    if (Objects.isNull(model) && !noException) {
      throw new NotFoundException(modelClass, "id", id.toString());
    }
    return model;
  }

  public M getById(UUID id, EntityGraph entityGraph, boolean noException) {
    M model = repository.findById(id, entityGraph).orElse(null);
    if (Objects.isNull(model) && !noException) {
      throw new NotFoundException(modelClass, "id", id.toString());
    }
    return model;
  }

  public void deleteById(UUID id) {
    M model = this.getById(id, false);
    this.delete(model);
  }

  public void delete(M model) {
    repository.delete(model);
  }

  public Page<M> getList(List<String> filter, Pageable pageable) {
    List<SearchCriteria> criteria = HelperUtils.formatSearchCriteria(filter);
    BooleanExpression expression = PredicateUtils.getBooleanExpression(criteria, modelClass);
    return repository.findAll(expression, pageable);
  }
}
