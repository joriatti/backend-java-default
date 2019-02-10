package com.backend.api.v1.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel("UserResponseList")
public class UserResponseList {
	
	List<UserResponse> items = new ArrayList();

	public List<UserResponse> getItems() {
		return items;
	}

	public void setItems(List<UserResponse> items) {
		this.items = items;
	}

	

}
