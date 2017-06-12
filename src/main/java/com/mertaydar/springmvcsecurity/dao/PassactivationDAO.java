package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Passactivation;
import com.mertaydar.springmvcsecurity.model.PassactivationInfo;

public interface PassactivationDAO {
	public Passactivation findPassactivation(Integer id);
	public Passactivation findPassactivationWithUser(Integer id);
	public Passactivation findPassactivationWithCode(String code);
	public void savePassactivation(Passactivation act);
	public void deletePassactivation(Integer id);
	public List<PassactivationInfo> listPassactivations();
	void deletePassactivationWithUser(Integer id);
}
