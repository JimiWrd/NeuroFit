package io.github.jimiwrd.workoutservice.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private int size;
    private int pageNum;
    private int totalPages;
    private List<T> content;

    public static <T> PageResponse<T> toResponse(Page<T> page){
        return PageResponse.<T>builder()
                .size(page.getNumberOfElements())
                .pageNum(page.getNumber())
                .totalPages(page.getTotalPages())
                .content(page.getContent())
                .build();
    }
}
