package com.bonam.library.api.v1.controller;

import com.bonam.library.api.v1.mapper.LoanDTOMapper;
import com.bonam.library.api.v1.model.request.UpdateLoanRequestDTO;
import com.bonam.library.api.v1.model.response.LoanResponseDTO;
import com.bonam.library.api.v1.openapi.controller.UpdateLoanControllerOpenApi;
import com.bonam.library.domain.loans.service.UpdateLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class UpdateLoanController implements UpdateLoanControllerOpenApi {

    private final UpdateLoanService updateLoanService;

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> updateLoan(@PathVariable Long id, @RequestBody @Valid UpdateLoanRequestDTO request) {
        var loan = LoanDTOMapper.toEntity(request);
        var updatedLoan = updateLoanService.updateLoan(id, loan);
        return ResponseEntity.status(HttpStatus.OK).body(LoanDTOMapper.toResponseDTO(updatedLoan));
    }
}
