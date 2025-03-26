package net.vinpos.api.service.rest;

import java.util.UUID;
import net.vinpos.api.dto.rest.request.FloorReqDto;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.mapping.rest.FloorMapper;
import net.vinpos.api.model.Floor;
import net.vinpos.api.repository.FloorRepository;
import org.springframework.stereotype.Service;

@Service
public class FloorService extends BaseService<Floor, FloorRepository> {

  private final FloorMapper floorMapper;

  public FloorService(FloorRepository repository, FloorMapper floorMapper) {
    super(repository);
    this.floorMapper = floorMapper;
  }

  public Floor create(FloorReqDto dto) {
    if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
      throw new ConflictException(modelClass, "name", dto.getName());
    }
    Floor floor = floorMapper.dto2Model(dto);
    return repository.save(floor);
  }

  public Floor updateById(UUID id, FloorReqDto dto) {
    Floor floor = this.getById(id, false);
    if (!floor.getName().equalsIgnoreCase(dto.getName())) {
      if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
        throw new ConflictException(modelClass, "name", dto.getName());
      }
    }
    floorMapper.updateModelFromDto(dto, floor);
    return repository.save(floor);
  }
}
