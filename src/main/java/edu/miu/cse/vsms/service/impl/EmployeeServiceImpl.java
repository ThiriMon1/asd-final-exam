package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto request) {
        // Write your code here
        System.out.println("service");
        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        employee.setHireDate(request.hireDate());
        System.out.println(employee.getName());
        System.out.println(employee.getEmail());
        System.out.println(employee.getPhone());


        Employee savedEmployee=employeeRepository.save(employee);
        return mapToResponseDto(savedEmployee);

    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        // Write your code here
        List<Employee> employees=employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtos=new ArrayList<>();
        for(Employee employee:employees){
            employeeResponseDtos.add(mapToResponseDto(employee));
        }

        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        // Write your code here
        Employee employee=employeeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee-"+id+" is not found"));
        return mapToResponseDto(employee);
    }

    @Override
    public EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {
        // Fetch the employee by ID or throw an exception if not found
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee-"+ id +" is not found"));

        // Apply each update based on the key
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    // Write your code here
                    employee.setName((String) value);


                    break;
                case "email":
                    // Write your code here
                    employee.setEmail((String) value);

                    break;
                case "phone":
                    // Write your code here
                    employee.setPhone((String) value);

                    break;
                case "hireDate":
                    // Write your code here
                    employee.setHireDate((LocalDate) value);

                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        // Write your code here
        Employee updatedEmployee=employeeRepository.save(employee);


        return mapToResponseDto(updatedEmployee);
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee) {
        List<VehicleServiceResponseDto> serviceDtos = employee.getVServices().stream()
                .map(service -> new VehicleServiceResponseDto(
                        service.getId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                )).toList();

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                serviceDtos
        );
    }
}
