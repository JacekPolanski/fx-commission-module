# 💼 FX Commission Module

A Spring Boot module for managing FX commission (spread) in currency transactions.

![Java](https://img.shields.io/badge/Java-21-007396?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=spring-boot)
![Build](https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle)
![Code Style](https://img.shields.io/badge/Checkstyle-enabled-4B32C3?logo=checkstyle)
![License](https://img.shields.io/badge/License-MIT-informational)

This repository implements the practical task:

> Build a banking app module for managing FX commission in FX transactions, with support for accounts, static currency rates for demo purposes, and two spread types: fixed and dynamic based on historical thresholds. Spread is added to the transaction amount, invisible to the destination account, and posted as revenue to a technical account. Users can manage spread parameters.

---

## Contents

- [Project Goal](#project-goal)
- [Domain Overview](#domain-overview)
- [Architecture Overview](#architecture-overview)
- [Running the App](#running-the-app)
- [API Overview](#api-overview)
    - [Accounts API](#accounts-api)
    - [Transactions API](#transactions-api)
    - [Technical Account API](#technical-account-api)
- [Commission Strategies](#commission-strategies)
- [Error Handling](#error-handling)
- [Postman Collection (import and usage)](#postman-collection)
- [Code Style and Checkstyle](#code-style-and-checkstyle)
- [Tests](#tests)
- [Notes and Assumptions](#notes-and-assumptions)
- [Contributing](#contributing)
- [License](#license)

---

## Project Goal

Provide a clear, testable module to:
- Manage single-currency accounts and transfers between them.
- Apply FX spread (commission) in transactions.
- Support two spread strategies:
    - Fixed value spread (per account).
    - Dynamic spread based on thresholds (e.g., transaction amount tiers).
- Allow user-driven spread configuration (add/remove levels, change strategy).
- Book spread into a technical account while keeping destination credit net of spread (spread is not visible on the destination account).

---

## Domain Overview

- Account: single currency, identified by UUID + IBAN.
- Spread:
    - Levels: map of thresholds to percentages (e.g., {0 -> 0.05, 1000 -> 0.10}).
    - Type:
        - FIXED: always uses the first configured level percentage.
        - DYNAMIC: selects the highest threshold not greater than the transaction amount.
- Transaction:
    - Initiation and approval flow.
    - Applies spread on the source side; destination receives principal without spread.
    - Spread is posted to a technical account (revenue).

Currencies/rates can be static for demo purposes.

---

## Architecture Overview

- Spring Boot (Java 21), REST controllers.
- Application layer exposes use-cases for:
    - Account CRUD-like operations and spread management.
    - Transaction initiation and approval.
- Strategies:
    - FixedStrategy: commission = amount × fixedPercent / 100.
    - TransactionAmountStrategy (dynamic): commission = amount × matchedTierPercent / 100 (HALF_UP, 2 decimals).

---

## Running the App

- Java 21 required.
- From the project root:
    - Gradle: `./gradlew bootRun`
- Default port: `8080`

### Quick Start

- Start the app: `./gradlew bootRun`
- Import Postman collection from `postman/FxCommissionModule.postman_collection.json`
- Typical flow:
    1) Create Account
    2) Configure Spread (levels / strategy)
    3) Initiate Transaction
    4) Approve Transaction
    5) Verify balances and commission booking


---

## API Overview

Base URL:
- `http://localhost:8080`

<details>
<summary><strong>Accounts API</strong></summary>

Base path: `/api/account`

- GET `/api/account` — list active accounts.
- POST `/api/account` — create an account.
- GET `/api/account/{id}` — get account by UUID.
- PUT `/api/account/{id}/add-spread-level` — add or update a spread level.
- DELETE `/api/account/{id}/remove-spread-level` — remove a spread level.
- PUT `/api/account/{id}/update` — update account info (e.g., spread type).

Response model (AccountDto):
- `id: UUID`
- `name: string`
- `iban: string`
- `currency: string (ISO 4217)`
- `balance: string (decimal as string)`
- `levels: map<int, decimal>` (threshold -> percentage)
- `spreadType: string`
</details>

<details>
<summary><strong>Transactions API</strong></summary>

Base path: `/api/transaction`

- GET `/api/transaction` — list all transactions.
- POST `/api/transaction/initiate` — initiate a transaction (pending approval).
- POST `/api/transaction/{id}/approve` — approve and post the transaction.

Response model (TransactionResponseDto):
- Contains identifiers, status, amounts, applied spread, timestamps, etc.
</details>

<details>
<summary><strong>Technical Account API</strong></summary>

Base path: `/api/technical-account`

- GET `/api/technical-account` — list technical account ledger entries (commission postings).
- Use this endpoint to inspect inflows to the technical account — this is where commissions are recorded per transaction.

Response model (TechnicalAccountDto):
- `id: UUID`
- `transactionId: UUID`
- `credit: string (decimal as string)`
- `currency: string (ISO 4217)`
- `description: string`
- `createdAt: string (ISO-8601 datetime)`
- `updatedAt: string (ISO-8601 datetime)`
</details>

---

## Commission Strategies

- Fixed value (per account):
    - Uses the first configured spread level percentage for any amount.
    - Example: levels `{0 -> 1.25}` means commission = amount × 1.25% (HALF_UP).
- Dynamic value (amount thresholds):
    - Chooses the highest threshold ≤ transaction amount.
    - Example:
        - levels `{0 -> 0.05, 1_000 -> 0.10, 10_000 -> 0.20}`
        - For amount `10_000`, uses `0.20%`.

Rounding:
- HALF_UP with suitable scale for percentages (typically 2 decimals).

Accounting rules:
- Spread is added to the source-side total cost, not credited to destination.
- Spread is booked as revenue to a technical account.
- You can view the recorded commission inflows under: `GET /api/technical-account` (Technical Account API).

---

## Error Handling

- API returns HTTP 400 with a message when validation/business errors occur.
- In general:
    - 200/201 for success,
    - 400 for invalid input/business rule violation,
    - 404 if an entity is missing (depending on implementation),
    - 500 for unexpected errors.

---

## Postman Collection

A ready-to-use Postman collection is included in the repository:
- Path: `postman/FxCommissionModule.postman_collection.json`

How to use:
1. Open Postman.
2. Import the collection file from the path above.
3. Set collection variables:
    - `baseUrl` (default: `http://localhost:8080`)
    - `accountId` (UUID of an existing account; used by some requests)
    - `transactionId` (UUID of an existing transaction; used by approval)
4. Execute requests in sequence:
    - Create Account → Add Spread Levels / Update Spread Type → Initiate Transaction → Approve Transaction → List endpoints.

Tip:
- You can capture response `id` fields and set them as `accountId`/`transactionId` variables for subsequent calls.

---

## Code Style and Checkstyle

This repository includes a Checkstyle configuration to maintain a consistent Java code style:
- Config file: `config/checkstyle/checkstyle.xml`
- Charset: UTF-8
- Default severity: warning

Highlights:
- Formatting:
    - Max line length: 120 characters (package/import lines ignored)
    - Newline at end of file required
    - Braces: opening at end of line, closing on its own line; braces required for control statements
    - Whitespace checks around tokens and after commas/keywords; no forced line wrap
- Imports: no star imports; unused and redundant imports disallowed
- Naming: checks for type/file name alignment, local variables, parameters, members, and catch parameters
- Modifiers: correct order; redundant modifiers disallowed
- Blocks/statements: empty blocks/statements flagged (limited exceptions), fall-through in switch detected
- Javadoc: not enforced (disabled)

Using in IntelliJ IDEA:
- Ensure the Checkstyle-IDEA plugin is installed.
- Point the plugin to the project config: `config/checkstyle/checkstyle.xml`.
- Run a scan from the Checkstyle tool window, or via Analyze/Inspections with a profile that uses Checkstyle.

CI (optional):
- Run Checkstyle in your pipeline (e.g., via Checkstyle CLI) using the same config to fail builds on violations.

---

## Tests

This project includes unit tests for the commission strategies to ensure correctness of spread calculation:
- Fixed strategy tests: validate percentage application, zero-amount behavior, and large amounts.
- Dynamic (amount-threshold) strategy tests: validate highest matching threshold selection, exact threshold behavior, rounding (HALF_UP to two decimals), and zero-amount cases.

How to run:
- All tests: `./gradlew test`
- Single module (if multi-module build is used): run `test` in the specific module directory.

Typical test report locations (Gradle):
- `build/test-results/test` (XML)
- `build/reports/tests/test/index.html` (HTML report)

---

## Notes and Assumptions

- Currency conversion rates can be considered static or mocked for demo purposes.
- Spread levels are expressed in percentages (e.g., `1.25` means `1.25%`).
- For FIXED spread, only the first configured level is used.
- For DYNAMIC spread, the matched level is the highest threshold ≤ amount.
- Spread is accounted to a technical account and not shown on the destination account balance.
- IDs are UUIDs; balances are decimals represented as strings in responses.

---

## Contributing

Contributions are welcome!
- Fork the repo, create a feature branch, follow code style (Checkstyle), add tests where applicable.
- Open a pull request with a clear description and context.

## License

This project is released under the MIT License.
