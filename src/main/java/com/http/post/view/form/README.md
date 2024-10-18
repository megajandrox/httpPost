# Swing Form with DAO Pattern

## Exercise Overview
This exercise combines the **DAO Pattern (Data Access Object)** with building a form using **Swing**. You are required to create a screen for an entity where users can capture the necessary data and handle basic operations like saving and clearing fields. The form should manage events for clearing the input fields and saving the captured data.

## Objectives
- Create a form that captures data for a selected entity.
- Implement the **DAO Pattern** to handle data storage independently of the storage type (e.g., database, file, etc.).
- Handle events for clearing fields and saving data using Swing components.
- Ensure the solution allows for easy extension, such as adding a service layer, making it adaptable for future projects (e.g., final TP).

## Functional Requirements
The application should have:
1. A form to input the required fields for a selected entity.
2. A "Save" button to persist the input data using the DAO layer.
3. A "Clear" button to reset all input fields.

## Features
- **DAO Layer**: Handles data persistence, abstracting away the underlying storage mechanism.
- **Swing Form**: Allows users to input data for a given entity.
- **Clear Fields**: Resets the form inputs using the `miTextField.setText("")` method for each field.
- **Save Data**: Captures the data from the form and stores it using the DAO layer.

## Steps to Complete
1. **Create the Form**: Design the user interface using any layout in Swing (GridLayout, BorderLayout, etc.).
    - Add labels and input fields for the entity's attributes.
    - Include two buttons: "Save" and "Clear".
2. **Implement Event Handling**:
    - **Clear Button**: When pressed, all input fields should be cleared.
    - **Save Button**: When pressed, capture the input data and send it to the DAO layer to be saved.
3. **DAO Implementation**:
    - Implement a DAO layer that stores the entity’s data. The storage solution can vary (database, file system, etc.), but the model should be independent of the storage type.

## Example Use Case
- **Entity**: A simple example could be a "Customer" with attributes like `Name`, `Email`, and `Phone Number`.
- **Form Layout**: You could use a `GridLayout` to display labels and text fields for these attributes.
- **DAO Layer**: This layer will handle saving the customer’s data to a specific storage option, ensuring the model is decoupled from the storage mechanism.

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/swing-dao-form.git
