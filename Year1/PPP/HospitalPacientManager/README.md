# Pacients Managing Program

## What is it about?
This work develops a sophisticated program to manage patients in a hospital, with the aim of providing an efficient and intuitive tool for managing medical data.
The program is divided into three files, each responsible for a specific part of the functionality, ensuring modularity and ease of maintenance. 
Using dynamic data structures, the program stores and manipulates patient information and health records efficiently and securely.
Dynamic data structures allow for flexible and scalable management, adapting to the potential growth of the patient database without compromising performance. 
In addition, the use of these structures facilitates the implementation of advanced functionalities, such as fast record searching, updating information in real time and generating detailed reports.

## System Operation
### Initialization:
The program starts by creating a list of patients (lista_doentes) using the "cria" function.
Then, it checks for the existence of the "doentes.txt" and "registos.txt" files. If they exist, it loads the data of patients and their records into the list.

### Main Menu:
The program presents an interactive menu with several options for the user:
- Introduzir novo Doente: Adds a new patient to the list.
- Eliminar Doente: Removes a patient from the list.
- Listar todos os Doentes: Prints all registered patients.
- Listar Doentes acima de uma tensão: Prints patients whose maximum recorded blood pressure is above a specified value.
- Apresentar informações de um Doente: Shows detailed information of a specific patient.
- Novo registo de um Doente: Adds a new record of data (blood pressure, weight, height) for an existing patient.
- Sair: Saves data to files and exits the program.

### Patient Management:
- Add Pacient (novo_doente): Receives patient data, validates the entries (birth date, citizen card number, phone number), and inserts the patient into the list.

- Remove Pacient (elimina_doente): Searches for a patient by name and removes them from the list.

- List Pacients (imprime): Prints the IDs and names of all patients.

- Search Pacient (pesquisa): Searches for a patient by name and returns their node in the list.

### Record Management:
- Add Record (novo_registo): Adds a new measurement record (blood pressure, weight, height) for a specific patient.

- Printing Records (imprime_registo): Prints all records of a patient.

- Search Record by date (procura_data): Searches for a specific record by date.

### Data Management:
- Load Pacients (le_doente_fich): Reads patient data from the doentes.txt file and inserts it into the list.

- Load Records (le_registo_fish): Reads records from the registos.txt file and associates them with the corresponding patients.

- Export Pacients (insere_doente_fich): Saves patient data to the doentes.txt file.

- Export Records (insere_registo_fich): Saves records to the registos.txt file.

## Data Structures
- Doente (Patient):
Contains personal information such as ID, name, date of birth, citizen card number, phone number, and email.

- Registo (Record):
Contains health measurements: maximum and minimum blood pressure, weight, and height.

- Lista:
Implemented as a linked list of patients (node), where each node contains a patient and a list of records (lista_reg).

- Lista de Registos:
Each node (node_reg) represents a record associated with a specific patient.

## Main Functions
- Structure Creation: cria, cria_registo, cria_tensoes

- Structure Destruction: destroi, destroi_reg, destroi_ten

- Insertion and Removal: insere, elimina, insere_registo, elimina_doente

- Search: procura, procura_id, procura_data

- Validations: dataValida, ccValido, telefoneValido
