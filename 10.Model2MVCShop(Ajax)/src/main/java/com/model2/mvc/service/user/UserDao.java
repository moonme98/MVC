package com.model2.mvc.service.user;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;


//==> 회원관리에서 CRUD 추상화/캡슐화한 DAO Interface Definition
public interface UserDao {
	
	// INSERT
	public void addUser(User user) throws Exception ;

	// SELECT ONE
	public User getUser(String userId) throws Exception ;
	
	// 카카오 로그인용: 이메일로 회원 조회
	public User getUserByEmail(String email) throws Exception;

	// SELECT LIST
	public List<User> getUserList(Search search) throws Exception ;

	// UPDATE
	public void updateUser(User user) throws Exception ;
	
	// 게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
	
}