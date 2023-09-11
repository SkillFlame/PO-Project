# Project Objective
The objective of the project is to develop an application that will function as the manager of a communication terminal network, referred to as "prr." In general, the program should allow for the management and querying of clients, terminals, and communications. Specifically, the system provides various services to its users, including: (i) registering client data; (ii) registering terminal data; (iii) registering data on communications made; (iv) conducting searches on communications made; and (v) accounting for the balance associated with terminals.

This document is organized as follows. Section 1 presents the entities within the application's domain. The application's functionalities are described in sections 3 and 4. Section 2 describes the design requirements that the developed application should offer.

In this text, bold type indicates a literal (i.e., exactly as presented); the symbol "~" indicates a space; and italic type indicates a variable part (i.e., a description).

# 1. Domain Entities
In this section, we describe the various entities that will be manipulated within the context of the application to be developed. Several important concepts in this context include: terminal network, client, communication, and terminal.

Clients, terminals, and communications have unique keys, strings for clients and terminals, and integers for communications.

Each terminal network, client, and terminal has a specific balance. The notion of balance in the application is defined as the difference between the values of payments made and debts outstanding, considering the context of the entity. Thus, the balance of a terminal network is calculated as the sum of the balances of its clients. The balance of a client takes into account the balances of its various terminals, and finally, the balance of a terminal is the difference between the cost of paid and unpaid communications.

In the implementation of this application, it may be necessary to consider other concepts beyond those explicitly identified in this section.

## 1.1 Client
Each client, in addition to the unique key, also has a name (string) and a fiscal identification number (integer). Each client can be associated with multiple terminals. The client maintains information about payments made (for past communications) and outstanding values (for communications whose value has not yet been paid).

There are three types of clients: Normal (initial situation, after registration - however, see the situation regarding reading textual data), Gold, and Platinum. The client type influences the cost of the communications it makes (see tariff plans). The client type evolves under the conditions indicated in Table 1.

| Before     | After      | Condition                           |
|------------|------------|-------------------------------------|
| Normal     | Gold       | Client's balance (after a payment) is greater than 500 credits. |
| Normal     | Platinum   | Not possible.                       |
| Gold       | Normal     | Client's balance (after a communication) is negative. |
| Gold       | Platinum   | The client made 5 consecutive video communications (the calculation for the 5th communication still considers the client as Gold) and does not have a negative balance. |
| Platinum   | Normal     | Client's balance (after a communication) is negative. |
| Platinum   | Gold       | The client made 2 consecutive text communications (the calculation for the 2nd communication still considers the client as Platinum) and does not have a negative balance. |

## 1.2 Terminal
Each terminal is identified by a numeric string (exactly 6 digits) and is associated with a single client. Terminals perform communications. There are at least two types of terminals: basic and sophisticated. Basic terminals can perform text and voice communications but cannot initiate or receive video communications. Sophisticated terminals can perform all types of communication: text, voice, and video.

Communications made by the terminal are billed according to the tariff associated with the client. The terminal has its own accounting, and it is always possible to know the values of payments made and amounts owed. A terminal can have one or more friend terminals. Communications between friend terminals are cheaper than those between non-friend terminals. A terminal cannot be included in its set of friends; this situation should have no effect on the application's state.

A newly created terminal is in the idle state; its payment and debt values are both zero, and it has an empty list of friends. Below, we describe the behavior of the various states a terminal can be in.

### 1.2.1 Terminal States
A terminal can be turned off or on. A turned-off terminal cannot initiate or receive any type of communication. A turned-on terminal must distinguish between three situations: Busy, Idle, and Silent. A turned-on terminal is busy when it is engaged in a communication (video or voice; text is considered instantaneous) with another terminal. While a terminal is busy, it cannot initiate other communications but can receive text messages. A turned-on terminal that is not engaged in any voice or video communication is either in Silent or Idle mode. In Silent mode, the terminal can initiate any supported type of communication, but only text communications can be received. If the terminal is in Idle mode, it can initiate and receive any supported type of communication. Furthermore, a terminal in Silent or Idle mode cannot engage in interactive communication with itself.

A terminal can transition between states under the following conditions (other transitions are not possible):
- Idle: From off (transition to idle); from silent (transition to idle); from busy (end of communication).
- Silent: From idle (switch to silent); from busy (end of communication).
- Busy: From idle or silent (communication start).
- Off: From idle or silent (when turning off).

### 1.2.2 Notifications
The application must have the capability to notify clients when a terminal with which they attempted to communicate but were unsuccessful becomes available. Only clients who attempted communication with a terminal and were unsuccessful at that time can be notified. When communication fails to occur, the attempt to contact is recorded so that notifications can be sent to the originating terminals as soon as the desired contact can be established. The record of contact attempts only occurs when the client of the originating terminal has active reception of failed contacts at the time the communication was attempted. At any time, a client can activate or deactivate the reception of failed contacts.

