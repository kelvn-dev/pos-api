package net.vinpos.api.service.rest;

import java.util.UUID;
import net.vinpos.api.dto.rest.request.TableReqDto;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.mapping.rest.TableMapper;
import net.vinpos.api.model.Floor;
import net.vinpos.api.model.TableEntity;
import net.vinpos.api.repository.FloorRepository;
import net.vinpos.api.repository.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService extends BaseService<TableEntity, TableRepository> {

  private final TableMapper tableMapper;
  private final FloorRepository floorRepository;

  public TableService(
      TableRepository repository, TableMapper tableMapper, FloorRepository floorRepository) {
    super(repository);
    this.tableMapper = tableMapper;
    this.floorRepository = floorRepository;
  }

  public TableEntity create(TableReqDto dto) {
    if (repository.findByNumber(dto.getNumber()).isPresent()) {
      throw new ConflictException(modelClass, "number", dto.getNumber().toString());
    }
    Floor floor =
        floorRepository
            .findById(dto.getFloorId())
            .orElseThrow(
                () -> new RuntimeException("Floor not found with ID: " + dto.getFloorId()));

    TableEntity table = tableMapper.dto2Model(dto);
    table.setFloor(floor);

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
