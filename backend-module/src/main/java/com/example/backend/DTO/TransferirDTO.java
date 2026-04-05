package com.example.backend.DTO;

import java.math.BigDecimal;

public record TransferirDTO(Long fromId, Long toId, BigDecimal amount) {
}
