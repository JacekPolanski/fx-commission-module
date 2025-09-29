package com.bank.fxcommission.application.dto;

import com.bank.fxcommission.domain.SpreadType;

import java.util.UUID;

public record UpdateAccountUseCaseDto(
        UUID id, String name, SpreadType spreadType) {}
