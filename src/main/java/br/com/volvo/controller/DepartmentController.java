package br.com.volvo.controller;

import br.com.volvo.persistence.models.Department;
import br.com.volvo.persistence.services.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("v1/categorias")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "Return all departments")
    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Return all departments with pageable")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<Department>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Department> departments = departmentService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(departments);
    }

    @ApiOperation(value = "Return one department")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Post department")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Department department) {
        department = departmentService.insert(department);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(department.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update department")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Department department, @PathVariable Integer id) {
        department.setId(id);
        department = departmentService.update(department);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete department")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
