package br.com.volvo.persistence.services;

import br.com.volvo.persistence.models.Department;
import br.com.volvo.persistence.repositories.DepartmentRepository;
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
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        List<Department> permissions = departmentRepository.findAll();
        if(permissions == null)
            throw new ObjectNotFoundException("Permission not found: " + Department.class.getName());
        return permissions;
    }

    public Page<Department> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return departmentRepository.findAll(pageRequest);
    }

    public Department findById(Integer id) {
        Optional<Department> categoria = departmentRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                "Permission not found: " + id + ", type: " + Department.class.getName()));
    }

    public Department insert(Department obj) {
        obj.setId(null);
        return departmentRepository.save(obj);
    }

    public Department update(Department obj) {
        Department permissionNew = findById(obj.getId());
        return departmentRepository.save(permissionNew);
    }

    public void delete(Integer id) {
    	findById(id);
        try {
            departmentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("You can not delete a department that is related.\n");
        }
    }

}
