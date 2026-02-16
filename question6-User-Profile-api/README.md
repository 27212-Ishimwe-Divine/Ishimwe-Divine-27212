# Bonus — User Profile API

Endpoints:
- POST /api/user-profiles — create profile (returns ApiResponse, 201)
- GET /api/user-profiles — list profiles (returns ApiResponse)
- GET /api/user-profiles/{userId} — get profile by ID
- PUT /api/user-profiles/{userId} — update profile
- DELETE /api/user-profiles/{userId} — delete profile
- GET /api/user-profiles/search/username?username={username}
- GET /api/user-profiles/search/country?country={country}
- GET /api/user-profiles/search/age-range?minAge={min}&maxAge={max}
- PUT /api/user-profiles/{userId}/activate — activate
- PUT /api/user-profiles/{userId}/deactivate — deactivate

Run:
```powershell
mvn -f question6-User-Profile-api spring-boot:run
```

Notes:
- Endpoints return an `ApiResponse<T>` object; HTTP status codes match success/failure (201/200/404).
