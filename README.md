
![GitHub repo size](https://img.shields.io/github/repo-size/yourusername/Pet-Adoption-CLI?style=for-the-badge)
![GitHub language count](https://img.shields.io/github/languages/count/yourusername/Pet-Adoption-CLI?style=for-the-badge)
![GitHub forks](https://img.shields.io/github/forks/yourusername/Pet-Adoption-CLI?style=for-the-badge)
![GitHub issues](https://img.shields.io/github/issues/yourusername/Pet-Adoption-CLI?style=for-the-badge)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/Pet-Adoption-CLI?style=for-the-badge)

# 🐾 **Pet Shelter CLI**

Pet Adoption CLI is a Java-based console application designed to assist animal shelters in managing pet records for adoption. With a friendly interface and simple interactions, shelter staff can easily register, search, list, update, and delete pet records using a structured command-line menu.

---

### 📌 **Project Highlights**

- Full CRUD support for pet records
- Input validation and exception handling
- Text file storage for persistence
- Search by name, age, breed, and more
- Form-based input read directly from `.txt` files
- Object-Oriented Design and Java best practices

---

## 🚀 **Features**

### 1. Register a New Pet
Register pets using structured prompts read from a file. Responses are validated and stored in a `.txt` file inside the `petsCadastrados` folder.

```java
// Sample pet object creation
Pet newPet = new Pet(
    "Florzinha da Silva",
    PetType.CAT,
    Sex.FEMALE,
    new Address("Rua 2", "456", "Seilandia"),
    6.0,
    5.0,
    "Siames"
);
```

✅ Validations:
- Name must include first and last name, only letters
- Breed must not include numbers or special characters
- Age must be ≤ 20 years
- Weight must be between 0.5kg and 60kg
- Defaults filled with `NÃO INFORMADO` if left blank

📝 Saved file example:
```
20250413T1457-FLORZINHADASILVA.TXT
```

---

### 2. List Pets
Displays a complete list of registered pets in the terminal with all their information.

---

### 3. Search Pets
Search using:
- Name (partial matches supported)
- Age
- Breed
- Sex
- Weight
- Address

🔍 Combine up to **2 criteria** and filter by **type (dog/cat)**. Results are displayed in a readable format.

---

### 4. Edit a Pet
Update any field of a pet after performing a search. All validations from the registration apply.

---

### 5. Delete a Pet
Search for a pet and delete its record permanently from storage.

---

### 6. Filter by Criteria
List pets based on:
- Age
- Name
- Breed

Future updates may include sorting options and advanced filters.

---

## 📂 **Folder Structure**

```
📦 Pet-Adoption-CLI
 ┣ 📂 petsCadastrados         # Stores pet .txt records
 ┣ 📄 formulario.txt          # Input prompt file
 ┣ 📄 Main.java               # Application entry point
 ┣ 📄 Pet.java                # Pet entity class
 ┣ 📄 PetService.java         # Business logic
 ┣ 📄 PetController.java      # Input/Output controller
 ┣ 📄 FileManager.java        # Handles file I/O
 ┗ 📄 Utils.java              # Utilities and constants
```

---

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

---

## 💻 **Technologies Used**

- Java 21 (JDK)
- Java IO for file handling
- Enums for pet type and gender
- Object-Oriented Design principles
- CLI interaction via `Scanner`
- Exception handling and input sanitization

---

## 🧪 **Possible Future Enhancements**

- 🐶 Photo upload (via path references)
- 📆 Filter by registration date
- 📊 Export data to CSV or Excel
- 🌍 Multilingual CLI
- ✅ Unit tests with JUnit
- ☁️ Cloud integration for remote storage

---

## ⚙️ **How to Run**

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/Pet-Adoption-CLI.git
   ```

2. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse)

3. Run the `Main` class

---

## 🔧 **Project Requirements**

- Java 21+
- Terminal or console access
- Basic understanding of OOP

---

## 🤝 **Contributing**

We welcome contributions! To contribute:

1. Fork the repo
2. Create your branch:  
   `git checkout -b feature/your-feature-name`
3. Commit changes:  
   `git commit -m 'Add new feature'`
4. Push your changes:  
   `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## 📬 **Contact**

Feel free to reach out if you have suggestions, feedback, or questions!

---

## ⭐ **Show Your Support**

If you liked this project, please ⭐ the repository!

---
