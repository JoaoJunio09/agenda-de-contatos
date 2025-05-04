const API_BASE_URL = "http://localhost:8080/api/person/v1";

async function createPerson(person) {
	try {
		const response = await fetch(API_BASE_URL, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(person)
		});

		if (!response.ok) {
			const errorData = await response.json().catch(() => ({}));
			const errorMessage = errorData.message || `Erro ${response.status}`;
			throw new Error(errorMessage);
		}

    console.log(response.json());
		return await response.json();
	}
	catch (error) {
		console.log("[PersonService] Erro ao cadastrar pessoa: ", error.message);
		throw error;
	}
}

export const PersonService = {
	cadastrar: createPerson
};