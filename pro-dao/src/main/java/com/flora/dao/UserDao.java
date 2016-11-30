package com.flora.dao;

import com.flora.domain.db.User;

import java.util.List;

public interface UserDao {

	public List<User> selectEntryList(Long id);

}
