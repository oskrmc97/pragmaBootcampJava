package co.com.pragma.model.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest {
    private final int page;
    private final int size;
}
