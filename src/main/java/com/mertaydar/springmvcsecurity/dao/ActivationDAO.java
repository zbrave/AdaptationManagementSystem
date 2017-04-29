package com.mertaydar.springmvcsecurity.dao;

import com.mertaydar.springmvcsecurity.entity.Activation;

public interface ActivationDAO {
	
	public Activation findActivation(Integer id);
	public Activation findActivationWithUser(Integer id);
	public void saveActivation(Activation act);
	public void deleteActivation(Integer id);
}
