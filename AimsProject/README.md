# AIMS Project - An Internet Media Store

AIMS (An Internet Media Store) is an e-commerce application for the Ti-do company, a fictional merger between Tiki and Sendo.

## Project Structure

The project follows a package-based architecture:

```
hust.soict.tido.aims
├── media         - Classes for media items (DVDs)
├── cart          - Shopping cart functionality
├── store         - Store management and inventory
├── order         - Order processing and management
├── payment       - Payment processing
├── screen        - User interface screens
├── customer      - Customer management
├── search        - Search functionality
└── utils         - Utility classes
```

## Features

### For Customers

- Browse DVDs available in the store (sorted by added date)
- Search for DVDs by:
  - Title (case-insensitive keyword matching)
  - Category (case-insensitive matching)
  - Price (range filtering)
- View DVD details including title, category, director, length, and cost
- Play a demo of the DVD (simulated in the console)
- Add DVDs to cart
- View cart contents and total cost
- Sort cart items by:
  - Title (alphabetical, with higher cost first for same title)
  - Cost (decreasing, with alphabetical title for same cost)
- Update DVD quantity or remove DVDs from cart
- Get a lucky item (randomly selected free item from cart)
- Filter cart by DVD ID or title
- Place orders with delivery information
- Pay for orders using a credit card

### For Store Manager

- View all DVDs in the store
- Add new DVDs to the store
- Remove DVDs from the store
- View pending orders
- Approve or reject orders

## Main Components

### Media and DVDs

The `Media` class is the abstract base class for all media types. Currently, only `DigitalVideoDisc` is implemented.

### Cart

The `Cart` class manages the customer's shopping cart, allowing them to add, remove, and sort items.

### Store

The `Store` class manages the inventory of available media items and provides search functionality.

### Order

The `Order` class represents a customer order, tracking ordered items, customer information, and order status.

### Payment

The `Payment` interface and `CreditCardPayment` implementation handle payment processing through a simulated card association system.

### Screen Classes

- `MainScreen`: The entry point of the application
- `StoreScreen`: For browsing and searching the store
- `CartScreen`: For managing the cart and placing orders
- `StoreManagerScreen`: For store management functions

## How to Run

To start the application, run the `MainScreen` class:

```
java hust.soict.tido.aims.screen.MainScreen
```

## Implementation Notes

- The demo uses a console-based interface for simplicity.
- Credit card validation and processing are simulated.
- Email notifications are simulated (displayed in the console).
- DVDs have a play feature that simulates playing the content in the console. 