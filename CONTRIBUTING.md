# Contributing to Sakshi SDK

Thank you for your interest in contributing to **Sakshi SDK**!

## Before You Start

**Before making any changes or starting development, you must carefully read the complete [Sākṣī Contribution Guidelines](https://sakshi-docs.gitbook.io/space/developer-guide/contributing/contributing).** Read the entire document, understand all its instructions, requirements, and contribution policies, and follow them throughout your contribution.

Do not skip any sections or start implementing changes without understanding the documented guidelines. All contributions must comply with the central contribution guidelines as well as the repository-specific instructions provided below.

## Code of Conduct

All contributors are expected to adhere to our [Code of Conduct](CODE_OF_CONDUCT.md).

## Development Setup & Guidelines

1. **Environment Requirements**:
   - JDK 21
   - Android SDK 37 (API level 37)
   - Gradle 9.6.1

2. **Architecture Rules**:
   - Please read [AGENT.md](AGENT.md) before making any code modifications.
   - **Sakshi SDK is headless**: No UI, Activities, Fragments, Compose, or XML layouts.
   - **Responsibility Boundaries**: Sakshi SDK performs IPC communication only. It does not perform file copying, timer management, or database storage.
   - **Developer Documentation**: Carefully read both the [Developer Guide](https://sakshi-docs.gitbook.io/space/developer-guide) and the [Developer Reference](https://sakshi-docs.gitbook.io/space/developer-reference) before making changes. Follow the documented architecture, development workflows, API contracts, and component boundaries.
   - **SDK API Reference**: Refer to the [SDK API Reference](https://sakshi-docs.gitbook.io/space/developer-reference/api) for the complete list of available APIs and instructions on how to use them. Ensure that all SDK changes remain consistent with the documented API contracts.
   - **Error Handling**: Read the [Error Codes Reference](https://github.com/RajnishKMehta/Sakshi-Docs/blob/main/developer-reference/error-codes.md) and follow its documented error-handling conventions. Prefer existing error codes whenever appropriate. If a new error type or error code is necessary, ensure it is properly documented.

3. **Coding Standards**:
   - Kotlin language only.
   - Public APIs must be fully documented with KDoc. Add or update KDoc whenever introducing or modifying public APIs, classes, functions, or other relevant code.
   - Maintain strict explicit API mode (`-Xexplicit-api=strict`).

## Pull Request Process

1. Fork the repository and create your branch from `main`.
2. Ensure your code passes all lint checks and compiles cleanly without warnings.
3. Run relevant tests and verify that your changes do not break existing functionality.
4. Ensure your implementation follows the central contribution guidelines and all repository-specific requirements.
5. Submit your Pull Request using our [PR Template](.github/PULL_REQUEST_TEMPLATE.md).

   Provide a clear description of your changes, their purpose, and the relevant testing performed.

   **Documentation Updates:** If your code changes require any documentation updates, you must make those updates as part of the same contribution. Alongside your main SDK Pull Request, submit a separate Pull Request to the central [Sakshi-Docs repository](https://github.com/RajnishKMehta/Sakshi-Docs). Both Pull Requests should be submitted together so that the implementation and its documentation remain consistent.

   This requirement applies to all documentation changes, including API references, error codes, guides, architecture documentation, and other relevant documentation. If you introduce a new error code, its corresponding documentation must also be updated in the same contribution.
