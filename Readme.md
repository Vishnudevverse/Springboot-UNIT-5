### **Part 1: User Registration and Faculty Login**

#### **Step 1: Register Faculty**

First, we'll create a user with the `FACULTY` role.

*   **Method:** `POST`
*   **URL:** `http://localhost:8080/auth/register`
*   **Body (raw, JSON):**

```json
{
    "username": "prof_dumbledore",
    "password": "password123",
    "role": "FACULTY"
}
```

#### **Step 2: Register Student**

Next, create a user with the `STUDENT` role. We'll use this user later.

*   **Method:** `POST`
*   **URL:** `http://localhost:8080/auth/register`
*   **Body (raw, JSON):**

```json
{
    "username": "harry_potter",
    "password": "password456",
    "role": "STUDENT"
}
```

#### **Step 3: Login as Faculty**

Now, log in as the faculty member to get an authentication token.

*   **Method:** `POST`
*   **URL:** `http://localhost:8080/auth/login`
*   **Body (raw, JSON):**

```json
{
    "username": "prof_dumbledore",
    "password": "password123"
}
```

### **Part 2: Creating an Assignment as Faculty**

#### **Step 4: Save the Faculty's JWT (Bearer Token)**

After you send the login request in Step 3, Postman will show a response body, which should contain the JWT. It will look something like this:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcm9mX2R1bWJsZWRvcmUiLCJpYXQiOjE2N..."
}
```

1.  **Copy the token value** from the response body.
2.  Go to the **Authorization** tab for your collection or for the next request (Step 5).
3.  Set the **Type** to **Bearer Token**.
4.  **Paste the copied token** into the **Token** field on the right. 

#### **Step 5: Create Assignment (as Faculty)**

Now, use the faculty's token to authorize the creation of a new assignment in the `Submission-Service`.

*   **Method:** `POST`
*   **URL:** `http://localhost:8081/api/assignments`
*   **Authorization:** Make sure the **Bearer Token** from Step 4 is applied to this request.
*   **Body (raw, JSON):**

```json
{
    "title": "Potions Midterm Essay",
    "description": "A 1000-word essay on the properties of Polyjuice Potion.",
    "dueDate": "2026-12-15"
}
```

After sending this request, the response should contain the details of the newly created assignment, including its `id`. **Make a note of the assignment `id`** (e.g., `1`), as you will need it for Step 8.

### **Part 3: Submitting the Assignment as Student**

#### **Step 6: Login as Student**

Log in as the student to get their unique authentication token.

*   **Method:** `POST`
*   **URL:** `http://localhost:8080/auth/login`
*   **Body (raw, JSON):**

```json
{
    "username": "harry_potter",
    "password": "password456"
}
```

#### **Step 7: Save the Student's JWT**

Just as you did in Step 4, copy the new token from the login response. Go to the **Authorization** tab in Postman and **replace the faculty's token with the new student's token**.

#### **Step 8: Submit Assignment (as Student)**

Use the student's token to submit their work for the assignment you created earlier.

*   **Method:** `POST`
*   **URL:** `http://localhost:8081/api/submissions`
*   **Authorization:** Make sure the **Bearer Token** is now the student's token from Step 7.
*   **Body (raw, JSON):**
    *(Replace `1` with the actual `id` of the assignment from Step 5)*

```json
{
    "assignmentId": 1,
    "submissionLink": "https://docs.google.com/document/d/12345/edit"
}
```

### **Part 4: Verifying the Submission as Faculty**

#### **Step 9: View Submissions (as Faculty)**

Finally, switch back to the faculty's token to view all submissions for the assignment.

1.  Go back to the **Authorization** tab and paste the **faculty's token** (from Step 4) back into the **Token** field.
2.  Send the following request:

*   **Method:** `GET`
*   **URL:** `http://localhost:8081/api/assignments/1/submissions`
    *(Replace `1` with the actual `id` of your assignment)*
*   **Authorization:** Make sure you are using the **faculty's Bearer Token**.
*   **Body:** None.

This request should return a list containing the submission made by the student in Step 8, confirming the entire workflow is functioning correctly.
