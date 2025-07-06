export class Person {
	id = null;
	firstName = "";
	lastName = "";
	birthDate = "";
	email = "";
	phone = "";
	gender = "";
	address = "";
	complement = "";
	number = "";
	nationality = "";
	cpf = "";
	rg = "";

	constructor() {
	}

	setDataFormCreate(id, firstName, lastName, birthDate, email, phone, gender, address, complement, number, nationality, cpf, rg) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.address = address;
		this.complement = complement;
		this.number = number;
		this.nationality = nationality;
		this.cpf = cpf;
		this.rg = rg;
	}

	setDataFormPage1(id, firstName, lastName, birthDate, email, phone, gender) {
		this.id = id;
		this.firstName = firstName,
		this.lastName = lastName,
		this.birthDate = birthDate,
		this.email = email,
		this.phone = phone,
		this.gender = gender;
	}

	setDataFormPage2(address, complement, number, nationality, cpf, rg) {
		this.address = address,
		this.complement = complement,
		this.number = number,
		this.nationality = nationality,
		this.cpf = cpf,
		this.rg = rg;
	}
}