package br.com.volvo.persistence.services;

import br.com.volvo.persistence.models.User;
import br.com.volvo.persistence.repositories.UserRepository;
import br.com.volvo.persistence.services.exceptions.DataIntegrityException;
import br.com.volvo.persistence.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }

    public List<User> findAll() {

        List<User> users = userRepository.findAll();
        if(users == null)
            throw new ObjectNotFoundException("User not found: " + User.class.getName());
        return users;
    }

    public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return userRepository.findAll(pageRequest);
    }

    public User findById(Integer id) {

        Optional<User> cliente = userRepository.findById(id);
        if(cliente == null)
            throw new ObjectNotFoundException("User not found: " + id + ", type: " + User.class.getName());
        return cliente.orElse(null);
    }

    @Transactional
    public User insert(User obj) {
        obj.setId(null);
        obj = userRepository.save(obj);
        return obj;
    }

    public User update(User obj) {
        User userNew = findById(obj.getId());
        return userRepository.save(userNew);
    }

    public void delete(Integer id) {
    	findById(id);
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("You can not delete a user that is related.");
        }
    }

}