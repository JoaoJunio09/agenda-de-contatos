const API_BASE_URL_FINDALL = "http://localhost:8080/api/user/v1";
const API_BASE_URL_DETAILS_USER = "http://localhost:8080/api/user/v1/:id/details";
const API_BASE_URL_UPDATE = "http://localhost:8080/api/user/v1";

async function findAll() {
	const response = await fetch(API_BASE_URL_FINDALL, {
		method: 'GET',
	});

	const data = await response.json();
	return data;
}

async function findUserDetails(id) {
	const url = API_BASE_URL_DETAILS_USER.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET',
	});

	if (!response.ok) {
		throw new Error("Erro ao busar dados do usuário");
	}

	const data = await response.json();
	return data;
}

async function update(user) {
	const response = await fetch(API_BASE_URL_UPDATE, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(user)
	});

	if (!response.ok) {
		throw new Error("Erro ao atualizar usuário");
	}

	const data = await response.json();
	return data;
}

export const UserService = {
	findAll,
	findUserDetails,
	update,
}