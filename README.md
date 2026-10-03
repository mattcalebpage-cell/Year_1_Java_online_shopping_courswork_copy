# Board Game Management System

A Java console application for a board game and accessories store, supporting two user roles — admin and customer — with stock management, a shopping basket, and polymorphic payment processing. Built as coursework, with a file-based (text file) backend rather than a database.

## Features

**Admin**
- View the full product catalogue
- Add new products (board games or accessories), with input validation on product ID (unique, 4 digits), category, type, price format, and stock quantity

**Customer**
- Browse the catalogue
- Add products to a basket by ID, with stock-availability checks
- View basket contents and running total
- Checkout via PayPal or Credit Card, with per-item stock validation against basket quantities before payment is accepted
- Clear the basket
- Look up a product by ID, or search/filter accessories by compatibility

## Design

- **`Product`** is an abstract base class, extended by **`BoardGame`** (type, max players) and **`Accessory`** (type, compatibility) — each overrides `toString()` for admin-facing output and provides a separate `custToString()` for the customer-facing view (hiding purchase cost).
- **`Payment_Method`** is an interface implemented by **`Paypal`** and **`CreditCard`**, each producing a **`Receipt`** — new payment methods can be added without changing the checkout logic that calls them.
- **`User`** and **`Address`** model accounts, loaded from a simple user file; **`ProductCategory`** is an enum (`BOARDGAME`, `ACCESSORY`).
- **`Main`** loads users and products from text files at startup, then routes to either **`AdminCLI`** or **`CustomerCLI`** based on the selected user's role.

## Data files

Semicolon-delimited `.txt` files stand in for a database:

- `UserAccounts.txt` — `user ID; name; house number; postcode; city; role`
- `Stock.txt` — `product ID; category; type; name; price; quantity in stock; purchase cost; extra info` (max players for board games, compatibility for accessories)

User "login" is done by selecting from a numbered list of loaded accounts at startup — there is no password check.

## Running it

Requires Java 21 (built against `JavaSE-21`). `UserAccounts.txt` and `Stock.txt` must be in the working directory the program is run from.

```
java -jar bgms.jar
```

Or import as an Eclipse project (`.project` / `.classpath` included) and run `CLIBasis.Main`.

## Known limitations

- No password/authentication — any listed account can be selected directly
- Card numbers and security codes are read and used as plain strings, with no masking or secure handling
- Stock file writes assume well-formed data; a malformed line is skipped with a warning rather than halting the program
