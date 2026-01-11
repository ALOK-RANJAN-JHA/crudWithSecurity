package com.crud.controller;


import com.crud.entity.Employee;
import com.crud.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository repo;

    public EmployeeController(EmployeeRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Employee> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee e) {
        return ResponseEntity.ok(repo.save(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody Employee e) {
        return repo.findById(id).map(existing -> {
            existing.setName(e.getName());
            existing.setDepartment(e.getDepartment());
            existing.setSalary(e.getSalary());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repo.findById(id).map(r -> { repo.delete(r); return ResponseEntity.ok().build(); })
            .orElse(ResponseEntity.notFound().build());
    }
}

