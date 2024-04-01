# Result Pattern Library

This library is inspired by [FluentResults](https://github.com/altmann/FluentResults) and aims to reduce the reliance on
exceptions for controlling the flow of code, particularly replacing the traditional use of try-catch blocks for error
handling.

## Features

The `Result` pattern encapsulates the outcome of operations in a robust manner, supporting the following key features:

- **Status Indication:** Each `Result` instance denotes whether an operation was successful or failed.
- **Return Value Handling:** It holds the return value of an operation. For operations without a return value (void
  operations), a special `NoValue` class representation is used. Attempting to retrieve a value from `Result<NoValue>`
  results in an exception.
- **Reasons for Outcome:** The `Result` can contain reasons for its status, allowing either `Success` or `Failure`
  states, with support for custom `Reason` implementations for detailed error handling and messaging.

## Usage

### Creating `Result` Instances

#### Successful

```java
import com.vincentdao.result.NoValue;
import com.vincentdao.result.Result;

public static void main(String[] args) {
    // Successful Result with value
    Result<Integer> valueResult = Result.successful()
            .withValue(1);
    // Successful Result with no value
    Result<NoValue> noValueResult = Result.successful()
            .withNoValue();
}
```

#### Failed

```java
import com.vincentdao.result.NoValue;
import com.vincentdao.result.Result;

public static void main(String[] args) {
    // Failed Result
    Result<Integer> failedValueResult = Result.failed();
    // Can also represent no-value one
    Result<NoValue> failedNoValue = Result.failed();
}
```

### Working with `Result`'s Value

```java
import com.vincentdao.result.Result;

public static void main(String[] args) {
    Result<Integer> result = Result.successful()
            .withValue(1);
    // Get the value
    Integer value = result.value();
}
```

### Working with `Result`'s Reasons

#### Adding Reasons

```java
import com.vincentdao.result.Result;
import com.vincentdao.result.trace.DefaultSuccess;
import com.vincentdao.result.trace.Success;
import java.util.Collection;
import java.util.LinkedList;

public static void main(String[] args) {
    Result<Integer> result = Result.successful()
            .withValue(1);
    // Default success with message
    result.withSuccessMessage("Success message");
    // Success instance
    result.withSuccess(new DefaultSuccess("Success message"));
    // Collection of Success
    Collection<Success> successes = new LinkedList<>();
    successes.add(new DefaultSuccess("Success message."));
    result.withSuccesses(successes);

    // Same operations above for Failure & Reason...
}
```

#### Retrieving Reasons

```java
import com.vincentdao.result.Result;
import com.vincentdao.result.trace.ExceptionalFailure;
import com.vincentdao.result.trace.Failure;
import com.vincentdao.result.trace.Reason;
import com.vincentdao.result.trace.Success;
import java.util.Collection;

public static void main(String[] args) {
    Result<Integer> result = Result.failed();
    // Get all reasons
    Collection<Reason> reasons = result.reasons();
    // Only Success reasons
    Collection<Success> successes = result.successes();
    // Only Failure reasons
    Collection<Failure> failures = result.failures();
    // Custom filtering
    Collection<Reason> filteredReasons = result
            .reasonsFiltered(reason -> reason instanceof ExceptionalFailure);
}
```

#### Adding `Failure` to a successful `Result`:

Note: adding a `Failure` to the currently successful `Result` will make it a failed `Result`.

```java
import com.vincentdao.result.Result;

public static void main(String[] args) {
    Result<Integer> successfulResult = Result.successful()
            .withValue(1);
    // Special case! Adding a Failure to the currently successful Result will make it a failed Result!
    successfulResult.withFailureMessage("Failure message.");
    System.out.println(successfulResult.isSuccessful());      // false
    System.out.println(successfulResult.isFailed());          // true
}
```

## Handling `Result` Instances

When receiving a `Result` instance, you can inspect its status, retrieve the value if the operation was successful, or
examine the failed reasons if not.

```java
import com.vincentdao.result.NoValue;
import com.vincentdao.result.Result;

public static void main(String[] args) {
    Result<NoValue> result = doSomething();
    if (result.isSuccessful()) {
        // Logic for successful operation
    } else {
        // Logic for failed operation
    }
}
```

## Custom `Reason` Implementations

Although the library provides `Success` and `Failure` as default reasons for operation outcomes, it is possible to
implement custom reasons by extending these base classes for more granular control over error representation.

```java
import com.vincentdao.result.trace.Failure;

public class CustomFailureReason extends Failure {
    // Implementation details
}
```

## Thread Safety

Note that `Result` instances are not thread-safe and are intended to be used within the context of a single operation.
They should be handled accordingly to avoid concurrency issues.

## License

This project is licensed under [MIT License](LICENSE).