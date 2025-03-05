package net.vinpos.api.service.rest;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.model.Dish;
import net.vinpos.api.repository.DishRepository;
import net.vinpos.api.enums.*;
import net.vinpos.api.exception.BadRequestException;
import net.vinpos.api.mapping.rest.DishMapper;
import net.vinpos.api.model.Dish;
import net.vinpos.api.repository.DishRepository;
import net.vinpos.api.utils.ConstantUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DishService extends BaseService<Dish, DishRepository> {

  private final DishMapper dishMapper;

  public DishService(
      DishRepository repository,
      DishMapper dishMapper
  ) {
    super(repository);
    this.dishMapper = dishMapper;
  }

  public Dish create(DishReqDto dto) {
    if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
      throw new ConflictException(modelClass, "name", dto.getName());
    }
    Dish dish = dishMapper.dto2Model(dto);
    return repository.save(dish);
  }

  public Dish updateById(UUID id, DishReqDto dto) {
    Dish dish = this.getById(id, false);
    if (!dish.getName().equalsIgnoreCase(dto.getName())) {
      if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
        throw new ConflictException(modelClass, "name", dto.getName());
      }
    }
    dishMapper.updateModelFromDto(dto, dish);
    return repository.save(dish);
  }
}