Notifications are generated, and clients can be notified under the following circumstances:
- A terminal is turned off and switched to silent (off-to-silent): Availability for receiving text communications is notified.
- A terminal is turned off or in silent mode and switched to idle (off-to-idle or silent-to-idle): Availability for receiving communications (any supported type) is notified.
- A terminal is no longer busy (busy-to-idle): Availability for receiving communications (any supported type) is notified.

Notifications contain information about their nature and the terminal they relate to. A given event produces only one notification per client, and the set of clients to be notified by a terminal is cleared after sending the notification.

The mechanism for delivering notifications to a client should be flexible to allow various delivery methods, e.g., SMS, email, text messages. Initially, each client is associated with the default delivery mechanism, but it should be possible to change a client's delivery method. The default delivery method involves registering the notification in the application and then presenting the notifications (in the order they were received) when viewing a client's information.

## 1.3 Communications
Each communication has a unique identifier (integer number, in the context of all clients). The first successful communication has the identifier "1," with subsequent identifiers obtained by incrementing the most recent identifier used. The communication also contains information about the source and destination terminals and the state of the communication: ongoing or terminated. Text communications also include the sent message. Interactive communications (video and voice) provide information about the communication's duration. The cost of a text communication depends on the message's length. In the case of interactive communications, their cost depends on their duration. The cost also depends on the tariff plan associated with each client (calculated at the end of the communication). All calculations involving communication costs must be performed without rounding.

## 1.4 Tariff Plans
Each tariff plan defines the costs for each type of communication, based on the client's level, communication type, among other characteristics. Tariff plans have a unique name within the context of the associated terminal network. The terminal network can offer multiple tariff plans, but at any given time, a client only has one tariff plan. The terminal network offers at least the "base" tariff plan. This tariff plan is initially assigned to all clients. Communication costs are measured in credits.

The cost of a communication should be calculated when the communication ends and stored to ensure that the cost is not affected by future changes in tariff plans. The cost (measured in credits) of a text communication with N characters in the base tariff plan is represented in Table 2.

|                   | Normal | Gold | Platinum |
|-------------------|--------|------|----------|
| N < 50            | 10     | 10   | 0        |
| 50 <= N < 100     | 16     | 10   | 4        |
| N >= 100          | 2 * N  | 2 * N| 4        |

The cost of video and voice communications for non-friend terminals in the base tariff plan is indicated in Table 3.

|                   | Communication Type | Cost per Minute (Credits) |
|-------------------|---------------------|---------------------------|
| Normal            | Voice               | 20                        |
| Gold              | Voice               | 10                        |
| Platinum          | Voice               | 10                        |
| Normal            | Video               | 30                        |
| Gold              | Video               | 20                        |
| Platinum          | Video               | 10                        |

# 2 Design Requirements

- Extensions or modifications of functionality should be possible with minimal impact on the existing application code.
- The objective is to enhance the flexibility of the application to support new features. Therefore, it should be possible to:
  - Define new types of clients.
  - Define new types of communication.
  - Define new tariff plans.
  - Define new search methods.
  - Allow the management of multiple terminal networks.
- Even though some entities cannot be removed in the current specification, it is important to anticipate their potential inclusion in the future to minimize the impact.

## 2.1 Functionality of the Application

- The application enables the management of information about model entities.
- It also has the capability to preserve its state (multiple versions of the application state can coexist).
- Users should be able to perform searches based on various criteria and across different entities managed by the application.
- A textual database with predefined concepts can be loaded at the beginning of the application.
- Initially, the application only contains information about entities that were loaded during startup.
- Note that there's no need to build the application from scratch. Some code is already provided, including the skeleton of classes required to implement menus and corresponding commands in the application's service layer. The `prr.app.App` class serves as the entry point for the application. Some classes related to the application's domain and a set of exceptions for use during development are also provided. Some commands are fully implemented, while others (the majority) are partially implemented and require completion.

## 2.2 Skeleton Application

- A skeleton of the application is already provided, so you don't need to create the application from scratch. You can find this skeleton in the `prr-skeleton.jar` archive in the Project section of the course's web page.
- The skeleton includes the `prr.app.App` class, representing the entry point of the application, as well as various commands and menus for the application (described in section 3). Some commands are already fully implemented, while others are partially implemented and require completion. These classes are implemented in the `prr.app` package and its sub-packages (e.g., `prr.app.main`).

## 2.3 Domain and Exceptions

- The skeleton also includes some classes related to the application's domain and a set of exceptions for use during development. You will need to create new entities in the core and complete those that are partially provided to have a set of entities that offer the required functionality for the application's domain.
- Exception handling is already defined in the service layer (commands) in the `prr.app.exception` package. The `prr.core.exception` package contains some exceptions for use in the domain layer. You can define new exceptions in this package if necessary to represent exceptional situations that may occur in the domain entities.

