package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public VehicleServiceResponseDto assignService(ServiceRequestDto request) {
        // Write your code here
        VService vservice = new VService();
        Employee employee = employeeRepository.findById(request.employeeId()).orElseThrow(() ->
                new ResourceNotFoundException("Employee not found"));
        vservice.setEmployee(employee);
        vservice.setServiceName(request.serviceName());
        vservice.setCost(request.cost());
        vservice.setVehicleType(request.vehicleType());
        VService savedVService=vehicleServiceRepository.save(vservice);

        VehicleServiceResponseDto vehicleServiceResponseDto= new VehicleServiceResponseDto(
               savedVService.getId(),savedVService.getServiceName(), savedVService.getCost(), savedVService.getVehicleType()
        );

        return vehicleServiceResponseDto;
    }
}
