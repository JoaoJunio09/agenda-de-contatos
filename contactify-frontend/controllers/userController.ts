type Person = {
	id: number,
	firstName: string,
	lastName: string,
	gender: string,
	address: string
};

function sumTwoNumbers(): Person {

	const person: Person = {
		id: 1,
		firstName: "string",
		lastName: "string",
		gender: "string",
		address: "string"
	}

	return person;
}

const data: Person = sumTwoNumbers();