import { setPerson, getPerson } from "../models/personModel.js";

const contentDataPerson = document.querySelector(".box-contact");
const sectionDataPerson = document.querySelector(".content-contacts");

for (let i = 1; i <= 20; i++) {
	let clone = contentDataPerson.cloneNode(true);

	let id = i+1;
	const fisrtName = "firstName123";
	const lastName = "lastName123";
	const address = "address123";
	const gender = "gender123";

	const data = {
		id,
		fisrtName,
		lastName,
		address,
		gender,
	};
	setPerson(data);
	const person = getPerson();

	clone.querySelector(".box-contact-title h4").innerHTML = `Cód: ${person.id}`;
	clone.querySelector(".box-contact-data").innerHTML = `
		<p id="firstName">First name: ${person.fisrtName}</p>
		<p id="lastName">Last name: ${person.lastName}</p>
		<p id="address">Address: ${person.address}</p>
		<p id="gender">Gender: ${person.gender}</p>
	`;

	sectionDataPerson.appendChild(clone);
}