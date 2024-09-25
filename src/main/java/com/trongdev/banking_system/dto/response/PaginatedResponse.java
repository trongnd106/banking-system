package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaginatedResponse<T> {
    long total;
    int curPage;
    int nextPage;
    int prevPage;
    int perPage;
    List<T> data;
}
