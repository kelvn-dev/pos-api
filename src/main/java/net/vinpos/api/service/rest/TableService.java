package net.vinpos.api.service.rest;

import java.util.UUID;
import net.vinpos.api.dto.rest.request.TableReqDto;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.mapping.rest.TableMapper;
import net.vinpos.api.model.TableEntity;
import net.vinpos.api.repository.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService extends BaseService<TableEntity, TableRepository> {

  private final TableMapper tableMapper;

  public TableService(TableRepository repository, TableMapper tableMapper) {
    super(repository);
    this.tableMapper = tableMapper;
  }

  public TableEntity create(TableReqDto dto) {
    if (repository.findByNumber(dto.getNumber()).isPresent()) {
      throw new ConflictException(modelClass, "number", dto.getNumber().toString());
    }
    TableEntity table = tableMapper.dto2Model(dto);
    return repository.save(table);
  }

  public TableEntity updateById(UUID id, TableReqDto dto) {
    TableEntity table = this.getById(id, false);
    if (!table.getNumber().equals(dto.getNumber())) {
      if (repository.findByNumber(dto.getNumber()).isPresent()) {
        throw new ConflictException(modelClass, "number", dto.getNumber().toString());
      }
    }
    tableMapper.updateModelFromDto(dto, table);
    return repository.save(table);
  }
}
