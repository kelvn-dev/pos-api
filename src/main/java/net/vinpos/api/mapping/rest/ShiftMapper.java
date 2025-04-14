package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.ShiftReqDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.dto.rest.response.ShiftResDto;
import net.vinpos.api.model.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
     @Mapping(target = "managerName", expression = "java(shift.getManager() != null ? shift.getManager().getNickname() : null)")
     @Mapping(target = "totalInvoices", expression = "java(shift.getInvoices() != null ? shift.getInvoices().size() : 0)")
     @Mapping(target = "totalRevenue", expression = "java(shift.getInvoices() != null ? shift.getInvoices().stream().map(Invoice::getTotal).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add) : java.math.BigDecimal.ZERO)")
     @Mapping(target = "invoices", source = "invoices")
     ShiftResDto toResDto(Shift shift);
     void updateModelFromDto(ShiftReqDto dto, @MappingTarget Shift shift);

     @Mapping(source = "totalElements", target = "totalItems")
     @Mapping(source = "number", target = "pageIndex")
     @Mapping(
         source = "content",
         target = "items",
         defaultExpression = "java(java.util.Collections.emptyList())")
     PageResDto<ShiftResDto> model2Dto(Page<Shift> page);

     default double calculateRevenue(Shift shift) {
          if (shift.getInvoices() == null) return 0;
          return shift.getInvoices()
                  .stream()
                  .mapToDouble(inv ->
                          inv.getTotal() != null ? inv.getTotal().doubleValue() : 0.0
                  )
                  .sum();
     }
}
