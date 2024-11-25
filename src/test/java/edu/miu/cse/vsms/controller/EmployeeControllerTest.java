package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void addEmployee_validInput_returnsEmployee() {
        EmployeeRequestDto employeeRequestDto=new EmployeeRequestDto("john","john@email.com","123456",LocalDate.of(2024,11,25));
        EmployeeResponseDto employeeResponseDto=new EmployeeResponseDto(1L,"john","john@email.com","123456", LocalDate.of(2014,11,25),null);
        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeRequestDto.class))).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> employeeResponseDtoResponseEntity=employeeController.addEmployee(employeeRequestDto);
        assert employeeResponseDtoResponseEntity.getStatusCode()== HttpStatus.CREATED;
        Assertions.assertEquals(employeeResponseDto,employeeResponseDtoResponseEntity.getBody());
    }
}