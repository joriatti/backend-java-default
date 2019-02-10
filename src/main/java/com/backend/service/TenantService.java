package com.backend.service;

import com.backend.model.Tenant;
import com.backend.model.enums.TenantStateEnum;
import com.backend.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService extends AbstractPersistence<Tenant, String> {

    @Autowired
    private IdGenerator idGenerator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Tenant createNewTenant() {
        Tenant tenant = new Tenant();
        tenant.setId(idGenerator.nextId());
        tenant.setName("Nome da Empresa");
        tenant.setState(TenantStateEnum.ACTIVE);

        return this.save(tenant);
    }
}
