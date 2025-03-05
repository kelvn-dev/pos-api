package net.vinpos.api.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<M, ID>
    extends EntityGraphJpaRepository<M, ID>, EntityGraphQuerydslPredicateExecutor<M> {
  Optional<M> findByIdAndCreatedBy(ID id, String createdBy);

  Optional<M> findByIdAndCreatedBy(ID id, String createdBy, EntityGraph entityGraph);
}
