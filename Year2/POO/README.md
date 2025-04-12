# POO Financial Services (POOFS)

A university project developed by André Leão and José Antunes for the Object-Oriented Programming course. 
The application simulates a financial service system that handles clients, invoices, and categorized products such as food and pharmaceutical items.
It focuses on object-oriented principles, data encryption, and efficient file management.

## What It Does

The program allows for:
- Managing client data along with their invoices and purchased products.
- Storing and retrieving data from a `.txt` file using a custom encryption format.
- Automatically calculating VAT (IVA) based on product types and regions.
- Applying object-oriented concepts like inheritance and polymorphism for clean and modular code design.

## Key Functionalities

### File Management & Encryption
- Data is imported from and exported to a `.txt` file using a structured and encrypted format.
- Robust data validation during import prevents corrupted or invalid entries.
- Encrypted format compresses and unifies client, invoice, and product data into a single line.

### Object-Oriented Architecture
- Extensive use of inheritance: 
  - Base class `Product`
    - Subclasses `Food` and `Pharmacy`
    - Further specialization into types like `Reduced`, `Intermediate`, `Normal`, `Prescription`, etc.
- Polymorphism used in methods like `calculateTax()` and `productData()` to customize behavior for different product types without conditionals.

### VAT (IVA) Calculation
- Automatically adjusts tax rates depending on the product category and geographical region (Continental Portugal, Madeira, Azores).

## Encryption Format Example

```text
name;nif;location|invoiceID@date@productID#name#description#qty#price#type#...
```

Special separators:
- `;` separates client data
- `|` separates invoices
- `@` separates invoice and products
- `#` separates product attributes
- `!` is used for sub-certifications in reduced tax products

## Authors
- André Leão
- José Antunes
