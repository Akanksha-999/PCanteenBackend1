/*package Pcanteen.Backend;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Employee API", description = "Operations related to employee") // Change name/desc as per controller
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}


	@PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        employeeService.registerEmployee(employee);
        return ResponseEntity.ok("Employee created successfully");
    }
}
*/