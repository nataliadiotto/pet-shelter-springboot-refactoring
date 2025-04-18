# 🐾  **Pet Shelter CLI**
![Java](https://img.shields.io/badge/Java-21-blue)
![CLI App](https://img.shields.io/badge/CLI-Application-orange)
![OOP](https://img.shields.io/badge/Principles-OOP-success)
![Strategy Pattern](https://img.shields.io/badge/Pattern-Strategy-yellow)
![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen)


Pet Adoption CLI is a Java-based console application designed to assist pet shelters in managing pet records for adoption. With a friendly interface and simple interactions, shelter staff can easily register, search, list, update, and delete pet records using a structured command-line menu.

The application is part of the challenge created by Lucas Carrilho ([@devmagro](https://x.com/devmagro)) to practice Java foundations. Access the project's [README](https://github.com/karilho/desafioCadastro?tab=readme-ov-file#desafio-programação---sistema-de-cadastros-) for further information.

---

### 📌 **Project Highlights**

- **Full CRUD Support:** Create, read, update, and delete pet records.
- **Input Validation and exception handling:** Ensures data integrity by validating user inputs.
- **Text File Storage:** Persists data using text files for simplicity and portability.
- **Strategy Pattern:** Applied to enable flexible filtering behavior to search pets by name, age, breed, and more.
- **Form-Based Input:** Reads structured prompts from `.txt` files for consistent data entry.
- **Object-Oriented Design:** Utilizes Java best practices for maintainable code.
  
---

## 💻 **Technologies Used**

- Java 21 (JDK)
- Java IO for file handling
- Enums for pet type and gender
- Object-Oriented Design principles
- CLI interaction via `Scanner`
- Exception handling and input sanitization

## 📄 **Sample Entry File – `formulario.txt`**

```
1 - What is the pet's full name?
2 - What type of pet is it (Dog/Cat)?
3 - What is the pet’s gender?
4 - What is the address and neighborhood it was found?
5 - What is the pet’s approximate age?
6 - What is the pet’s approximate weight?
7 - What is the pet’s breed?
```
> ⚠️ The application reads this file directly – **do not hardcode questions**.


## 🚀 **Features**

### Main Menu
<img width="503" alt="image" src="https://github.com/user-attachments/assets/efbdaac4-076d-4e4c-b04d-f1768a7f6755" />


### 1. Register a New Pet 🐾 
Register pets using structured prompts read from a file. Responses are validated and stored in a `.txt` file inside the `registeredPetsDir` folder.

```java
// Sample pet object creation
Pet newPet = new Pet(
    "Clara Bow",
    PetType.CAT,
    Sex.FEMALE,
    new Address("1313", "cornelia street", "new york"),
    13.0,
    13.0,
    "british shorthair"
);
```

✅ Validations:
- Name must include first and last name, only letters
- Breed must not include numbers or special characters
- Age must be ≤ 20 years
- Weight must be between 0.5kg and 60kg
- Defaults filled with `NOT INFORMED` if left blank

📝 Saved file name example:
```
20250413T1457-BOWCLARA.TXT
```

📝 Saved file content example:
```
1 - Clara Bow
2 - Cat
3 - Female
4 - 1313, Cornelia Street, New York
5 - 13.0 years old
6 - 13.0kg
7 - British Shorthair
```

---

### 2. 📋 List All Pets
Displays a complete list of registered pets in the terminal with all their information, retrieved from all the `.txt` files inside the `registeredPetsDir` directory.


📋 Example of registered pets display: 
<img width="880" alt="image" src="https://github.com/user-attachments/assets/0002e922-48e0-404b-83a9-f0c2eaa2dc03" />


---

### 3. Search Pets 🔎
Search using:
- Name (partial matches supported)
- Age
- Breed
- Sex
- Weight
- Address

🔍 Filter by **type (dog/cat)** first and then, combine up to **2 criteria**. Results are displayed in a readable format.
🧠 Implemented using the Strategy Pattern:
Each search criterion (e.g., filter by name, age, or breed) is encapsulated as a strategy. This promotes code extensibility and clean separation of logic, making it easier to add new filters or modify behavior.


📋 Example of filtering cats by gender: 
<img width="742" alt="image" src="https://github.com/user-attachments/assets/4f502867-7249-48f2-80b2-5a1a7cf40cbd" />


---

### 4. Edit a Pet 🔧
Update any field of a pet after performing a search as shown above. All validations from the registration apply.
- Users may enter the new information to update the Pet's data or simply press `enter` to keep the current information in each mandatory field.
- Optional fields such as `address number`, `age`, `weight`, and `breed` may also be left blank by pressing `0`, which will automatically set the fields as `NOT INFORMED`.
- If a pet is updated, the old file will be deleted and replaced by a new one, with its file name formatted to the most recent data.


---

### 5. Delete a Pet ❌
- Search for a pet and delete its record permanently from storage.
- All files of deleted pets are removed from `registeredPetsDir` directory.

---

## 🧪 **Possible Future Enhancements**

- 🌱 Refactor it with Spring framework
- 🐶 Photo upload (via path references)
- 📆 Filter by registration date
- 📊 Export data to CSV or Excel
- ✅ Unit tests with JUnit
- ☁️ Cloud integration for remote storage

---

### ⚙️ How to Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/nataliadiotto/pet-shelter.git
   ```

2. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse)

3. Run the `PetShelterApp` class

---

## 🔧 **Project Requirements**

- Java 21+
- Terminal or console access
- Basic understanding of OOP
  

### 🤝 Contributing

1. Fork the repo
2. Create your branch:  
   `git checkout -b feature/your-feature-name`
3. Commit changes:  
   `git commit -m 'Add new feature'`
4. Push your changes:  
   `git push origin feature/your-feature-name`
5. Open a Pull Request
   

### ⭐ Show Your Support

> If you find this project helpful, please ⭐ the repository!


