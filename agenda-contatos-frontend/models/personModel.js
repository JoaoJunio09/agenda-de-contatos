let person = {};

function setPerson(dataPerson) {
	person = dataPerson;
}

function getPerson() {
	return person;
}

export { setPerson, getPerson };