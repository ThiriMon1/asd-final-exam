package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Add a new employee
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto request) {
        // Write your code here
        System.out.println("controller"+request.email());
        EmployeeResponseDto responseDto = employeeService.addEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        // Write your code here
        List<EmployeeResponseDto> responseDto = employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Get a specific employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        // Write your code here
        EmployeeResponseDto responseDto = employeeService.getEmployeeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Update partially an existing employee
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        // Write your code here
        EmployeeResponseDto responseDto = employeeService.partiallyUpdateEmployee(id, updates);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
