package com.backend.service;

import com.backend.exceptions.BaseException;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Signup;
import com.backend.model.Tenant;
import com.backend.model.User;
import com.backend.model.enums.UserStateEnum;
import com.backend.model.enums.UserTypeEnum;
import com.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService extends AbstractPersistence<User, String> {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private TenantService tenantService;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User findByEmail(String email) {
        return this.findByUniqueProperty("email", email);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User createUserFromSignup(Signup signup) {

        Tenant tenant = tenantService.createNewTenant();

        User user = new User();
        user.setId(idGenerator.nextId());
        user.setTenantId(tenant.getId());
        user.setUserType(UserTypeEnum.ADMIN);
        user.setState(UserStateEnum.ACTIVE);
        user.setMainUser(true);
        user.setFirstName(signup.getFirstName());
        user.setEmail(signup.getEmail());
        user.setPassword(signup.getPassword());
        user.setLastName(signup.getLastName());

        return this.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User findById(String tenantId, String id) throws BaseException {
        Optional<User> user = this.findById(id);
        if (!user.isPresent() || !user.get().getTenantId().equals(tenantId)) {
            throw new NotFoundException("USER_NOT_FOUND_FOR_ID", new Object[] {id});
        }
        return user.get();
    }
}