package org.sarah.web.client.services.impl;

import javax.annotation.PostConstruct;

import org.sarah.web.client.dao.UserDao;
import org.sarah.web.client.entities.AccessTokens;
import org.sarah.web.client.entities.User;
import org.sarah.web.client.oauthclient1.http.OAuthHttp;
import org.sarah.web.client.oauthclient1.http.impl.OAuthHttpImpl;
import org.sarah.web.client.services.UserService;
import org.sarah.web.client.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/**
 * The class UserServiceImpl.
 * 
 * @author chandan
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
	/**
	 * userDao
	 */
	@Autowired
	UserDao userDao;
	/**
	 * gson
	 */
	Gson gson = new Gson();
	/**
	 * oAuthHttp
	 */
	OAuthHttp oAuthHttp = null;
	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;

	/**
	 * intialize
	 */
	@PostConstruct
	public void intialize() {
		try {
			oAuthHttp = new OAuthHttpImpl();
			setGenericDao(userDao);
			getGenericDao().setMongoTemplate(userDao.getMongoTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * createLocalAccount
	 * 
	 * @param accessToken
	 * @return User
	 */
	public User createLocalAccount(AccessTokens accessToken) {
		try {
			String baseUrl = propertiesFile.getoAuthBaseUrl();
			String res = oAuthHttp.get(baseUrl.substring(0, baseUrl.lastIndexOf("/")), accessToken.getUserId(),
					"/users/" + accessToken.getUserId(), accessToken.getAccessToken(), propertiesFile.getConsumerKey(),
					propertiesFile.getConsumerSecret(), accessToken.getAccessTokenSecret());
			User user = gson.fromJson(res, User.class);
			if (user != null) {
				User alreadyUser = userDao.get(user.getId());
				if (alreadyUser == null) {
					userDao.save(user);
					return user;
				} else {
					return alreadyUser;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
