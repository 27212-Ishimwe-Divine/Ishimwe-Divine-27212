package question6.question6_User_Profile_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user-profiles")
@CrossOrigin(origins = "*")
public class UserProfileController {

    private List<UserProfile> userProfiles = new ArrayList<>();
    private Long nextId = 1L;

    // CREATE - Add a new user profile
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUserProfile(@RequestBody UserProfile userProfile) {
        userProfile.setUserId(nextId++);
        userProfiles.add(userProfile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User profile created successfully", userProfile));
    }

    // READ - Get all user profiles
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUserProfiles() {
        if (userProfiles.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No user profiles found", userProfiles));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User profiles retrieved successfully", userProfiles));
    }

    // READ - Get a specific user profile by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserProfileById(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile retrieved successfully", userProfile.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    // UPDATE - Update a user profile
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUserProfile(@PathVariable Long userId,
            @RequestBody UserProfile updatedProfile) {
        Optional<UserProfile> existingProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (existingProfile.isPresent()) {
            UserProfile profile = existingProfile.get();
            profile.setUsername(updatedProfile.getUsername());
            profile.setEmail(updatedProfile.getEmail());
            profile.setFullName(updatedProfile.getFullName());
            profile.setAge(updatedProfile.getAge());
            profile.setCountry(updatedProfile.getCountry());
            profile.setBio(updatedProfile.getBio());
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated successfully", profile));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    // DELETE - Delete a user profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            userProfiles.remove(userProfile.get());
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "User profile deleted successfully", "User " + userId + " deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    // SEARCH - Search by username
    @GetMapping("/search/username")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByUsername(@RequestParam String username) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, "No user profiles found with username: " + username, results));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User profiles found", results));
    }

    // SEARCH - Search by country
    @GetMapping("/search/country")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByCountry(@RequestParam String country) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No user profiles found in country: " + country, results));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User profiles found", results));
    }

    // SEARCH - Search by age range
    @GetMapping("/search/age-range")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchByAgeRange(@RequestParam int minAge,
            @RequestParam int maxAge) {
        List<UserProfile> results = userProfiles.stream()
                .filter(up -> up.getAge() >= minAge && up.getAge() <= maxAge)
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true,
                    "No user profiles found in age range " + minAge + " to " + maxAge, results));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User profiles found", results));
    }

    // ACTIVATE - Activate a user profile
    @PutMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> activateUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setActive(true);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile activated successfully", profile));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    // DEACTIVATE - Deactivate a user profile
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserProfile>> deactivateUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> userProfile = userProfiles.stream()
                .filter(up -> up.getUserId().equals(userId))
                .findFirst();

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setActive(false);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile deactivated successfully", profile));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User profile not found", null));
        }
    }

    // Get active users only
    @GetMapping("/filter/active")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getActiveUsers() {
        List<UserProfile> activeUsers = userProfiles.stream()
                .filter(UserProfile::isActive)
                .collect(Collectors.toList());

        if (activeUsers.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No active user profiles found", activeUsers));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Active user profiles retrieved successfully", activeUsers));
    }

    // Get inactive users only
    @GetMapping("/filter/inactive")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getInactiveUsers() {
        List<UserProfile> inactiveUsers = userProfiles.stream()
                .filter(up -> !up.isActive())
                .collect(Collectors.toList());

        if (inactiveUsers.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No inactive user profiles found", inactiveUsers));
        }
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Inactive user profiles retrieved successfully", inactiveUsers));
    }
}
