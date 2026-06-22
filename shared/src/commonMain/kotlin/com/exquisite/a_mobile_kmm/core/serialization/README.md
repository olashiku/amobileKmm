# Serialization Utilities

This package contains custom serializers and utilities for handling special JSON serialization
cases.

## EmptyObjectAsNullSerializer

A generic serializer that handles empty JSON objects `{}` as `null` during deserialization. This is
particularly useful for APIs that return empty objects for error cases instead of `null`.

### Problem

Many APIs return responses in this format:

- **Success**: `{"success": true, "data": {...}, "message": "Success"}`
- **Error**: `{"success": false, "data": {}, "message": "Error message"}`

By default, kotlinx.serialization will throw a `JsonDecodingException` when trying to deserialize an
empty object `{}` into a data class with required fields.

### Solution

Use `EmptyObjectAsNullSerializer` to treat empty objects as `null`.

### Usage

#### Step 1: Create a serializer object for your data type

```kotlin
import com.exquisite.dripp.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class MyDataDto(
    val field1: String,
    val field2: Int
)

// Create a serializer object that extends EmptyObjectAsNullSerializer
object MyDataDtoSerializer : EmptyObjectAsNullSerializer<MyDataDto>(MyDataDto.serializer())
```

#### Step 2: Apply the serializer to your response DTO

```kotlin
@Serializable
data class MyResponseDto(
    @Serializable(with = MyDataDtoSerializer::class)
    val data: MyDataDto?,  // Make this nullable
    val message: String,
    val success: Boolean
)
```

### Example

Here's a complete example for a login response:

```kotlin
// Data DTOs
@Serializable
data class LoginDataDto(
    val token: String,
    val user: UserDto
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val full_name: String
)

// Create the serializer
object LoginDataDtoSerializer : EmptyObjectAsNullSerializer<LoginDataDto>(LoginDataDto.serializer())

// Response DTO with serializer applied
@Serializable
data class LoginResponseDto(
    @Serializable(with = LoginDataDtoSerializer::class)
    val data: LoginDataDto?,
    val message: String,
    val success: Boolean,
    val timestamp: String
)
```

Now your code will handle both responses correctly:

- **Success**: `data` will contain the `LoginDataDto` object
- **Error**: `data` will be `null` (converted from `{}`)

### When to Use

Use this serializer whenever:

1. Your API returns empty objects `{}` instead of `null` for error cases
2. You have a `data` field in your response that could be either a full object or empty
3. You want to avoid `JsonDecodingException` when deserializing API responses

### Implementation Details

The serializer works by:

1. Intercepting the JSON deserialization process
2. Checking if the JSON object is empty (`{}`)
3. Returning `null` if empty, or deserializing normally if it contains fields
4. On serialization, it converts `null` back to an empty object `{}` to match the API format
