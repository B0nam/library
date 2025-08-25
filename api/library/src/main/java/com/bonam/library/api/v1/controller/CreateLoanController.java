package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.LoanDTOMapper;
import com.bonam.library.api.v1.model.request.CreateLoanRequestDTO;
import com.bonam.library.api.v1.model.response.LoanResponseDTO;
import com.bonam.library.api.v1.openapi.controller.CreateLoanControllerOpenApi;
import com.bonam.library.domain.loans.service.CreateLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class CreateLoanController implements CreateLoanControllerOpenApi {

    private final CreateLoanService createLoanService;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody @Valid CreateLoanRequestDTO request) {
        var loan = LoanDTOMapper.toEntity(request);
        var createdLoan = createLoanService.createLoan(loan, request.getBookId(), request.getLibraryUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(LoanDTOMapper.toResponseDTO(createdLoan));
    }
}
