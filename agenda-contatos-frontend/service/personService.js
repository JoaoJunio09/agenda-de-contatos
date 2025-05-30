const API_BASE_URL_CREATE = "http://localhost:8080/api/user/v1";
const API_BASE_URL_FINDALL = "http://localhost:8080/api/user/v1";

async function createPerson(person) {
  const response = await fetch(API_BASE_URL_CREATE, {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(person)
	});

	if (!response.ok) {
		const errorData = await response.json().catch(() => ({}));
		const errorMessage = errorData.message || `Erro ${response.status}`;

		const objectError = {
			title: errorData.title,
			body: errorMessage || "",
		};

		throw objectError;
	}

	const data = await response.json();
	return data;
}

async function findAllPerson() {
	const response = await fetch(API_BASE_URL_FINDALL, {
		method: "GET",
	});

	const data = await response.json();
	return data;
}

export const PersonService = {
    cadastrar: createPerson,
	listar: findAllPerson
};