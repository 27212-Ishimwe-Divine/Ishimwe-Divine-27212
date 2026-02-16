# Question 4 — E-Commerce Product API

Endpoints:
- GET /api/products — all products (optional pagination: ?page={page}&limit={limit})
- GET /api/products/{productId} — product details
- GET /api/products/category/{category} — by category
- GET /api/products/brand/{brand} — by brand
- GET /api/products/search?keyword={keyword} — search name/description
- GET /api/products/price-range?min={min}&max={max} — price range
- GET /api/products/in-stock — products with stockQuantity > 0
- POST /api/products — add product (JSON body)
- PUT /api/products/{productId} — update product
- PATCH /api/products/{productId}/stock?quantity={quantity} — update stock
- DELETE /api/products/{productId} — delete product

Run:
```powershell
mvn -f question4-E-Commerce-api spring-boot:run
```

Notes:
- POST returns 201 Created; GET by id returns 404 if missing; DELETE returns 204.
