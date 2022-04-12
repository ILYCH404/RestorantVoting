package com.example.restoranvoting.web.user;

import com.example.restoranvoting.model.User;
import com.example.restoranvoting.repository.UserRepository;
import com.example.restoranvoting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}