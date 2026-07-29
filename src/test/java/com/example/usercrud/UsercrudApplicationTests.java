package com.example.usercrud;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.boot.test.context.SpringBootTest;
import com.example.usercrud.dto.UserRequest;
import com.example.usercrud.dto.UserResponse;
import com.example.usercrud.entity.User;
import com.example.usercrud.repository.UserRepository;
import com.example.usercrud.service.UserService;
@ExtendWith(MockitoExtension.class)
class UsercrudApplicationTests {
UserRequest request; 
User savedUser;

@Mock
UserRepository repository;   // mock repository object
@InjectMocks    // inject this mock repository to service 
UserService service;

 @BeforeEach  // before each function always void 
    void setUp() {
      // comman test arangment (used for all service
        request = new UserRequest();
        request.setUsername("Ankush");
        request.setPassword("12345");

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("Ankush");
        savedUser.setPassword("12345");
    }


@Test
    void createUserTest() {

        // ---------------- Arrange ----------------

        // Whenever repository.save(any User) is called,
        // return savedUser instead of calling a real database.
        when(repository.save(any(User.class)))
                .thenReturn(savedUser);

        // ---------------- Act ----------------

        // Execute the real service method
        UserResponse response = service.createUser(request);

        // ---------------- Assert ----------------

        // Verify repository.save() was called exactly once
        verify(repository).save(any(User.class));
        
        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);
				// Capture the User object passed to repository.save()
        verify(repository).save(captor.capture());
        User capturedUser = captor.getValue();

        // Verify the data sent to the repository is correct or not
        assertNull(capturedUser.getId());
        assertEquals("Ankush", capturedUser.getUsername());
        assertEquals("12345", capturedUser.getPassword());

        // Verify the response returned by the service is correct or not
        assertEquals(1L, response.getId());
        assertEquals("Ankush", response.getUsername());
    }


@Test 
void getUserTest(){
	
	// Arrange
	when(repository.findById(any(Long.class))).thenReturn(Optional.of(savedUser));

	//Act
    UserResponse response =  service.getUserById(1L);
	//
	verify(repository).findById(any(Long.class)); // check once call 
	ArgumentCaptor<Long> captor =
                ArgumentCaptor.forClass(Long.class);
    verify(repository).findById(captor.capture()); // Capture the User object passed to repository.save()
    
	// check correct id search 
	Long id = captor.getValue();
	assertEquals( 1L, id );
	
	// check resposne correct
	assertEquals("Ankush", response.getUsername());
    assertEquals(1L, response.getId());

}


@Test 
void getUserNtFoundTest(){
	
	// Arrange
	when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

  RuntimeException ex =  assertThrows(RuntimeException.class, ()-> service.getUserById(1L));
  assertEquals("User not found", ex.getMessage());

    verify(repository).findById(anyLong());

}

}
