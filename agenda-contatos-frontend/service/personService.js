const API_BASE_URL = "http://localhost:8080/api/user/v1";

function createPerson(person) {
	// try {
	// 	const response = await fetch(API_BASE_URL, {
	// 		method: "POST",
	// 		headers: {
	// 			"Content-Type": "application/json"
	// 		},
	// 		body: JSON.stringify(person)
	// 	});

	// 	if (response.ok) {
	// 		const errorData = await response.json().catch(() => ({}));
	// 		const errorMessage = errorData.message || `Erro ${response.status}`;

			const objectError = {
				title: "Erro ao cadastrar usuário.",
				body: "errorMessage",
			};

			throw objectError;
		// }

		// const data = await response.json();
    	// console.log(data.JSON);
		// return data;
	// }
	// catch (error) {
	// 	console.log(`Error: `, error.message);
	// }
}

export const PersonService = {
	cadastrar: createPerson
};