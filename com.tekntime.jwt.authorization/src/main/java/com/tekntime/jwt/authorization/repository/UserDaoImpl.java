package com.tekntime.jwt.authorization.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.tekntime.jwt.authorization.model.TekntimeUser;


@Repository
public class UserDaoImpl {

	private static final Logger logger   = LoggerFactory.getLogger(UserDaoImpl.class);	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public List<User> findAll() {
		List<User> users = mongoTemplate.findAll(User.class);
        return users;
    }
	
    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    
	public User save(TekntimeUser user) throws Exception{
		if("".equals(user.get_id() )) {
			logger.info("----->new add : ");
			return mongoTemplate.save(user);
		}else {
			logger.info("----->update : ");
			return this.update(user, user.get_id());
		}
    }

	public User update(User user, String id) throws Exception{
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("username",user.getUsername());
        update.set("password",user.getPassword());
        update.set("locked", user.isAccountNonLocked());
        update.set("expired", user.isAccountNonExpired());
        update.set("passwordexpired", user.isCredentialsNonExpired());
        update.set("enabled", user.isEnabled());
        update.set("roles", user.getAuthorities());
       
        Object obj= mongoTemplate.findAndModify(query, update, User.class);
        if(obj ==null) {
        	logger.error("User not found _id=" + id);
        	throw new Exception("User not found _id=" + id);
        }else {
        	((TekntimeUser)user).set_id(id);
        	return user;
        }
    }
	
	
	public List<User> findOne(String username){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.find(query, User.class);
    }

    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, User.class);
        return true;
    }
	
}
