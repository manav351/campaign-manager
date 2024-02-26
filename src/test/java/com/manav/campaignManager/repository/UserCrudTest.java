package com.manav.campaignManager.repository;

import com.manav.campaignManager.config.SecurityFilterConfig;
import com.manav.campaignManager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@DataJpaTest
class UserCrudTest {
    private final UserCrud userCrud;

    @Autowired
    UserCrudTest( final UserCrud userCrud){
        this.userCrud = userCrud;
    }

    // Tests the findById method of UserCrud
    @Test
    void findById() {
        // Given
        User user = User.builder()
                .emailId("testUser1@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();
        userCrud.save(user);

        // When
        Integer userId = user.getUserId();
        Optional<User> userFromDatabase = userCrud.findById(userId);

        // Then
        assertThat(userFromDatabase.isPresent()).isTrue();
        assertThat(userFromDatabase.get()).isEqualTo(user);
    }

    // Fetching user details using emailId
    @Test
    void testFindByEmailId() {
        // Given
        User user = User.builder()
                .emailId("testUser2@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();
        userCrud.save(user);

        // When
        String emailId = user.getEmailId();
        Optional<User> userFromDatabase = userCrud.findByEmailId(emailId);

        // Then
        assertThat(userFromDatabase.isPresent()).isTrue();
        assertThat(userFromDatabase.get()).isEqualTo(user);
    }


    // Saving a user with optional fields populated
    @Test
    void testSaveUserWithOptionalFields() {
        // Given
        User user = User.builder()
                .emailId("testUser3@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();

        // When
        userCrud.save(user);

        // Then
        assertThat(user.getUserId()).isNotNull();
        Optional<User> userFromDatabase = userCrud.findById(user.getUserId());
        assertThat(userFromDatabase.isPresent()).isTrue();
    }

    // Saving a user with all required fields populated
    @Test
    void testSaveUserWithRequiredFields() {
        // Given
        User user = User.builder()
                .emailId("testUser4@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .password("testPassword")
                .build();

        // When
        userCrud.save(user);

        // Then
        assertThat(user.getUserId()).isNotNull();
        Optional<User> userFromDatabase = userCrud.findById(user.getUserId());
        assertThat(userFromDatabase.isPresent()).isTrue();
    }


    @Test
    void testSaveUserWithExistingEmailId() {
        // Given
        User user1 = User.builder()
                .emailId("testUser5@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();
        userCrud.save(user1);

        User user2 = User.builder()
                .emailId("testUser5@yopmail.com")
                .userRole("admin")
                .firstName("John")
                .lastName("Doe")
                .password("testPassword2")
                .build();

        // When
        Throwable throwable = catchThrowable(() -> userCrud.save(user2));

        // Then
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testSaveUserWithManuallySetUserId() {
        // Given
        User user = User.builder()
                .userId(100)
                .emailId("testUser6@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password("testPassword")
                .build();

        // When
        userCrud.save(user);

        // Then
        assertThat(user.getUserId()).isNotEqualTo(100);
        Optional<User> userFromDatabase = userCrud.findById(user.getUserId());
        assertThat(userFromDatabase.isPresent()).isTrue();
        assertThat(userFromDatabase.get()).isEqualTo(user);
    }

    // Saving a user with empty emailId field
    @Test
    void testSaveUserWithEmptyEmailId() {
        // Given
        User user = User.builder()
                .emailId("")
                .userRole("user")
                .firstName("Marry")
                .password("testPassword")
                .build();

        // When
        userCrud.save(user);

        // Then
        assertThat(user.getUserId()).isNotNull();
        Optional<User> userFromDatabase = userCrud.findById(user.getUserId());
        assertThat(userFromDatabase.isPresent()).isTrue();
    }

    @Test
    void testSaveUserWithNullPassword() {
        // Given
        User user = User.builder()
                .emailId("testUser@yopmail.com")
                .userRole("user")
                .firstName("Marry")
                .lastName("Jane")
                .password(null)
                .build();

        // When
        Throwable throwable = catchThrowable(() -> userCrud.save(user));

        // Then
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }
}