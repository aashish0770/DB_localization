# Shopping Cart Application with Database Localization

A JavaFX shopping cart application that reads UI messages from a MariaDB database and saves cart data with multilingual support.

---

## Prerequisites

- Java 17
- Maven
- MariaDB / MySQL
- Vs Code

---

## Database Setup

1. Open your MariaDB client (e.g. HeidiSQL, DBeaver, or terminal)
2. Run the following SQL to create the database and tables:

```sql
CREATE DATABASE IF NOT EXISTS shopping_cart_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;

CREATE TABLE IF NOT EXISTS cart_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_items INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_items_id INT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (cart_items_id) REFERENCES cart_records(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
);
```

3. Insert localization data for all supported languages:

```sql
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.itemName', 'Item Name', 'en'),
('prompt.itemPrice', 'Item Price', 'en'),
('prompt.itemQuantity', 'Item Quantity', 'en'),
('total.bill', 'Total Bill: ', 'en'),
('prompt.displayButton', 'Display Cart', 'en'),
('prompt.addItem', 'Add Item', 'en'),
('field.name', 'Name', 'en'),
('field.price', 'Price', 'en'),
('field.quantity', 'Quantity', 'en'),
('prompt.itemAdded', 'Item Added!', 'en'),

('prompt.itemName', 'Tuotteen nimi', 'fi'),
('prompt.itemPrice', 'Tuotteen hinta', 'fi'),
('prompt.itemQuantity', 'Tuotteen määrä', 'fi'),
('total.bill', 'Yhteensä: ', 'fi'),
('prompt.displayButton', 'Näytä ostoskori', 'fi'),
('prompt.addItem', 'Lisää tuote', 'fi'),
('field.name', 'Nimi', 'fi'),
('field.price', 'Hinta', 'fi'),
('field.quantity', 'Määrä', 'fi'),
('prompt.itemAdded', 'Tuote lisätty!', 'fi'),

('prompt.itemName', 'Artikelnamn', 'sv'),
('prompt.itemPrice', 'Artikelpris', 'sv'),
('prompt.itemQuantity', 'Artikelantal', 'sv'),
('total.bill', 'Totalt: ', 'sv'),
('prompt.displayButton', 'Visa kundvagn', 'sv'),
('prompt.addItem', 'Lägg till artikel', 'sv'),
('field.name', 'Namn', 'sv'),
('field.price', 'Pris', 'sv'),
('field.quantity', 'Antal', 'sv'),
('prompt.itemAdded', 'Artikel tillagd!', 'sv'),

('prompt.itemName', 'アイテム名', 'ja'),
('prompt.itemPrice', '価格', 'ja'),
('prompt.itemQuantity', '数量', 'ja'),
('total.bill', '合計: ', 'ja'),
('prompt.displayButton', 'カートを表示', 'ja'),
('prompt.addItem', 'アイテムを追加', 'ja'),
('field.name', '名前', 'ja'),
('field.price', '価格', 'ja'),
('field.quantity', '数量', 'ja'),
('prompt.itemAdded', 'アイテムが追加されました！', 'ja'),

('prompt.itemName', 'چیز کا نام', 'ur'),
('prompt.itemPrice', 'قیمت', 'ur'),
('prompt.itemQuantity', 'مقدار', 'ur'),
('total.bill', 'کل بل: ', 'ur'),
('prompt.displayButton', 'کارٹ دکھائیں', 'ur'),
('prompt.addItem', 'چیز شامل کریں', 'ur'),
('field.name', 'نام', 'ur'),
('field.price', 'قیمت', 'ur'),
('field.quantity', 'مقدار', 'ur'),
('prompt.itemAdded', 'چیز شامل ہو گئی!', 'ur'),

('prompt.itemName', 'वस्तुको नाम', 'ne'),
('prompt.itemPrice', 'मूल्य', 'ne'),
('prompt.itemQuantity', 'मात्रा', 'ne'),
('total.bill', 'कुल बिल: ', 'ne'),
('prompt.displayButton', 'कार्ट देखाउनुहोस्', 'ne'),
('prompt.addItem', 'वस्तु थप्नुहोस्', 'ne'),
('field.name', 'नाम', 'ne'),
('field.price', 'मूल्य', 'ne'),
('field.quantity', 'मात्रा', 'ne'),
('prompt.itemAdded', 'वस्तु थपियो!', 'ne');
```

---

## Project Setup

1. Clone the repository:
```bash
git clone https://github.com/aashish0770/DB_localization.git
```

2. Update the database credentials in `MariaDbConnection.java`:
```java
"jdbc:mariadb://localhost:3306/shopping_cart_localization?user=root&password=yourpassword"
```

3. Install dependencies with Maven:
```bash
mvn clean install
```

---

## Running the Application

Run from IntelliJ by executing the `Main` class, or from terminal:
```bash
mvn javafx:run
```

---



## Supported Languages

| Language | Code |
|----------|------|
| English  | en   |
| Finnish  | fi   |
| Swedish  | sv   |
| Japanese | ja   |
| Urdu     | ur   |
| Nepali   | ne   |

---

## How It Works

1. On startup the app fetches English UI strings from the `localization_strings` table
2. Clicking a language button reloads all UI strings for that language from the DB
3. Adding items stores them in the `ShoppingCart` HashMap in memory
4. Clicking **Total Bill** calculates the total, displays it, and saves the cart to the DB — one row in `cart_records` and one row per item in `items`