## 2.4 Serialization

- The application supports the ability to save and restore its current state, preserving all relevant information from the application's domain as described in section 1.

# 3 User Interaction
This section describes the maximum functionality of the user interface. In general, commands request all information before proceeding to validation (except where indicated). All menus automatically have the "Exit" option (which closes the menu).

Operations involving user input and information presentation should be performed using the "form" and "display" objects present in each command. If a command uses more than one form to interact with the user, it may be necessary to create at least one additional instance of "Form" during the execution of that command. Messages are generated by the methods of support libraries (`po-uilib` and classes in the application's skeleton). New messages cannot be defined. Any potential omissions should be clarified with the teaching staff before implementation.

Monetary values should always be presented with rounding to the nearest integer, but the internal representation should not be rounded. Lists of domain entities (such as clients, etc.) should be presented in ascending order of their respective keys, which may be numeric or lexicographic (UTF-8), with no distinction between uppercase and lowercase.

To ensure independence between various application layers, there should be no user interaction code in the core of the application (`prr.core`). This allows for the reuse of core application code with different user interface implementations. By separating business logic into the core layer, code reusability is enhanced, and the application becomes more readable and easier to maintain.

Exceptions used in user interaction code to describe error situations, unless indicated otherwise, are subclasses of `pt.tecnico.uilib.menus.CommandException` and should be thrown by commands (then automatically handled by the existing `pt.tecnico.po.ui.Menu` class). These exceptions are already defined in the `prr.app.exception` package. Other exceptions should not replace those described in the cases mentioned. It is the responsibility of each group to define any necessary exceptions in the context of application domain entities (or reuse existing exceptions from the Java library if appropriate). Exceptions available in `prr.app.exception` should only be used in user interaction code.

Whenever there is interaction with the user, whether to request data or present data, the responsible method of the `Message` class in the relevant package should be indicated for generating the message to be displayed to the user.

## 3.1 Main Menu
The actions in this menu allow for managing the state of the application, opening submenus, and accessing global information. The complete list is as follows: Open, Save, Customer Management, Terminal Management, Queries, and Show Global Payment and Debt Information. Various dialog messages used in the various commands of this menu are defined in the various methods of the `prr.app.main.Message` class.

Some of the commands that will implement the functionalities of this menu are already partially implemented in the classes of the `prr.app.main` package: DoOpen, DoSave, and DoShowGlobalBalance. The remaining commands (which correspond to opening a submenu) are already fully implemented.

### 3.1.1 Save Current State
Initially, the application is empty or contains information about entities that were loaded at startup from a text file (see Section 4).

The content of the application (representing the current relevant state in memory) can be saved for later retrieval (using Java serialization: `java.io.Serializable`). Exception handling should be implemented when reading and writing the application's state. The functionality is as follows:

**Open**: Loads data from a previous session from a previously saved file (associating this file with the application for future save operations). The user is prompted to enter the name of the file to open (using the string returned by `openFile()`). If a problem occurs while opening or processing the file, the `FileOpenFailedException` exception should be thrown. The successful execution of this option replaces all application information.

**Save**: Saves the current state of the application to the associated file. If there is no associated file, the user is prompted to enter the file name to use (using the string returned by `newSaveAs()`), associating the application with this file.

Note that the "Exit" option never saves the application's state, even if there are changes.

### 3.1.2 Management and Querying of Application Data
Management and querying of application data are performed through the following options:

- **Customer Management Menu**: Opens the customer management menu.
- **Terminal Management Menu**: Opens the terminal management menu.
- **Queries Menu**: Opens the queries menu (searches).

### 3.1.3 Show Global Payment and Debt Information
This option presents the global values corresponding to payments and debts (sum of partial values for all registered clients). The presentation of these values should be done using the message returned by `Message.globalPaymentsAndDebts()`. Note that although internally payments and debts are real numbers, the presentation of values should be rounded to the nearest integer.

## 3.2 Customer Management Menu
This menu allows for operations on the customer database. The complete list is as follows: View Customer, View All Customers, Register Customer, Enable Failed Contact, and Delete Customer. Various dialog messages used in the various commands of this menu are defined in the various methods of the `prr.app.customer.Message` class.

### 3.2.1 View Customer
This command allows viewing detailed information about a specific customer. The customer is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the customer identifier to be viewed (using the string returned by `requestCustomerKey()`).
- Display detailed information about the customer (using the `display()` method).
- If the customer is not found, throw the `UnknownKeyException` exception.

### 3.2.2 View All Customers
This command allows viewing a list of all registered customers, including their identifiers and names.

The specific functionality of this command is as follows:
- Retrieve and display a list of all customers (using the `listCustomers()` method).
- If no customers are registered, display the message returned by `noCustomers()`.
  
### 3.2.3 Register Customer
This command allows registering a new customer. The customer is identified by a numeric identifier and a name (string).

The specific functionality of this command is as follows:
- Request the customer's numeric identifier (using the string returned by `requestCustomerKey()`).
- Request the customer's name (using the string returned by `requestCustomerName()`).
- Create and register the new customer.
- If a customer with the same identifier already exists, throw the `DuplicateKeyException` exception.

### 3.2.4 Enable Failed Contact
This command allows enabling the contact status of a customer. The customer is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the customer identifier for enabling contact (using the string returned by `requestCustomerKey()`).
- Enable contact for the specified customer.
- If the customer is not found, throw the `UnknownKeyException` exception.

### 3.2.5 Delete Customer
This command allows deleting a customer from the database. The customer is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the customer identifier for deletion (using the string returned by `requestCustomerKey()`).
- Delete the specified customer.
- If the customer is not found, throw the `UnknownKeyException` exception.

## 3.3 Terminal Management Menu
This menu allows for operations on the terminal database. The complete list is as follows: View Terminal, View All Terminals, Register Terminal, Update Terminal Location, and Delete Terminal. Various dialog messages used in the various commands of this menu are defined in the various methods of the `prr.app.terminal.Message` class.

### 3.3.1 View Terminal
This command allows viewing detailed information about a specific terminal. The terminal is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the terminal identifier to be viewed (using the string returned by `requestTerminalKey()`).
- Display detailed information about the terminal (using the `display()` method).
- If the terminal is not found, throw the `UnknownKeyException` exception.

### 3.3.2 View All Terminals
This command allows viewing a list of all registered terminals, including their identifiers and locations.

The specific functionality of this command is as follows:
- Retrieve and display a list of all terminals (using the `listTerminals()` method).
- If no terminals are registered, display the message returned by `noTerminals()`.

### 3.3.3 Register Terminal
This command allows registering a new terminal. The terminal is identified by a numeric identifier and a location (string).

The specific functionality of this command is as follows:
- Request the terminal's numeric identifier (using the string returned by `requestTerminalKey()`).
- Request the terminal's location (using the string returned by `requestTerminalLocation()`).
- Create and register the new terminal.
- If a terminal with the same identifier already exists, throw the `DuplicateKeyException` exception.

### 3.3.4 Update Terminal Location
This command allows updating the location of a terminal. The terminal is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the terminal identifier for location update (using the string returned by `requestTerminalKey()`).
- Request the new location for the terminal (using the string returned by `requestTerminalLocation()`).
- Update the location of the specified terminal.
- If the terminal is not found, throw the `UnknownKeyException` exception.

### 3.3.5 Delete Terminal
This command allows deleting a terminal from the database. The terminal is identified by a numeric identifier.

The specific functionality of this command is as follows:
- Request the terminal identifier for deletion (using the string returned by `requestTerminalKey()`).
- Delete the specified terminal.
- If the terminal is not found, throw the `UnknownKeyException` exception.

## 3.4 Queries Menu
This menu allows for performing various queries on the application data. The complete list of query options is as follows: Query Payments by Customer, Query Debt by Customer, Query Customer Payments, and Query Customer Debt. Various dialog messages used in the various queries are defined in the various methods of the `prr.app.query.Message` class.

### 3.4.1 Query Payments by Customer
This query allows querying the payments made by a specific customer. The customer is identified by a numeric identifier.

The specific functionality of this query is as follows:
- Request the customer identifier for payment query (using the string returned by `requestCustomerKey()`).
- Retrieve and display the payments made by the specified customer (using the `listPaymentsByCustomer()` method).
- If the customer is not found, throw the `UnknownKeyException` exception.

### 3.4.2 Query Debt by Customer
This query allows querying the debt of a specific customer. The customer is identified by a numeric identifier.

The specific functionality of this query is as follows:
- Request the customer identifier for debt query (using the string returned by `requestCustomerKey()`).
- Retrieve and display the debt of the specified customer (using the `displayDebtByCustomer()` method).
- If the customer is not found, throw the `UnknownKeyException` exception.

### 3.4.3 Query Customer Payments
This query allows querying the payments made by all customers.

The specific functionality of this query is as follows:
- Retrieve and display the payments made by all customers (using the `listCustomerPayments()` method).
- If no customers have made payments, display the message returned by `noPayments()`.

### 3.4.4 Query Customer Debt
This query allows querying the debt of all customers.

The specific functionality of this query is as follows:
- Retrieve and display the debt of all customers (using the `listCustomerDebt()` method).
- If no customers have debt, display the message returned by `noDebt()`.


