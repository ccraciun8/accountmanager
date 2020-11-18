package com.assignment.accountmgr;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.accountmgr.classes.Account;
import com.assignment.accountmgr.classes.User;
import com.assignment.accountmgr.repository.UserRepository;
import com.assignment.accountmgr.utils.AccountUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test") // Set up a different profile for unit tests, to use in memory db
public class UserRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void emptyDatabase() {
		// make sure each test will start on a clean database
		userRepository.deleteAll();
	}
	
	@Test
	public void test_noUsers() {
		List<User> allFoundUsers = userRepository.findAll();
		assertTrue(allFoundUsers.size() == 0);
	}

	@Test
	public void test_getUserByUsername() {
		// given
		User user1 = new User("admin", "password");
		User user2 = new User("user", "user");
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.flush();

		// when
		User foundUser1 = userRepository.getUserForUsername(user1.getUsername());
		User foundUser2 = userRepository.getUserForUsername(user2.getUsername());

		// then
		assertTrue(foundUser1.getId() == user1.getId());
		assertTrue(foundUser2.getId() == user2.getId());
	}	
	
	@Test
	public void test_badUsername() {
		// given
		User user1 = new User("admin", "password");
		User user2 = new User("user", "user");
		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.flush();

		// when
		User foundUser = userRepository.getUserForUsername("incorrect");
		
		// then
		assertNull(foundUser);
	}	
}