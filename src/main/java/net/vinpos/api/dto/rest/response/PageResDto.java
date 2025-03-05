package net.vinpos.api.dto.rest.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResDto<M> implements Serializable {
  private int pageIndex;
  private int totalPages;
  private long totalItems;
  private boolean last;
  private boolean first;
  private List<M> items;
}
