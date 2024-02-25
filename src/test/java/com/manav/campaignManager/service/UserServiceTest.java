package com.manav.campaignManager.service;

import com.manav.campaignManager.entity.User;
import com.manav.campaignManager.exceptionHandler.exceptions.InvalidUserRequest;
import com.manav.campaignManager.exceptionHandler.exceptions.UserAlreadyExists;
import com.manav.campaignManager.repository.UserCrud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserCrud mockUserCrud;

    @Test
    public void test_getAllUsers() {
        // Given
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(User.builder().firstName("John").lastName("Doe").userRole("user").emailId("john@example.com").build());
        expectedUsers.add(User.builder().firstName("John").lastName("Doe").userRole("user").emailId("john2@example.com").build());

        when(mockUserCrud.findAll()).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.getAllUsers();

        // Then
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void test_addUser_existingUser() {
        // Given
        User existingUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId("john@example.com")
                .password("password123")
                .build();

        when(mockUserCrud.findByEmailId(existingUser.getEmailId())).thenReturn(Optional.of(existingUser));

        // When/Then
        assertThatThrownBy(() -> userService.addUser(existingUser)).isInstanceOf(UserAlreadyExists.class);
    }

    @Test
    public void test_addUser_notNullUserId() {
        // Given
        User newUser = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId("john@example.com")
                .password("password123")
                .build();

        // When/Then
        assertThatThrownBy(() -> userService.addUser(newUser)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void test_addUser_nullFirstName() {
        // Given
        User newUser = User.builder()
                .firstName(null)
                .lastName("Doe")
                .userRole("user")
                .emailId("john@example.com")
                .password("password123")
                .build();

        // When/Then
        assertThatThrownBy(() -> userService.addUser(newUser)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void test_addUser_emptyEmailId() {
        // Given
        User newUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId("")
                .password("password123")
                .build();

        // When/Then
        assertThatThrownBy(() -> userService.addUser(newUser)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void test_addUser_emptyPassword() {
        // Given
        User newUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId("john@example.com")
                .build();

        // When/Then
        assertThatThrownBy(() -> userService.addUser(newUser)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void test_addUser_existingEmailId() {
        // Given
        User existingUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId("john@example.com")
                .password("password123")
                .build();

        when(mockUserCrud.findByEmailId(existingUser.getEmailId())).thenReturn(Optional.of(existingUser));

        // When/Then
        assertThatThrownBy(() -> userService.addUser(existingUser)).isInstanceOf(UserAlreadyExists.class);
    }

    @Test
    public void test_findUserById_validId() {
        // Given
        Integer userId = 1;
        User expectedUser = User.builder().firstName("John").lastName("Doe").userRole("user").emailId("john@example.com").password("password123").build();

        when(mockUserCrud.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        User actualUser = userService.findUserById(userId);

        // Then
        assertThat(expectedUser).isEqualTo(actualUser);
    }

    @Test
    public void findUserById_null_user_id(){
        assertThatThrownBy(()-> userService.findUserById(null)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void findUserById_negative_user_id(){
        assertThatThrownBy(()-> userService.findUserById(-2)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void test_findUserByEmailId_validId() {
        // Given
        String email = "john@example.com";
        User expectedUser = User.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .userRole("user")
                .emailId(email)
                .password("password123")
                .build();

        when(mockUserCrud.findByEmailId( email )).thenReturn(Optional.of(expectedUser));

        // When
        User actualUser = userService.findUserByEmailId( email );

        // Then
        assertThat(expectedUser).isEqualTo(actualUser);
    }

    @Test
    public void findUserByEmailId_null_user_id(){
        assertThatThrownBy(()-> userService.findUserByEmailId(null)).isInstanceOf(InvalidUserRequest.class);
    }

    @Test
    public void findUserByEmailId_negative_user_id(){
        assertThatThrownBy(()-> userService.findUserByEmailId("")).isInstanceOf(InvalidUserRequest.class);
    }
}