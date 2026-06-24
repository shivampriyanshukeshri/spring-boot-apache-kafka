package com.glory.global.persistenceStore;

import com.glory.global.dto.BorrowResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@ToString
@Component
public class BorrowPersistenceStore{
    private Map<Long, BorrowResponseDTO> failedBorrowResponses = new HashMap<>();
    private Map<Long, BorrowResponseDTO> successfulBorrowResponses = new HashMap<>();
}