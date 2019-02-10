package com.backend.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.backend.exceptions.BaseException;
import com.backend.exceptions.NotFoundException;
import com.backend.model.Signup;
import com.backend.model.Tenant;
import com.backend.model.User;
import com.backend.model.enums.UserStateEnum;
import com.backend.model.enums.UserTypeEnum;
import com.backend.util.IdGenerator;

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
    
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<User> findAllUsersByTenantId(String currentTenantId, String tenantId) throws BaseException {
    	if (!currentTenantId.equals(tenantId)) {
    		throw new NotFoundException("TENANT_NOT_FOUND");
    	}
    	String sql = "SELECT u FROM User u WHERE u.tenantId = :tenantId";
    	TypedQuery<User> query =  this.em.createQuery(sql, User.class);
    	query.setParameter("tenantId", tenantId);
    	List<User> result = query.getResultList();
    	
    	return result;
    }
}