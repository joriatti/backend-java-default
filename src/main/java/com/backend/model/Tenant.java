package com.backend.model;

import com.backend.model.enums.TenantStateEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APP_TENANT")
public class Tenant {

    @Id
    @Column(name = "ID", length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TENANT_STATE", length = 30, nullable = false)
    private TenantStateEnum state = TenantStateEnum.INACTIVE;

    @Column(name = "TENANT_NAME", length = 100)
    private String name;

    @Column(name = "TENANT_LOGO_ID", length = 36)
    private String tenantLogoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TenantStateEnum getState() {
        return state;
    }

    public void setState(TenantStateEnum state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantLogoId() {
        return tenantLogoId;
    }

    public void setTenantLogoId(String tenantLogoId) {
        this.tenantLogoId = tenantLogoId;
    }
}