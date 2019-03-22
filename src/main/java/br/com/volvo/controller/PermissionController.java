package br.com.volvo.controller;

import br.com.volvo.controller.utils.URL;
import br.com.volvo.persistence.models.Permission;
import br.com.volvo.persistence.services.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/produtos")
public class PermissionController {

    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "Return all permissions")
    @GetMapping
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(permissionService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Return one permission")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(permissionService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Return all permissions with pageable")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<Permission>> findPage(
            @RequestParam(value="nome", defaultValue="") String nome,
            @RequestParam(value="categorias", defaultValue="") String categorias,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Permission> permissions = permissionService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(permissions);
    }

    @ApiOperation(value = "Post permission")
    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody Permission permission) {
        permissionService.insert(permission);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(permission.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update permission")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Permission permission, @PathVariable Integer id) {
        permission.setId(id);
        permission = permissionService.update(permission);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete permission")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}