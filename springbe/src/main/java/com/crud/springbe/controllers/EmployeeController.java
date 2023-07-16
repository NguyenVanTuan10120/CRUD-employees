package com.crud.springbe.controllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.crud.springbe.exception.ResourceNotFoundException;
import com.crud.springbe.models.Employee;
import com.crud.springbe.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
@RequestMapping("/api/v1/")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("employee not exist with id:"+ id));
        return ResponseEntity.ok(employee);
    }

    
    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        //truy xuất employee từ database
        Employee employee = employeeRepository.findById(id)
        .orElseThrow(() ->new ResourceNotFoundException("employee not exist with id:"+ id));
        // update employee
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        //lưu thông tin user vào database rồi trả đối tượng update về cho client
        Employee updatEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatEmployee);
    }


    // delete employee rest api
    @DeleteMapping("/employees/{id}") // chú thích ánh xạ để phương thức này được sử lý yêu cầu http
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        // truy xuất employee có id cụ thể 
        Employee employee = employeeRepository.findById(id)
        // nếu id không có trong database thì đưa ra cảnh báo
        .orElseThrow(() ->new ResourceNotFoundException("employee not exist with id:"+ id));
        // thực hiện lấy data employee từ employeeRepository, sử dụng phương thức xoá employee cụ thể với id với kiểu trả về là employee
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
