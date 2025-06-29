package service;

import DTO.employeeuser;
import entity.UserEntity;
import repository.UserRepository;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    public UserService() {
        this.userRepository = new UserRepository();
        this.modelMapper = new ModelMapper();
    }
    
    public boolean createUser(employeeuser user, UserEntity.UserRole role) {
        try {
            UserEntity entity = new UserEntity();
            entity.setEmail(user.getEmail());
            entity.setPassword(user.getPassword()); // In production, hash the password
            entity.setRole(role);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setIsActive(true);
            
            userRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    
    public UserEntity authenticateUser(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            // Update last login
            UserEntity userEntity = user.get();
            userEntity.setLastLogin(LocalDateTime.now());
            userRepository.save(userEntity);
            return userEntity;
        }
        return null;
    }
    
    public boolean updateUser(employeeuser user) {
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                UserEntity entity = existingUser.get();
                entity.setPassword(user.getPassword()); // In production, hash the password
                userRepository.save(entity);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    
    public UserEntity findUserByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
    
    public boolean deactivateUser(String email) {
        try {
            Optional<UserEntity> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                UserEntity entity = user.get();
                entity.setIsActive(false);
                userRepository.save(entity);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error deactivating user: " + e.getMessage());
            return false;
        }
    }
}