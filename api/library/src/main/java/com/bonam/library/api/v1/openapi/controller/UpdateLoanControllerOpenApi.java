package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.UpdateLoanRequestDTO;
import com.bonam.library.api.v1.model.response.LoanResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Loans", description = "API for loan management")
public interface UpdateLoanControllerOpenApi {

    @Operation(summary = "Update a loan", description = "Updates an existing loan with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Loan not found")})
    ResponseEntity<LoanResponseDTO> updateLoan(Long id, UpdateLoanRequestDTO request);
}
