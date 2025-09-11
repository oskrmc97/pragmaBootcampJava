package co.com.pragma.model.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResult<T> {
    private final List<T> items;
    private final long total;
    private final int page;
    private final int size;
}
