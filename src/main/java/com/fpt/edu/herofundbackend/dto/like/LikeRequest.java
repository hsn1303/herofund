package com.fpt.edu.herofundbackend.dto.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequest {
    private long id;
    private long accountId;
    private long itemId;
    private long typeId;
    private int status;
}
