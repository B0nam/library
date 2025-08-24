package com.bonam.library.api.v1.openapi.controller;

import com.bonam.library.api.v1.model.request.CreateLoanRequestDTO;
import com.bonam.library.api.v1.model.response.LoanResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Loans", description = "API for loan management")
public interface CreateLoanControllerOpenApi {

    @Operation(summary = "Create a new loan", description = "Creates a new loan with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "404", description = "Book or Library User not found")
    })
    ResponseEntity<LoanResponseDTO> createLoan(CreateLoanRequestDTO request);
}
