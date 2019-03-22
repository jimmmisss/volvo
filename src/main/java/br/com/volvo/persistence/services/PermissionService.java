package br.com.volvo.persistence.services;

import br.com.volvo.persistence.models.Permission;
import br.com.volvo.persistence.repositories.PermissionRepository;
import br.com.volvo.persistence.services.exceptions.DataIntegrityException;
import br.com.volvo.persistence.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> findAll() {
        List<Permission> permissions = permissionRepository.findAll();
        if(permissionRepository == null)
            throw new ObjectNotFoundException("Permission not found: " + Permission.class.getName());
        return permissions;
    }

    public Permission findById(Integer id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.orElseThrow(() -> new ObjectNotFoundException("Permission not found: " + id + ", type: " + Permission.class.getName()));
    }

    public Page<Permission> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Permission> permissions = permissionRepository.findAllById(ids);
        return (Page<Permission>) permissions;
    }

    public Permission insert(Permission obj) {
        obj.setId(null);
        return permissionRepository.save(obj);
    }

    public Permission update(Permission obj) {
        Permission permissionNew = findById(obj.getId());
        return permissionRepository.save(permissionNew);
    }

    public void delete(Integer id) {
    	findById(id);
        try {
            permissionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("You can not delete a permission that is related.");
        }
    }

}
