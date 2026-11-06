# Insurance Policy Microservice (SOA Skill In-Sem Exam 2)

**Student Details:**
- **Roll Number:** 2400030709
- **Question Number:** 29
- **Entity:** InsurancePolicy
- **Service Name:** insurance-policy-service
- **Port:** 2006
- **Database:** PostgreSQL (`soataskdb`)

---

## 1. Entity Attributes
The `InsurancePolicy` entity (`insurancepolicy_table`) includes:
- `insurancePolicyId` : `Long` (Primary Key, Generated Identity)
- `name` : `String`
- `type` : `String`
- `status` : `String`
- `location` : `String`
- `createdAt` : `LocalDateTime` (`@CreationTimestamp`)
- `updatedAt` : `LocalDateTime` (`@UpdateTimestamp`)

---

## 2. Implemented Endpoints

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/insurancepolicy/` | Service Health / Welcome | Public |
| `GET` | `/insurancepolicy/test` | Public Test API | Public |
| `POST` | `/insurancepolicy/login` | Generate JWT Bearer Token | Public |
| `POST` | `/insurancepolicy/add` | Insert new Insurance Policy | Bearer JWT |
| `GET` | `/insurancepolicy/viewall` | Display All Policies | Bearer JWT |
| `GET` | `/insurancepolicy/displayall` | Display All Policies (Alias) | Bearer JWT |
| `GET` | `/insurancepolicy/view/{id}` | Display Policy By ID | Bearer JWT |
| `GET` | `/insurancepolicy/display/{id}` | Display Policy By ID (Alias) | Bearer JWT |
| `PUT` | `/insurancepolicy/update` | Update Policy Details | Bearer JWT |
| `DELETE` | `/insurancepolicy/delete/{id}` | Delete Policy By ID | Bearer JWT |
| `GET` | `/insurancepolicy/findbyname/{name}` | Custom Query: findByName() | Bearer JWT |
| `GET` | `/insurancepolicy/findbytype/{type}` | Custom Query: findByType() | Bearer JWT |
| `GET` | `/swagger-ui/index.html` | Swagger OpenAPI UI Documentation | Public |

---

## 3. Testing Implementations

### A. JUnit Testing (4 Demonstrations)
1. **Demo 1 (`StudentTest.java`)**: Basic Assertions (`@Test`, `@BeforeAll`, `@BeforeEach`, `assertEquals`, `assertTrue`, `assertThrows`).
2. **Demo 2 (`StringUtilTest.java`)**: String operations testing (reverse, lowercase, uppercase, isEmpty).
3. **Demo 3 (`PaymentServiceTest.java`)**: Boundary value and null validation tests.
4. **Demo 4 (`InsurancePolicyServiceTest.java`)**: Mockito unit testing for Service Layer.

### B. Advanced Testing
1. **Spring Data JPA Repository Testing (`InsurancePolicyRepositoryTest.java`)**:
   - Matches `UserRepositoryTest.java`
   - Uses `@DataJpaTest` & `@AutoConfigureTestDatabase(replace = NONE)`
   - Covers: Insert, Find All, Find By ID, findByName(), findByType(), Update, Delete, Database Connection Check.
2. **Integration & Security Testing (`InsurancePolicyIntegrationTest.java`)**:
   - Matches `UserServiceIntegrationTest.java`
   - **Concept 1: JWT Security Testing** (Login -> JWT -> Protected API, Invalid JWT -> 401, Missing JWT -> 401).
   - **Concept 2: Service-to-Service Contract Testing** (Contract format validation).
   - **Concept 3: Load Balancing Testing** (Instance discovery and request distribution).
   - **Public Test API verification** (`/insurancepolicy/test` -> 200).

---

## 4. How to Run Tests
```bash
# Run all tests
mvn test

# Run Repository Tests
mvn test -Dtest=InsurancePolicyRepositoryTest

# Run Integration & JWT Security Tests
mvn test -Dtest=InsurancePolicyIntegrationTest

# Run 4 JUnit Demonstrations
mvn test -Dtest=StudentTest,StringUtilTest,PaymentServiceTest,InsurancePolicyServiceTest
```

---

## 5. Docker Deployment Tasks

### A. Build Backend Image
```bash
docker build -t sasankjiguru/insurancepolicy-backend:latest .
```

### B. Push to Docker Hub
```bash
docker login
docker push sasankjiguru/insurancepolicy-backend:latest
```

### C. Run with Docker Compose
```bash
# Starts PostgreSQL (postgres:15-alpine) and Backend container
docker-compose up -d

# Verify running containers
docker-compose ps

# Check backend container logs
docker-compose logs -f backend
```

### D. AWS EC2 Deployment
```bash
# On your AWS EC2 instance:
sudo apt update && sudo apt install -y docker.io docker-compose
sudo systemctl start docker

# Pull latest image from Docker Hub
docker pull sasankjiguru/insurancepolicy-backend:latest

# Run containers using docker-compose.yml
docker-compose up -d
```
