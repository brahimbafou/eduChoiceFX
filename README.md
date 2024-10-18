# EduChoiceFX

EduChoiceFX is a JavaFX application designed for managing students, specialties, and their choices in an educational context. This application allows users to add, edit, delete, and view student records, as well as manage specialties and choices. The app provides a user-friendly interface to streamline the educational administrative processes.

## Features

- **Student Management**: Add, edit, and delete student records.
- **Specialty Management**: Manage different specialties offered in the institution.
- **Choice Management**: Allow students to make choices based on available specialties.
- **Search Functionality**: Search for students by ID or name, and specialties by name.
- **Responsive UI**: A clean and intuitive user interface built with JavaFX.

## Technologies Used

- **Java**: Core programming language.
- **JavaFX**: UI framework for building rich desktop applications.
- **Hibernate**: ORM for managing database interactions.
- **MySQL**: Database to store application data.

## Installation

To set up and run the application, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/EduChoiceFX.git
   cd EduChoiceFX
   ``` 

2. **Install Dependencies**:
   Ensure you have Maven installed, then run:
   ```bash
   mvn install
   ```


3. **Set Up Database**:
   - Create a MySQL database named `edu_choice`.
   - Update the database connection properties in the `hibernate.cfg.xml` file.

4. **Run the Application**:
   ```bash
   mvn javafx:run
   ```
   

## Usage

1. Launch the application.
2. Use the navigation tabs to switch between Student Management, Specialty Management, and Choice Management.
3. Add or edit records using the forms provided.
4. Use the search functionality to find students or specialties quickly.

## Contributing

Contributions are welcome! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the open-source community for their resources and support.
- Special thanks to the contributors who make this project better.
---

For more information, please feel free to reach out or open issues for any questions or suggestions.

### How to Use This Template

1. **Project Description**: Modify the project description to accurately reflect your application's functionality and goals.
2. **Installation Instructions**: Ensure that the installation steps are clear and relevant to your project's setup. You can include commands that specifically apply to your application.
3. **Usage**: Provide a brief guide on how to use the application, highlighting key features and functionalities.
4. **Contributing**: Include guidelines for contributing, which can help others understand how they can assist in improving the project.
5. **License**: Update the license section if you're using a different license.

Feel free to add sections for FAQs, known issues, or more detailed instructions based on your application’s needs!
