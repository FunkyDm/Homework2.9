package pro.sky.collectionStart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pro.sky.collectionStart.exceptions.*;
import pro.sky.collectionStart.model.Employee;
import pro.sky.collectionStart.service.impl.EmployeeServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/employee/")
public class EmployeeController {
    private final EmployeeServiceImpl employeeServiceImpl;

    public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {

        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping("/add")
    public Employee addEmployee(@RequestParam(value = "firstName") String firstName,
                                @RequestParam(value = "lastName") String lastName,
                                @RequestParam(value = "salary") double salary,
                                @RequestParam(value = "department") int department) {
        try {
            return employeeServiceImpl.addEmployee(firstName, lastName, salary, department);
        } catch (EmployeesStorageFullException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Больше нельзя добавлять сотрудников.", exception);
        } catch (EmployeeAlreadyAddedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сотрудник уже добавлен", exception);
        } catch (EmployeeWrongSalaryException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильное значение зарплаты", exception);
        } catch (EmployeeWrongDepartmentNumberException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильный номер отдела", exception);
        }
    }

    @GetMapping("/remove")
    public Employee removeEmployee(@RequestParam(value = "firstName") String firstName,
                                   @RequestParam(value = "lastName") String lastName) {
        try {
            return employeeServiceImpl.removeEmployee(firstName, lastName);
        } catch (EmployeeNotFoundExceptions exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого сотрудника не существует.", exception);
        }
    }

    @GetMapping("/find")
    public Employee findEmployee(@RequestParam(value = "firstName") String firstName,
                                 @RequestParam(value = "lastName") String lastName) {
        try {
            return employeeServiceImpl.findEmployee(firstName, lastName);
        } catch (EmployeeNotFoundExceptions exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого сотрудника не существует.", exception);
        }
    }

    @GetMapping
    public Collection<Employee> printAllEmployees() {
        return employeeServiceImpl.printAllEmployees();
    }

}