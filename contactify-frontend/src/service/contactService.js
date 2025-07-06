const API_BASE_URL_FINDALL = "http://localhost:8080/api/contact/v1";
const API_BASE_URL_FINDBYID = "http://localhost:8080/api/contact/v1/:id";
const API_BASE_URL_DELETE = "http://localhost:8080/api/contact/v1/:id";
const API_BASE_URL_FINDCONTACTSBYUSER = "http://localhost:8080/api/contact/v1/findContactsByUser/:id";
const API_BASE_URL_CREATE = "http://localhost:8080/api/contact/v1";
const API_BASE_URL_UPDATE = "http://localhost:8080/api/contact/v1";

async function findAll() {
	const response = await fetch(API_BASE_URL_FINDALL, {
		method: 'GET'
	});

	const data = await response.json();
	return data;
}

async function findById(id) {
	const url = API_BASE_URL_FINDBYID.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível buscar contato");
	}

	const data = await response.json();
	return data;
}

async function findContactsByUser(id) {
	const url = API_BASE_URL_FINDCONTACTSBYUSER.replace(":id", id);

	const response = await fetch(url, {
		method: 'GET'
	});

	if (!response.ok) {
		throw new Error("Não foi possível buscar contatos");
	}

	const data = await response.json();
	return data;
}

async function create(user) {
	const response = await fetch(API_BASE_URL_CREATE, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(user)
	});

	if (!response.ok) {
		throw new Error("Erro ao registrar Contato");
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
		throw new Error("Erro ao atualizar Contato");
	}

	const data = await response.json();
	return data;
}

async function deleteContact(id) {
	const url = API_BASE_URL_DELETE.replace(":id", id);
	
	const response = await fetch(url, {
		method: 'DELETE'
	});

	if (!response.ok) {
		throw new Error({ type: "error", message: "Erro ao deletar contato." });
	}
	else {
		return { type: "success", message: "Contato deletado." };
	}
}

export const ContactService = {
	findAll,
	findById,
	findContactsByUser,
	create,
	update,
	deleteContact
}